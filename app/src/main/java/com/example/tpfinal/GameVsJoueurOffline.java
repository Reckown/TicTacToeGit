package com.example.tpfinal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class GameVsJoueurOffline extends AppCompatActivity {

    private TextView textViewIA;
    private TextView textViewJoueur;
    // Grille
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

    private int player; // Joueur actuel 1 ou 2

    // Boolean pour arreter de jouer lorsque la partie est fini.
    // Tant qu'ilk est à true on peut continuer
    boolean ok;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_vs_joueur_offline);

        // Initialisation
        this.textViewIA = findViewById(R.id.textView11);
        this.textViewIA.setText("Partie locale : ");
        this.textViewJoueur = findViewById(R.id.textView12);
        player = 1;
        this.textViewJoueur.setText("Joueur "+player);

        // On met la grille dans la classe :
        this.grille1 = findViewById(R.id.grille1o);
        this.grille2 = findViewById(R.id.grille2o);
        this.grille3 = findViewById(R.id.grille3o);
        this.grille4 = findViewById(R.id.grille4o);
        this.grille5 = findViewById(R.id.grille5o);
        this.grille6 = findViewById(R.id.grille6o);
        this.grille7 = findViewById(R.id.grille7o);
        this.grille8 = findViewById(R.id.grille8o);
        this.grille9 = findViewById(R.id.grille9o);

        tab = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        this.ok = true;
    }

    // Méthode qui va etre appeler apres chaque tour d'un des joueurs, va servir à reload l'interface graphique
    // En mettant le coup qui vient d'etre joué.
    public void reloadImg(){
        if(tab[0][0] == 1 ){
            grille1.setBackgroundResource(R.drawable.rond);
        } else if(tab[0][0] == 2){
            grille1.setBackgroundResource(R.drawable.croix);
        }
        if(tab[0][1] == 1 ){
            grille2.setBackgroundResource(R.drawable.rond);
        } else if(tab[0][1] == 2){
            grille2.setBackgroundResource(R.drawable.croix);
        }
        if(tab[0][2] == 1 ){
            grille3.setBackgroundResource(R.drawable.rond);
        } else if(tab[0][2] == 2){
            grille3.setBackgroundResource(R.drawable.croix);
        }
        if(tab[1][0] == 1 ){
            grille4.setBackgroundResource(R.drawable.rond);
        } else if(tab[1][0] == 2){
            grille4.setBackgroundResource(R.drawable.croix);
        }
        if(tab[1][1] == 1 ){
            grille5.setBackgroundResource(R.drawable.rond);
        } else if(tab[1][1] == 2){
            grille5.setBackgroundResource(R.drawable.croix);
        }
        if(tab[1][2] == 1 ){
            grille6.setBackgroundResource(R.drawable.rond);
        } else if(tab[1][2] == 2){
            grille6.setBackgroundResource(R.drawable.croix);
        }
        if(tab[2][0] == 1 ){
            grille7.setBackgroundResource(R.drawable.rond);
        } else if(tab[2][0] == 2){
            grille7.setBackgroundResource(R.drawable.croix);
        }
        if(tab[2][1] == 1 ){
            grille8.setBackgroundResource(R.drawable.rond);
        } else if(tab[2][1] == 2){
            grille8.setBackgroundResource(R.drawable.croix);
        }
        if(tab[2][2] == 1 ){
            grille9.setBackgroundResource(R.drawable.rond);
        } else if(tab[2][2] == 2){
            grille9.setBackgroundResource(R.drawable.croix);
        }
    }

    // Si l'utilisateur clique sur la g
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void clickOnGrille(View view){

        // Permet de savoir si on peut continuer ou pas (si le click sur la grille c'est bien passé)
        boolean continuer = false;

        // Est ce qu'on peut continuer la partie?
        if(!ok){
            return;
        }
        if(view == grille1){
            if(tab[0][0] == 0){
                tab[0][0] = player;
                continuer = true;
            }
        }
        if(view == grille2){
            if(tab[0][1] == 0){
                tab[0][1] = player;
                continuer = true;
            }
        }
        if(view == grille3){
            if(tab[0][2] == 0){
                tab[0][2] = player;
                continuer = true;
            }
        }
        if(view == grille4){
            if(tab[1][0] == 0){
                tab[1][0] = player;
                continuer = true;
            }
        }
        if(view == grille5){
            if(tab[1][1] == 0){
                tab[1][1] = player;
                continuer = true;
            }
        }
        if(view == grille6){
            if(tab[1][2] == 0){
                tab[1][2] = player;
                continuer = true;
            }
        }
        if(view == grille7){
            if(tab[2][0] == 0){
                tab[2][0] = player;
                continuer = true;
            }
        }
        if(view == grille8){
            if(tab[2][1] == 0){
                tab[2][1] = player;
                continuer = true;
            }
        }
        if(view == grille9){
            if(tab[2][2] == 0){
                tab[2][2] = player;
                continuer = true;
            }
        }
        if(continuer){
            reloadImg();
            if(isGameWin() != 0){
                textViewJoueur.setText("Le joueur "+player+" a gagné !");
                ok = false;
                return;
            }
            if(isTabFull()){
                textViewJoueur.setText("Match nul");
                ok = false;
                return;
            }
            if (player == 1) {
                player = 2;
            } else if (player == 2) {
                player = 1;
            }
            textViewJoueur.setText("Joueur "+player);
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

    // Si l'utilisateur veut relancer une partie, par obliger que la partie actuel soit fini
    public void rejouerClick(View view){
        Intent intent = new Intent(this, GameVsJoueurOffline.class);
        startActivity(intent);
    }

    // Pour que l'utilisateur puisse revenir au menu précédent
    public void retourClick(View view){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

}