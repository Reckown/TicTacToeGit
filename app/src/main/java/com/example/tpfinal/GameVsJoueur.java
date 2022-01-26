package com.example.tpfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class GameVsJoueur extends AppCompatActivity implements ValueEventListener {

    // Base de donnée :

    private FirebaseDatabase database;
    // Référence de la root :
    private DatabaseReference rootReference;
    // Référence vers qui peut jouer,
    // Si peutJouer = 0 alors personne ne peut jouer (partie fini ou en attente des 2 joueurs)
    // Si peutJouer = 1 alors tour du joueur 1,
    // Si peutJouer = 2 alors tour du jouer 2,
    private DatabaseReference peutJouer;
    // Reférence vers qui gagne la partie :
    // 0 pas encore de gagnant ni de match nul,
    // 1 le joueur 1 gagne la partie,
    // 2 le joueur 2 gagne la partie
    private DatabaseReference gagne;
    // Référence vers les 2 joueurs,
    // Si un des joueurs = 0 alors la place est libre,
    // Si un des joueurs = 1 alors il y a déjà un joueur ici
    // Si un nouveau joueur arrive il faut regardé combien de joueur il y a actuellement
    private DatabaseReference joueur;
    // Référence vers le tableau contenant les 9 cases du jeu
    // Si une case = 0 elle est libre,
    // si une case = 1 il y a un rond dedans,
    // Si une case = 2 il y a un carré dedans;
    private DatabaseReference cases;

    // Grille :
    private ImageButton grille1;
    private ImageButton grille2;
    private ImageButton grille3;
    private ImageButton grille4;
    private ImageButton grille5;
    private ImageButton grille6;
    private ImageButton grille7;
    private ImageButton grille8;
    private ImageButton grille9;

    //  Table qui va contenir la partie et les différentes classes
    // Si case = 0 alors la case est vide et disponible
    // Si case = 1 alors la case contient un rond
    // Si case = 2 alors la case contient une croix
    private int[][] tab;

    // Sauvegarde si cet utilisateur est le 1 ou le 2
    private int player = 0;
    private int opposant = 0;

    // Text view qui dit 'a vous de jouer'
    // Ou tour de l'adversaire
    private TextView whoPlay;

    // Textview qui affiche joueur 1 ou joueur 2 en fonction du joueur derriere le téléphone
    private TextView affichageJoueur;

    private boolean canPlay = false;

    // ProgressBar qu'on va afficher lorsque l'on attend le joueur 2
    private ProgressBar progressBar;

    // True si la personne derriere l'écran est en mode spec
    private boolean spectator;

    // Utile uniquement pour le mode spéctateur, sert à remettre la tab à 0 lorsque l'on passe à la partie suivante
    private boolean nextGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_vs_joueur);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Initialisation de la base de donnée et des références :
        database = FirebaseDatabase.getInstance("https://moreisen-4d056-default-rtdb.europe-west1.firebasedatabase.app");
        rootReference = database.getReference();
        peutJouer = rootReference.child("peutjouer");
        gagne = rootReference.child("gagne");
        joueur = rootReference.child("joueur");
        cases = rootReference.child("cases");

        // Initialisation de la grille :
        this.grille1 = findViewById(R.id.grille1h);
        this.grille2 = findViewById(R.id.grille2h);
        this.grille3 = findViewById(R.id.grille3h);
        this.grille4 = findViewById(R.id.grille4h);
        this.grille5 = findViewById(R.id.grille5h);
        this.grille6 = findViewById(R.id.grille6h);
        this.grille7 = findViewById(R.id.grille7h);
        this.grille8 = findViewById(R.id.grille8h);
        this.grille9 = findViewById(R.id.grille9h);

        this.whoPlay = findViewById(R.id.whoPlay);

        this.affichageJoueur = findViewById(R.id.textViewidJoueur);
        affichageJoueur.setVisibility(View.GONE);

        joueur.child("j3").setValue("");
        progressBar = findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.GONE);

        this.spectator = false;
        this.nextGame = false;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        // On regarde la key pour savoir ce qui a été modifié
        String key = snapshot.getKey();
        if(snapshot.getValue() == null){
            return;
        }
        // On met un nouvau joueur dans la base :
        if(key.equals("joueur")){
            Map<String, Object> joueur = (Map<String, Object>) snapshot.getValue();
            // Le joueur qui vient d'arriver est le j1
            assert joueur != null;
            if(joueur.get("j1") == null && this.player == 0){
                whoPlay.setText("Attente du joueur 2...");
                this.player = 1;
                this.opposant = 2;
                this.joueur.removeEventListener(this);
                this.joueur.child("j1").setValue("1");
                tab = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
                affichageJoueur.setVisibility(View.VISIBLE);
                affichageJoueur.setText("Joueur 1");
                progressBar.setVisibility(View.VISIBLE);
            }else if(joueur.get("j2") == null && this.player == 0){
                // Si le joueur qui vient d'arriver est le j2 alors ca veut dire qu'on peut commancer la partie :
                this.player = 2;
                this.opposant = 1;
                this.joueur.removeEventListener(this);
                this.joueur.child("j2").setValue("1");
                tab = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
                affichageJoueur.setVisibility(View.VISIBLE);
                affichageJoueur.setText("Joueur 2");
                whoPlay.setText("Tour de l'adversaire");
                // On autorise donc le joueur 1 à jouer :
                peutJouer.setValue("1");

            }else {
                // Cas ou la partie est déjà pleine :
                if(this.player == 0){
                    this.spectator = true;
                    whoPlay.setText("La partie est déjà pleine, revenez plus tard !");
                    affichageJoueur.setVisibility(View.VISIBLE);
                    affichageJoueur.setText("Mode spectateur");
                    this.joueur.removeEventListener(this);
                    peutJouer.removeEventListener(this);
                    opposant = 1;
                    player = 2;
                    tab = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
                    //cases.removeEventListener(this);
                    //gagne.removeEventListener(this);
                }
            }
        }
        if(key.equals("peutjouer")){
            progressBar.setVisibility(View.GONE);
            int peutjouer = Integer.parseInt((String) snapshot.getValue());
            if(peutjouer == this.player){
                // C'est le tour du joueur derriere cette machine :
                whoPlay.setText("A vous de jouer ! ");
                canPlay = true;
            }
        }
        if(key.equals("cases")){
            // La case sur laquel on a cliqué,
            // Map qui recoit l'ensemble du plateau

            if(this.nextGame){
                nextGame = false;
                for(int i=0; i<3; i++){
                    for (int j=0; j<3; j++){
                        tab[i][j] = 0;
                    }
                }
            }

            for(DataSnapshot snapshot1 : snapshot.getChildren()){
                long compare = (long) snapshot1.getValue();
                if(snapshot1.getKey().equals("0")){
                    if(compare == opposant){
                        tab[0][0] = opposant;
                    } else if(compare == player){
                        tab[0][0] = player;
                    }
                }
                if(snapshot1.getKey().equals("1")){
                    if(compare == opposant){
                        tab[0][1] = opposant;
                    }else if(compare == player){
                        tab[0][1] = player;
                    }

                }
                if(snapshot1.getKey().equals("2")){
                    if(compare == opposant){
                        tab[0][2] = opposant;
                    }else if(compare == player){
                        tab[0][2] = player;
                    }
                }
                if(snapshot1.getKey().equals("3")){
                    if(compare == opposant){
                        tab[1][0] = opposant;
                    }else if(compare == player){
                        tab[1][0] = player;
                    }
                }
                if(snapshot1.getKey().equals("4")){
                    if(compare == opposant){
                        tab[1][1] = opposant;
                    }else if(compare == player){
                        tab[1][1] = player;
                    }
                }
                if(snapshot1.getKey().equals("5")){
                    if(compare == opposant){
                        tab[1][2] = opposant;
                    }else if(compare == player){
                        tab[1][2] = player;
                    }
                }
                if(snapshot1.getKey().equals("6")){
                    if(compare == opposant){
                        tab[2][0] = opposant;
                    }else if(compare == player){
                        tab[2][0] = player;
                    }
                }
                if(snapshot1.getKey().equals("7")){
                    if(compare == opposant){
                        tab[2][1] = opposant;
                    }else if(compare == player){
                        tab[2][1] = player;
                    }
                }
                if(snapshot1.getKey().equals("8")){
                    if(compare == opposant){
                        tab[2][2] = opposant;
                    }else if(compare == player){
                        tab[2][2] = player;
                    }
                }
            }
            // Une fois que le tableau est remplis on appel la fonction qui va reload toute nos images :
            reloadImg();
            // Si le joueur n'est pas spéctateur on effectue toutes les actions :
            if(!this.spectator){
                if(isGameWin() == player){
                    gagne.setValue(player);
                    return;
                }
                else if(isGameWin() == opposant){
                    gagne.setValue(opposant);
                    return;
                }
                // Si la table est pleine et qu'on est pas passé dans le if précédent alors il y a matche nul :O
                if(isTabFull()){
                    gagne.setValue(0);
                }
            }
            if(this.spectator){
                isGameWin();
            }

        }
        if(key.equals("gagne")){
            if(!spectator){
                long gagne = (long) snapshot.getValue();
                // Le joueur ayant ce téléphone gagne :
                canPlay = false;
                if(gagne == player){
                    whoPlay.setText("Vous avez gagné !");
                } else if(gagne == opposant){
                    whoPlay.setText("Vous avez perdu");
                }

                if(gagne == 0){
                    whoPlay.setText("Match nul");

                }

                // On enleve tous les listener pour éviter les potentiels problemes
                joueur.removeEventListener(this);
                peutJouer.removeEventListener(this);
                cases.removeEventListener(this);
                this.gagne.removeEventListener(this);
                rootReference.removeValue();
            }
            else{
                nextGame = true;
            }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }

    @Override
    protected void onStart(){
        super.onStart();
        joueur.addValueEventListener(this);
        peutJouer.addValueEventListener(this);
        cases.addValueEventListener(this);
        gagne.addValueEventListener(this);
    }

    // Fonction de click sur la grille
    public void clickOnGrille(View view) {
        if (canPlay) {
            boolean ok = false;
            if (view == grille1) {
                if (tab[0][0] == 0) {
                    ok = true;
                    tab[0][0] = player;
                    cases.child("0").setValue(player);
                }
            }
            if (view == grille2) {
                if (tab[0][1] == 0) {
                    ok = true;
                    tab[0][1] = player;
                    cases.child("1").setValue(player);
                }
            }
            if (view == grille3) {
                if (tab[0][2] == 0) {
                    ok = true;
                    tab[0][2] = player;
                    cases.child("2").setValue(player);
                }
            }
            if (view == grille4) {
                if (tab[1][0] == 0) {
                    ok = true;
                    tab[1][0] = player;
                    cases.child("3").setValue(player);
                }
            }
            if (view == grille5) {
                if (tab[1][1] == 0) {
                    ok = true;
                    tab[1][1] = player;
                    cases.child("4").setValue(player);
                }
            }
            if (view == grille6) {
                if (tab[1][2] == 0) {
                    ok = true;
                    tab[1][2] = player;
                    cases.child("5").setValue(player);
                }
            }
            if (view == grille7) {
                if (tab[2][0] == 0) {
                    ok = true;
                    tab[2][0] = player;
                    cases.child("6").setValue(player);
                }
            }
            if (view == grille8) {
                if (tab[2][1] == 0) {
                    ok = true;
                    tab[2][1] = player;
                    cases.child("7").setValue(player);
                }
            }
            if (view == grille9) {
                if (tab[2][2] == 0) {
                    ok = true;
                    tab[2][2] = player;
                    cases.child("8").setValue(player);
                }
            }
            if(ok){
                this.canPlay = false;
                whoPlay.setText("Tour de l'adversaire");
                peutJouer.setValue(""+opposant);
            }
        }
    }

    public void reloadImg(){

        if(tab[0][0] == 1 ){
            grille1.setBackgroundResource(R.drawable.rond);
        } else if(tab[0][0] == 2){
            grille1.setBackgroundResource(R.drawable.croix);
        } else {
            grille1.setBackgroundResource(R.drawable.casevide);
        }
        if(tab[0][1] == 1 ){
            grille2.setBackgroundResource(R.drawable.rond);
        } else if(tab[0][1] == 2){
            grille2.setBackgroundResource(R.drawable.croix);
        } else {
            grille2.setBackgroundResource(R.drawable.casevide);
        }
        if(tab[0][2] == 1 ){
            grille3.setBackgroundResource(R.drawable.rond);
        } else if(tab[0][2] == 2){
            grille3.setBackgroundResource(R.drawable.croix);
        } else {
            grille3.setBackgroundResource(R.drawable.casevide);
        }
        if(tab[1][0] == 1 ){
            grille4.setBackgroundResource(R.drawable.rond);
        } else if(tab[1][0] == 2){
            grille4.setBackgroundResource(R.drawable.croix);
        }else {
            grille4.setBackgroundResource(R.drawable.casevide);
        }
        if(tab[1][1] == 1 ){
            grille5.setBackgroundResource(R.drawable.rond);
        } else if(tab[1][1] == 2){
            grille5.setBackgroundResource(R.drawable.croix);
        }else {
            grille5.setBackgroundResource(R.drawable.casevide);
        }
        if(tab[1][2] == 1 ){
            grille6.setBackgroundResource(R.drawable.rond);
        } else if(tab[1][2] == 2){
            grille6.setBackgroundResource(R.drawable.croix);
        }else {
            grille6.setBackgroundResource(R.drawable.casevide);
        }
        if(tab[2][0] == 1 ){
            grille7.setBackgroundResource(R.drawable.rond);
        } else if(tab[2][0] == 2){
            grille7.setBackgroundResource(R.drawable.croix);
        }else {
            grille7.setBackgroundResource(R.drawable.casevide);
        }
        if(tab[2][1] == 1 ){
            grille8.setBackgroundResource(R.drawable.rond);
        } else if(tab[2][1] == 2){
            grille8.setBackgroundResource(R.drawable.croix);
        }else {
            grille8.setBackgroundResource(R.drawable.casevide);
        }
        if(tab[2][2] == 1 ){
            grille9.setBackgroundResource(R.drawable.rond);
        } else if(tab[2][2] == 2){
            grille9.setBackgroundResource(R.drawable.croix);
        }else {
            grille9.setBackgroundResource(R.drawable.casevide);
        }
    }


    // Return 0 si personne ne gagne
    // Return 1 si le joueur 1 a gagné la partie
    // Return 2 si le joueur 2 a gagné la partie
    public int isGameWin(){
        int whoWOn = 0;

        // On modifie l'image pour montrer ou l'on a gagné :

        // Lignes :
        if(tab[0][0] == tab[0][1] && tab[0][1] == tab[0][2] && tab[0][0] != 0){
            whoWOn = tab[0][0];
            if(whoWOn == 1){
                grille1.setBackgroundResource(R.drawable.rond2);
                grille2.setBackgroundResource(R.drawable.rond2);
                grille3.setBackgroundResource(R.drawable.rond2);
            } else {
                grille1.setBackgroundResource(R.drawable.croix2);
                grille2.setBackgroundResource(R.drawable.croix2);
                grille3.setBackgroundResource(R.drawable.croix2);
            }
        }
        if(tab[1][0] == tab[1][1] && tab[1][0] == tab[1][2] && tab[1][0] != 0){
            whoWOn = tab[1][0];
            if(whoWOn == 1){
                grille4.setBackgroundResource(R.drawable.rond2);
                grille5.setBackgroundResource(R.drawable.rond2);
                grille6.setBackgroundResource(R.drawable.rond2);
            } else {
                grille4.setBackgroundResource(R.drawable.croix2);
                grille5.setBackgroundResource(R.drawable.croix2);
                grille6.setBackgroundResource(R.drawable.croix2);
            }
        }
        if(tab[2][0] == tab[2][1] && tab[2][0] == tab[2][2] && tab[2][0] != 0){
            whoWOn = tab[2][0];
            if(whoWOn == 1){
                grille7.setBackgroundResource(R.drawable.rond2);
                grille8.setBackgroundResource(R.drawable.rond2);
                grille9.setBackgroundResource(R.drawable.rond2);
            } else {
                grille7.setBackgroundResource(R.drawable.croix2);
                grille8.setBackgroundResource(R.drawable.croix2);
                grille9.setBackgroundResource(R.drawable.croix2);
            }
        }
        // Colonnes :
        if(tab[0][0] == tab[1][0] && tab[0][0] == tab[2][0] && tab[0][0] != 0){
            whoWOn = tab[0][0];
            if(whoWOn == 1){
                grille1.setBackgroundResource(R.drawable.rond2);
                grille4.setBackgroundResource(R.drawable.rond2);
                grille7.setBackgroundResource(R.drawable.rond2);
            } else {
                grille1.setBackgroundResource(R.drawable.croix2);
                grille4.setBackgroundResource(R.drawable.croix2);
                grille7.setBackgroundResource(R.drawable.croix2);
            }
        }
        if(tab[0][1] == tab[1][1] && tab[0][1] == tab[2][1] && tab[0][1] != 0){
            whoWOn = tab[0][1];
            if(whoWOn == 1){
                grille2.setBackgroundResource(R.drawable.rond2);
                grille5.setBackgroundResource(R.drawable.rond2);
                grille8.setBackgroundResource(R.drawable.rond2);
            } else {
                grille2.setBackgroundResource(R.drawable.croix2);
                grille5.setBackgroundResource(R.drawable.croix2);
                grille8.setBackgroundResource(R.drawable.croix2);
            }
        }
        if(tab[0][2] == tab[1][2] && tab[0][2] == tab[2][2] && tab[0][2] != 0){
            whoWOn = tab[0][2];
            if(whoWOn == 1){
                grille3.setBackgroundResource(R.drawable.rond2);
                grille6.setBackgroundResource(R.drawable.rond2);
                grille9.setBackgroundResource(R.drawable.rond2);
            } else {
                grille3.setBackgroundResource(R.drawable.croix2);
                grille6.setBackgroundResource(R.drawable.croix2);
                grille9.setBackgroundResource(R.drawable.croix2);
            }
        }
        // Diagonales :
        if(tab[0][0] == tab[1][1] && tab[0][0] == tab[2][2] && tab[0][0] != 0){
            whoWOn = tab[0][0];
            if(whoWOn == 1){
                grille1.setBackgroundResource(R.drawable.rond2);
                grille5.setBackgroundResource(R.drawable.rond2);
                grille9.setBackgroundResource(R.drawable.rond2);
            } else {
                grille1.setBackgroundResource(R.drawable.croix2);
                grille5.setBackgroundResource(R.drawable.croix2);
                grille9.setBackgroundResource(R.drawable.croix2);
            }
        }
        if(tab[2][0] == tab[1][1] && tab[0][2] == tab[2][0] && tab[2][0] != 0){
            whoWOn = tab[2][0];
            if(whoWOn == 1){
                grille3.setBackgroundResource(R.drawable.rond2);
                grille5.setBackgroundResource(R.drawable.rond2);
                grille7.setBackgroundResource(R.drawable.rond2);
            } else {
                grille3.setBackgroundResource(R.drawable.croix2);
                grille5.setBackgroundResource(R.drawable.croix2);
                grille7.setBackgroundResource(R.drawable.croix2);
            }
        }
        return whoWOn;
    }
    // On regarde si ta table est pleine ou non,
    // Si elle est plein on renvoit true et on arrete la partie :)
    // On se contente de compter le nombre de case pleine
    public boolean isTabFull(){
        int count = 0;

        for (int i = 0; i<3; i++){
            for (int j = 0; j<3; j++){
                if(tab[i][j] != 0){
                    count ++;
                }
            }
        }
        return count == 9;
    }

    // Méthode pour revenir au menu précédent en cliquant sur le boutton retour
    public void retourClick(View view){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

    public void rejouerClick(View view){
        Intent intent = new Intent(this, GameVsJoueur.class);
        //rootReference.removeValue();
        startActivity(intent);
    }

    public void resetClick(View view){
        rootReference.removeValue();
        Intent intent = new Intent(this, GameVsJoueur.class);
        startActivity(intent);
    }

}