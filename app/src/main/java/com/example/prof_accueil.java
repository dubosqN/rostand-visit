package com.example;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.example.R.layout.single_item;

public class prof_accueil extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String id, nom, prenom, mail, etablissement, section , visite, specialite;

    //private static String url = "http://192.168.0.22/android/rostand-visit/select_etudiants.php";
    String url = "http://172.30.31.1/rostand-visit/select_etudiants.php";

    ListView lvEtudiant = findViewById(R.id.lvEtudiant);

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
                    JSONArray jsonArray = response.getJSONArray("specialites");
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String specialiteLibelle = jsonObject.optString("specialite_libelle");
                        etudiantsList.add(specialiteLibelle);
                        etudiantsAdapter = new ArrayAdapter<>(prof_accueil.this,
                                android.R.layout.simple_list_item_1, etudiantsList);
                        etudiantsAdapter.setDropDownViewResource(android.R.layout.activity_list_item);
                        lvEtudiant.setAdapter(etudiantsAdapter);
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
        lvEtudiant.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}