package com.example;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class prof_accueil extends AppCompatActivity {
    //private static String url = "http://192.168.0.22/android/rostand-visit/select_etudiants.php";
    private static String url = "http://172.30.31.1/rostand-visit/select_etudiants.php";

    TextView nbEtudiants;
    EditText editTextannee, editTextDiplome;
    Button buttonfetch;
    ListView lvEtudiant;
    String annee;
    String id, nom, prenom, mail, etablissement, diplome, visite, specialite;
    ProgressDialog mProgressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_accueil);

        editTextannee = findViewById(R.id.etAnnee);
        editTextDiplome = findViewById(R.id.etDiplome);
        buttonfetch = findViewById(R.id.btnfetch);
        lvEtudiant = findViewById(R.id.lvEtudiant);
        nbEtudiants = findViewById(R.id.accueil_prof_nb_etu);
        buttonfetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                annee = editTextannee.getText().toString().trim();
                diplome = editTextDiplome.getText().toString().trim();

                InputMethodManager imm = (InputMethodManager)getSystemService(prof_accueil.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextannee.getWindowToken(), 0);

                if (annee.equals("")){
                    Toast.makeText(prof_accueil.this, "Entrez une année.", Toast.LENGTH_SHORT).show();
                }else {

                    GetMatchData();
                }

            }
        });
    }

    private void GetMatchData() {

        annee = editTextannee.getText().toString().trim();
        diplome = editTextDiplome.getText().toString().trim();

        mProgressDialog = new ProgressDialog(prof_accueil.this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMessage(getString(R.string.progress_detail));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgress(0);
        mProgressDialog.setProgressNumberFormat(null);
        mProgressDialog.setProgressPercentFormat(null);
        mProgressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")) {

                            showJSON(response);
                            mProgressDialog.dismiss();

                        } else {

                            showJSON(response);
                            mProgressDialog.dismiss();


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(prof_accueil.this, ""+error, Toast.LENGTH_LONG).show();
                        mProgressDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("etudiant_annee", annee);
                map.put("etudiant_diplome", diplome);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("etudiants");

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString("etudiant_id");
                String nom = jo.getString("etudiant_nom");
                String prenom = jo.getString("etudiant_prenom");
                String mail = jo.getString("etudiant_mail");
                String etablissement = jo.getString("etudiant_etablissement");
                String section = jo.getString("etudiant_section");
                String visite = jo.getString("etudiant_visite");
                String specialite = jo.getString("etudiant_libelle");

                Log.d("---------------- test --------------------------", "showJSON:" + jo);




                final HashMap<String, String> etudiants = new HashMap<>();
                etudiants.put("etudiant_nom",  nom);
                etudiants.put("etudiant_prenom", prenom);
                etudiants.put("etudiant_mail", mail);
                etudiants.put("etudiant_etablissement", etablissement);
                etudiants.put("etudiant_section", section);
                etudiants.put("etudiant_visite", visite);
                etudiants.put("etudiant_libelle", specialite);

                list.add(etudiants);

            }
            Log.d("Nb", "Nombre " + list.size());
            nbEtudiants.setText("Résultat de la recherche: " + list.size() + " étudiant(e)s.");

        } catch (JSONException e) {
            e.printStackTrace();
            nbEtudiants.setText("Vérifiez vos critères de recherche...");
        }
        ListAdapter adapter = new SimpleAdapter(
                prof_accueil.this, list, R.layout.single_item,
                new String[]{"etudiant_nom", "etudiant_prenom", "etudiant_mail", "etudiant_etablissement", "etudiant_section", "etudiant_visite", "etudiant_libelle"},
                new int[]{R.id.item_nom, R.id.item_prenom, R.id.item_mail, R.id.item_etablissement, R.id.item_section, R.id.item_visite, R.id.item_specialite});

        lvEtudiant.setAdapter(adapter);

    }

    public void logout(View view){
        Intent intent = new Intent(this, garde.class);
        startActivity(intent);
        finish();
    }

    public void profile(View view){
        Intent intent = new Intent(this, update_password.class);
        startActivity(intent);
        finish();
    }



}