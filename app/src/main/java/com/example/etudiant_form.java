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
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar progressBar;



    Spinner spinnerSpecialites, spinnerOptions, spinnerEtablissement, spinnerSection;

    ArrayList<String> specialitesList = new ArrayList<>();
    ArrayList<String> optionsList = new ArrayList<>();
    ArrayList<String> etablissementList = new ArrayList<>();
    ArrayList<String> sectionList = new ArrayList<>();

    ArrayAdapter<String> specialitesAdapter;
    ArrayAdapter<String> optionsAdapter;
    ArrayAdapter<String> etablissementAdapter;
    ArrayAdapter<String> sectionAdapter;

    Map<String, String> optionsMap = new HashMap<String, String>();
    Map<String, String> etablissementMap = new HashMap<String, String>();
    Map<String, String> sectionMap = new HashMap<String, String>();

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiant_form);

        textInputEditTextNom = findViewById(R.id.nom);
        textInputEditTextPrenom = findViewById(R.id.prenom);
        textInputEditTextMail = findViewById(R.id.username_login);
        textInputEditTextEtablissement = findViewById(R.id.password_login);

        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.loginText);


        requestQueue = Volley.newRequestQueue(this);
        spinnerSpecialites = findViewById(R.id.spinner_specialites);
        spinnerOptions = findViewById(R.id.spinner_options);
        spinnerEtablissement = findViewById(R.id.spinner_etablissement);
        spinnerSection = findViewById(R.id.spinner_section);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                ConfigUrl.KEY_URL_SPECIALITE, null, new Response.Listener<JSONObject>() {
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

        JsonObjectRequest jsonObjectRequest_section = new JsonObjectRequest(Request.Method.POST,
                ConfigUrl.KEY_URL_SECTION, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    sectionMap.clear();
                    JSONArray jsonArray = response.getJSONArray("sections");
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String specialiteLibelle = jsonObject.optString("sections_libelle");
                        String sectionsId = jsonObject.optString("sections_id");

                        sectionMap.put(specialiteLibelle, sectionsId);
                        sectionList.add(specialiteLibelle);
                        sectionAdapter = new ArrayAdapter<>(etudiant_form.this,
                                android.R.layout.simple_spinner_item, sectionList);
                        sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        spinnerSection.setAdapter(sectionAdapter);
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
        requestQueue.add(jsonObjectRequest_section);
        spinnerSection.setOnItemSelectedListener(this);

        JsonObjectRequest jsonObjectRequest_etablissement = new JsonObjectRequest(Request.Method.POST,
                ConfigUrl.KEY_URL_ETABLISSEMENT, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    etablissementMap.clear();
                    JSONArray jsonArray = response.getJSONArray("etablissements");
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String etablissementLibelle = jsonObject.optString("etablissement_libelle");
                        String etablissementId = jsonObject.optString("etablissement_id");

                        etablissementMap.put(etablissementLibelle, etablissementId);
                        etablissementList.add(etablissementLibelle);
                        etablissementAdapter = new ArrayAdapter<>(etudiant_form.this,
                                android.R.layout.simple_spinner_item, etablissementList);
                        etablissementAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        spinnerEtablissement.setAdapter(etablissementAdapter);
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
        requestQueue.add(jsonObjectRequest_etablissement);
        spinnerEtablissement.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(final AdapterView<?> adapterView, View view, int position, final long id) {
        if(adapterView.getId() == R.id.spinner_specialites){
            optionsList.clear();
            String selectedSpecialites = adapterView.getSelectedItem().toString();

            requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    ConfigUrl.KEY_URL_OPTION_LIBELLE+selectedSpecialites, null, new Response.Listener<JSONObject>() {
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
            specialite = optionsMap.get(adapterView.getSelectedItem().toString());
        }

        if(adapterView.getId() == R.id.spinner_section){

            section = sectionMap.get(adapterView.getSelectedItem().toString());
        }

        if(adapterView.getId() == R.id.spinner_etablissement){

            etablissement = etablissementMap.get(adapterView.getSelectedItem().toString());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void save(View view){
        nom = textInputEditTextNom.getText().toString().trim();
        prenom = textInputEditTextPrenom.getText().toString().trim();
        mail = textInputEditTextMail.getText().toString().trim();

        if(!nom.equals("") && !prenom.equals("") && !mail.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigUrl.KEY_URL_ADD_ETUDIANT, new Response.Listener<String>() {
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