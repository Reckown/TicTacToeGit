package com.example.tpfinal.IA;

// Class servant à crée l'arbre des possibilités de l'IA, qui va ensuite etre utiliser dans l'algorithme MinMax


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Arrays;
import java.util.Random;


public class Tree {
    int [][]tab;
    // Le tableau courant de ce noeud de l'arbre,

    int note;
    // La note de ce noeud de l'arbre,
    // Note = 1 si jamais l'IA va gagner,
    // Note = -1 si l'ia va perdre,
    // Note = 0 si la partie est nul

    Tree[] trees;
    // La décendance de ce noeud,

    int length;

    int ia;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Tree(int[][] partie, int ia, int turn){
        this.ia = ia;
        // ia contient si l'ia joue les 1 ou les 2

        // Turn contient le tour du joueur
        // Si l'IA doit simuler son tour alors turn = 2
        // Si l'IA doit simuler le tour de l'humain alors turn = 1

        //On met la partie dans le contenu :
        tab = new int[3][3];
        for(int i = 0; i<3; i++){
            System.arraycopy(partie[i], 0, tab[i], 0, 3);
        }
        // Notation de la position :
        if (note() == 0){
            note = 0;
        } else if(note() == ia){
            note = 1;
        } else {
            note = -1;
        }

        // Appel des fils :

        //On commance par compter le nombre de fils qu'a notre arbre :
        int nbChild = 0;
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                if(tab[i][j] == 0){
                    nbChild++;
                }
            }
        }
        // On initialise un tableau à la bonne taille
        length = nbChild;
        trees = new Tree[nbChild];
        int k = 0;

        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                if(tab[i][j] == 0){
                    // Partie qui va etre utiliser dans le prochain noeud :
                    int[][] copy = Arrays.stream(partie).map(int[]::clone).toArray(int[][]::new);
                    copy[i][j] = turn;
                    if(turn == 1){
                        trees[k] = new Tree(copy, ia, 2);
                        k++;
                    } else{
                        trees[k] = new Tree(copy, ia, 1);
                        k++;
                    }
                }
            }
        }
    }

    public int minMax(Tree node, boolean maxPlayer, int depth){
        //System.out.println("I = "+m);
        int value;
        if(depth == 0 || node.length == 0){
            //System.out.println("Sheeesh");
            return node.note;
        }
        // Cas ou l'on veut maximiser le tout
        if(maxPlayer){
            value = -3;
            for(Tree tree  : node.trees){
                // Condition d'arret, l'ia est perdente à ce point inutile d'aller chercher plus loin
                if(node.note == -1){
                    return node.note;
                }
                value = max(value, minMax(tree, false, depth -1));
            }
        } else {
            value = 3;
            //Cas ou l'on veut minimiser
            for(Tree tree  : node.trees){
                // Condition d'arret, l'ia est gagnante à ce point inutile d'aller chercher plus loin
                if(node.note == 1){
                    return node.note;
                }
                value = min(value, minMax(tree, true, depth -1));
            }
        }
        node.note = value;
        return value;
    }



    // Méthode pour vraiment jouer le coup
    public int[][] play(int depth){
        int[][] ret = new int[3][3];



        // set up toutes les notes :
        minMax(this, true, depth);

        // Table dans laquel on va stocker l'ensemble des possibilités :
        Tree[] tabTree= new Tree[9];
        // Itérateur de la table
        int i = 0;

        for(Tree tree : trees){
            // On cherche les notes les plus hautes :
            if(tree.note == 1){
                tabTree[i] = tree;
                i++;
            }
        }
        Random random = new Random();
        if(i != 0){
            int value = random.nextInt(i);
            return tabTree[value].tab;
        }

        tabTree = new Tree[9];
        i=0;

        //Cas ou l'on va faire matche nul :

        for (Tree tree : trees){
            if(tree.note == 0){
                tabTree[i] = tree;
                i++;
            }
        }
        random = new Random();
        if(i != 0){
            int value = random.nextInt(i);
            return tabTree[value].tab;
        }

        // Cas ou l'ia va perdre (en théorie c'est pas trop possible mais sait on jamais :p)
        for(Tree tree : trees){
            if(tree.note == -1){
                tabTree[i] = tree;
                i++;
            }
        }
        random = new Random();
        if(i != 0){
            int value = random.nextInt(i);
            return tabTree[value].tab;
        }
        // En théorie on est jamais supposé arriver jusqu'a ici mais bon :
        return tab;
    }

    // Savoir le nombre max entre a et b :
    public int max(int i, int j){
        if(i>j){
            return i;
        } else {
            return j;
        }
    }

    // Savoir le nombre min entre a et b
    public int min(int i, int j){
        if(i<j){
            return i;
        } else {
            return j;
        }
    }


    // Algorithme minMax pour savoir quel coup jouer :


    public int note(){
        int note = 0;

        // Lignes :
        if(tab[0][0] == tab[0][1] && tab[0][1] == tab[0][2] && tab[0][0] != 0){
            note = tab[0][0];
        }
        if(tab[1][0] == tab[1][1] && tab[1][0] == tab[1][2]&& tab[1][0] != 0){
            note = tab[1][0];
        }
        if(tab[2][0] == tab[2][1] && tab[2][0] == tab[2][2]&& tab[2][0] != 0){
            note = tab[2][0];
        }
        // Colonnes :
        if(tab[0][0] == tab[1][0] && tab[0][0] == tab[2][0]&& tab[0][0] != 0){
            note = tab[0][0];
        }
        if(tab[0][1] == tab[1][1] && tab[0][1] == tab[2][1]&& tab[0][1] != 0){
            note = tab[0][1];
        }
        if(tab[0][2] == tab[1][2] && tab[0][2] == tab[2][2]&& tab[0][2] != 0){
            note = tab[0][2];
        }
        // Diagonales :
        if(tab[0][0] == tab[1][1] && tab[0][0] == tab[2][2]&& tab[0][0] != 0){
            note = tab[0][0];
        }
        if(tab[2][0] == tab[1][1] && tab[0][2] == tab[2][0]&& tab[2][0] != 0){
            note = tab[2][0];
        }
        return note;
    }
}
