package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class etudiant_form extends AppCompatActivity{

    TextInputEditText textInputEditTextNom, textInputEditTextPrenom, textInputEditTextMail, textInputEditTextEtablissement, textInputEditTextSection;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiant_form);

        textInputEditTextNom = findViewById(R.id.nom);
        textInputEditTextPrenom = findViewById(R.id.prenom);
        textInputEditTextMail = findViewById(R.id.username_login);
        textInputEditTextEtablissement = findViewById(R.id.password_login);
        textInputEditTextSection = findViewById(R.id.section);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.loginText);
        progressBar = findViewById(R.id.progress);

    }

    public void login_prof(View view){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}