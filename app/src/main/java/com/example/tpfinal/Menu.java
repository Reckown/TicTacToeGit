package com.example.tpfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void clickOrdinateur(View view){
        Intent intent = new Intent(this, MenuIA.class);
        startActivity(intent);
    }

    public void clickJouerEnLigne(View view){
        Intent intent = new Intent(this, GameVsJoueur.class);
        startActivity(intent);
    }

    public void clickJouerEnLocal(View view){
        Intent intent = new Intent(this, GameVsJoueurOffline.class);
        startActivity(intent);
    }

}