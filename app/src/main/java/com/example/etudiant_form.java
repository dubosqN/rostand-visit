package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

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
        textInputEditTextMail = findViewById(R.id.mail);
        textInputEditTextEtablissement = findViewById(R.id.etablissement);
        textInputEditTextSection = findViewById(R.id.section);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.loginText);
        progressBar = findViewById(R.id.progress);

        final Spinner spinner = findViewById(R.id.spinner_section);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.section_choice, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //spinner.setOnItemClickListener(this);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String nom, prenom, mail, etablissement, section;

                nom = String.valueOf(textInputEditTextNom.getText());
                prenom = String.valueOf(textInputEditTextPrenom.getText());
                mail = String.valueOf(textInputEditTextMail.getText());
                etablissement = String.valueOf(textInputEditTextEtablissement.getText());
                //section = String.valueOf(textInputEditTextSection.getText());
                section = String.valueOf(spinner.getSelectedItem().toString());

                if(!nom.equals("") && !prenom.equals("") && !mail.equals("") && !etablissement.equals("") && !section.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[5];
                            field[0] = "nom";
                            field[1] = "prenom";
                            field[2] = "mail";
                            field[3] = "etablissement";
                            field[4] = "section";
                            //Creating array for data
                            String[] data = new String[5];
                            data[0] = nom;
                            data[1] = prenom;
                            data[2] = mail;
                            data[3] = etablissement;
                            if (section.equals("SISR")){
                                data[4] = String.valueOf(2);
                            }
                            else if (section.equals("SLAM")){
                                data[4] = String.valueOf(1);
                            }

                            PutData putData = new PutData("http://192.168.0.22/android/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();

                                    String toUtf8 = StringFormatter.convertUTF8ToString((result));

                                    if(result.equals("Enregistrement effectué.")){
                                        Toast.makeText(getApplicationContext(), toUtf8, Toast.LENGTH_SHORT).show();
                                        Log.d("rostand-visit",result);
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Log.d("rostand-visit",result);
                                        Toast.makeText(getApplicationContext(), toUtf8, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "Vous devez effectuer la saisie de tous les champs.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}