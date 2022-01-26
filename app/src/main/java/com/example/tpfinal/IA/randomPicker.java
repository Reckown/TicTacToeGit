package com.example.tpfinal.IA;

import java.util.Random;

public class randomPicker {
    public static int[][] tabrandom(int[][] tab, int ia){
        // On compte le nombre de case vide puis on choisi un nombre entre aléatoire et on place une
        // case ici.

        int count = 0;
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                if(tab[i][j] == 0){
                    count++;
                }
            }

        }
        Random random = new Random();
        int value = random.nextInt(count);

        count = 0;
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                if(tab[i][j] == 0){
                    if(value == count){
                        tab[i][j] = ia;
                    }
                    count++;
                }
            }
        }
        return tab;
    }

}
