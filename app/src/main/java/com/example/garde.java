package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class garde extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garde);
    }

    public void etudiantForm(View view){
        Intent intent = new Intent(this, etudiant_form.class);
        startActivity(intent);
        finish();
    }

    public void login_prof(View view){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    public void rgpd(View view){
        Intent intent = new Intent(this, rgpd.class);
        startActivity(intent);
        finish();
    }
}