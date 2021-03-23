package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.R.layout.single_item;

public class prof_accueil extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String id, nom, prenom, mail, etablissement, section , visite, specialite;

    //private static String url = "http://192.168.0.22/android/rostand-visit/select_etudiants.php";
    String url = "http://172.30.31.1/rostand-visit/select_etudiants.php";

    //ListView lvEtudiant = findViewById(R.id.lvEtudiant);
    private static final String TAG = "---------------------------- Prof Accueil ----------------------------";


    ArrayList<String> etudiantsList = new ArrayList<>();
    ArrayAdapter etudiantsAdapter;

    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_accueil);

        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("etudiants");
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.optString("etudiant_id");
                        etudiantsList.add(id);

                        String nom = jsonObject.optString("etudiant_nom");
                        etudiantsList.add(nom);

                        String prenom = jsonObject.optString("etudiant_prenom");
                        etudiantsList.add(prenom);

                        String mail = jsonObject.optString("etudiant_mail");
                        etudiantsList.add(mail);

                        String etablissement = jsonObject.optString("etudiant_etablissement");
                        etudiantsList.add(etablissement);

                        String section = jsonObject.optString("etudiant_section");
                        etudiantsList.add(section);

                        String visite = jsonObject.optString("etudiant_visite");
                        etudiantsList.add(visite);

                        String specialite = jsonObject.optString("etudiant_specialite");
                        etudiantsList.add(specialite);

                        Log.d(TAG, "onCreate: " + etudiantsList);



/*                        etudiantsAdapter = new ArrayAdapter<>(prof_accueil.this,
                                android.R.layout.simple_list_item_1, etudiantsList);
                        etudiantsAdapter.setDropDownViewResource(android.R.layout.activity_list_item);*/
                        //lvEtudiant.setAdapter(etudiantsAdapter);
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
        Log.d(TAG, "onCreate: " + etudiantsList);
        //lvEtudiant.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}