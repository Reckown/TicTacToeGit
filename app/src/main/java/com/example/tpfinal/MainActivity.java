package com.example.tpfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity {

    EditText textNum;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        spinner = findViewById(R.id.home_spinner_country);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));
        textNum = findViewById(R.id.editTextPhone);
    }

    public void click(View view){
        String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
        String number = textNum.getText().toString().trim();

        if(number.isEmpty() || number.length() < 8){
            textNum.setError("Entrez un numéro de téléphone valide");
            return;
        }

        String phoneNumer = "+"+code+number;
        Intent intent = new Intent(MainActivity.this, VerifyNumber.class);
        intent.putExtra("phoneNumber", phoneNumer);
        startActivity(intent);
    }

}