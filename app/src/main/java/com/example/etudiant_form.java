package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class etudiant_form extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextInputEditText textInputEditTextNom, textInputEditTextPrenom, textInputEditTextMail, textInputEditTextEtablissement, textInputEditTextSection;
    String nom, prenom, mail, etablissement, section, specialite;
    //String specialite;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar progressBar;



    Spinner spinnerSpecialites, spinnerOptions;
    ArrayList<String> specialitesList = new ArrayList<>();
    ArrayList<String> optionsList = new ArrayList<>();

    ArrayAdapter<String> specialitesAdapter;
    ArrayAdapter<String> optionsAdapter;

    Map<String, String> optionsMap = new HashMap<String, String>();

    RequestQueue requestQueue;
    String url_add = "http://192.168.0.22/android/rostand-visit/add_etudiant.php";



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


        requestQueue = Volley.newRequestQueue(this);
        spinnerSpecialites = findViewById(R.id.spinner_specialites);
        spinnerOptions = findViewById(R.id.spinner_options);


        String url = "http://192.168.0.22/android/rostand-visit/specialite.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("specialites");
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String specialiteLibelle = jsonObject.optString("specialite_libelle");
                        specialitesList.add(specialiteLibelle);
                        specialitesAdapter = new ArrayAdapter<>(etudiant_form.this,
                                android.R.layout.simple_spinner_item, specialitesList);
                        specialitesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        spinnerSpecialites.setAdapter(specialitesAdapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
        spinnerSpecialites.setOnItemSelectedListener(this);
        spinnerOptions.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(final AdapterView<?> adapterView, View view, int position, final long id) {
        if(adapterView.getId() == R.id.spinner_specialites){
            optionsList.clear();
            String selectedSpecialites = adapterView.getSelectedItem().toString();
            String url = "http://192.168.0.22/android/rostand-visit/option.php?specialite_libelle="+ selectedSpecialites;
            requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("options");
                        optionsMap.clear();
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String optionsLibelle = jsonObject.optString("option_libelle");
                            String optionId = jsonObject.optString("option_id");

                            optionsMap.put(optionsLibelle, optionId);
                            optionsList.add(optionsLibelle);
                            optionsAdapter = new ArrayAdapter<>(etudiant_form.this,
                                    android.R.layout.simple_spinner_item, optionsList);
                            optionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            spinnerOptions.setAdapter(optionsAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            requestQueue.add(jsonObjectRequest);
        }

        if(adapterView.getId() == R.id.spinner_options){
            specialite = optionsMap.get(adapterView.getSelectedItem().toString());;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void save(View view){
        nom = textInputEditTextNom.getText().toString().trim();
        prenom = textInputEditTextPrenom.getText().toString().trim();
        mail = textInputEditTextMail.getText().toString().trim();
        etablissement = textInputEditTextEtablissement.getText().toString().trim();
        section = textInputEditTextSection.getText().toString().trim();
        //specialite

        if(!nom.equals("") && !prenom.equals("") && !mail.equals("") && !section.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_add, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("success")) {
                        Toast.makeText(etudiant_form.this, "Vous avez été ajouté avec succès...", Toast.LENGTH_SHORT).show();
                    } else if (response.equals("error")) {
                        Toast.makeText(etudiant_form.this, "Il y a eu un problème...", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    StringFormatter formatter = new StringFormatter();
                    Map<String, String> data = new HashMap<>();
                    data.put("nom", nom);
                    data.put("prenom", prenom);
                    data.put("mail", mail);
                    data.put("etablissement", etablissement);
                    data.put("section", section);
                    data.put("specialite", specialite);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
        else {
            Toast.makeText(this, "Un ou plusieurs champs sont vides !", Toast.LENGTH_SHORT).show();
        }

    }

    public void login_prof(View view){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}