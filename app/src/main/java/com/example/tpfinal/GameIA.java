package com.example.tpfinal;

import static com.example.tpfinal.IA.randomPicker.tabrandom;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tpfinal.IA.Tree;
import com.example.tpfinal.IA.randomPicker;

public class GameIA extends AppCompatActivity {

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

    // Si la variable est à true alors c'est le tour du joueur,
    // Sinon c'est le tour de l'IA
    // Evite que le joueur joue 2 fois de suite
    Boolean playerTurn;

    // Stock si l'ia va jouer les ronds ou jouer les croix
    int ia;
    int player;

    // Contient la difficulté de l'IA :
    // 1 : joue au hasard,
    // 2 : Minmax depth = 2
    // 3 : minmax avec une profondeur maximal, impossible à battre
    int difficulty;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_ia);

        // Récupération des intents avec les infos sur la partie
        String iaDiff = getIntent().getStringExtra("difficulty");
        String type = getIntent().getStringExtra("type");

        switch (iaDiff) {
            case "facile":
                this.difficulty = 1;
                break;
            case "normale":
                this.difficulty = 2;
                break;
            case "difficile":
                this.difficulty = 3;
                break;
        }

        this.textViewIA = findViewById(R.id.textViewVsIa);
        this.textViewIA.setText("VS IA : "+iaDiff);

        this.textViewJoueur = findViewById(R.id.textViewJoueur);
        if(type.equals("Ronds")){
            this.textViewJoueur.setText("Joueur 1");
            ia = 2;
            player = 1;
            playerTurn = true;
        } else if(type.equals("Croix")){
            this.textViewJoueur.setText("Joueur 2");
            playerTurn = false;
            ia = 1;
            player = 2;
        }

        // On met la grille dans la classe :
        this.grille1 = findViewById(R.id.grille1);
        this.grille2 = findViewById(R.id.grille2);
        this.grille3 = findViewById(R.id.grille3);
        this.grille4 = findViewById(R.id.grille4);
        this.grille5 = findViewById(R.id.grille5);
        this.grille6 = findViewById(R.id.grille6);
        this.grille7 = findViewById(R.id.grille7);
        this.grille8 = findViewById(R.id.grille8);
        this.grille9 = findViewById(R.id.grille9);

        tab = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};

        if(!playerTurn){
            game();
        }

    }

    // Méthode qui va gerer la partie entre le joueur et l'IA
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void game(){
        // Tour de l'IA
        // On choisit la difficulté
        if(!playerTurn){
            //cas ou la table est completement vide, on met juste une croix au hasard dessus pas besoin de calculer :
            boolean tabEmpty = true;
            for(int i = 0; i<3; i++){
                for(int j = 0; j<3; j++){
                    if (tab[i][j] != 0) {
                        tabEmpty = false;
                        break;
                    }
                }
            }
            if(tabEmpty){
                tab = randomPicker.tabrandom(tab, ia);
            } else {
                if(difficulty == 1){
                    tab = tabrandom(tab, ia);
                } else if(difficulty == 2){
                    Tree tree = new Tree(tab, ia, ia);
                    tab = tree.play(2);
                } else {
                    Tree tree = new Tree(tab, ia, ia);
                    tab = tree.play(10);
                }
            }

            playerTurn = true;
            reloadImg();
            if(isGameWin() == ia){
                textViewJoueur.setText("Vous avez perdu :(");
                playerTurn = false;
            }

            if(isTabFull()){
                textViewJoueur.setText("Match nul");
                playerTurn = false;
            }
            // C'est le tour du joueur apres
        }

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
        if(playerTurn){
            boolean ok = false;
            while (!ok){
                if(view == grille1){
                    if(tab[0][0] == 0){
                        ok = true;
                        tab[0][0] = player;
                    }
                }
                if(view == grille2){
                    if(tab[0][1] == 0){
                        ok = true;
                        tab[0][1] = player;
                    }
                }
                if(view == grille3){
                    if(tab[0][2] == 0){
                        ok = true;
                        tab[0][2] = player;
                    }

                }
                if(view == grille4){
                    if(tab[1][0] == 0){
                        ok = true;
                        tab[1][0] = player;
                    }
                }
                if(view == grille5){
                    if(tab[1][1] == 0){
                        ok = true;
                        tab[1][1] = player;

                    }
                }
                if(view == grille6){
                    if(tab[1][2] == 0){
                        ok = true;
                        tab[1][2] = player;

                    }
                }
                if(view == grille7){
                    if(tab[2][0] == 0){
                        ok = true;
                        tab[2][0] = player;
                    }
                }
                if(view == grille8){
                    if(tab[2][1] == 0){
                        ok = true;
                        tab[2][1] = player;
                    }
                }
                if(view == grille9){
                    if(tab[2][2] == 0){
                        ok = true;
                        tab[2][2] = player;
                    }
                }
            }
            // Si on arrive ici alors l'humain à jouer :
            // On peut donc reload toutes les images,
            // Et regarder si l'humain a gagné
            reloadImg();
            if(isGameWin() == player){
                textViewJoueur.setText("Vous avez gagné bravo !!");
                playerTurn = false;
                return;
            }
            if(isTabFull()){
                textViewJoueur.setText("Match nul");
                playerTurn = false;
                return;
            }
            playerTurn = false;
            game();
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

        Intent intent = new Intent(this, GameIA.class);
        intent.putExtra("type", getIntent().getStringExtra("type"));
        intent.putExtra("difficulty", getIntent().getStringExtra("difficulty"));

        startActivity(intent);
    }

    // Pour que l'utilisateur puisse revenir au menu précédent
    public void retourClick(View view){
        Intent intent = new Intent(this, MenuIA.class);
        startActivity(intent);
    }

}