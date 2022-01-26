package com.example.tpfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MenuIA extends AppCompatActivity {

    private Spinner spinner;
    private static final String[] type = {
            "Ronds", "Croix"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ia);
        this.spinner = findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<String >(this, android.R.layout.simple_spinner_dropdown_item, type));

    }

    // Lancer la partie, on regarde sur quel boutton l'utilisateur a cliquer et si il veut les ronds ou les croix.
    // On envoie toutes les données dans l'intent
    public void lunchGame(View view){
        Intent intent = new Intent(this, GameIA.class);
        String type = MenuIA.type[spinner.getSelectedItemPosition()];
        intent.putExtra("type", type);
        // L'utilisateur veut lancer en mode facile :
        if(view == findViewById(R.id.facileButton)){
            intent.putExtra("difficulty", "facile");
            startActivity(intent);
        } else if(view == findViewById(R.id.normalButton)){
            intent.putExtra("difficulty", "normale");
        } else if(view == findViewById(R.id.difficileButton)){
            intent.putExtra("difficulty", "difficile");
        }
        // On start l'activité suivante avec les bonnes infos dans l'intent
        startActivity(intent);
    }

    public void retour(View view){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
}