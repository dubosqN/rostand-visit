package com.example;

/*import androidx.appcompat.app.AppCompatActivity;

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



*//*                        etudiantsAdapter = new ArrayAdapter<>(prof_accueil.this,
                                android.R.layout.simple_list_item_1, etudiantsList);
                        etudiantsAdapter.setDropDownViewResource(android.R.layout.activity_list_item);*//*
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

}*/
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
    EditText editTextannee;
    Button buttonfetch;

    ListView lvEtudiant;
    //année
    String annee;
    String id, nom, prenom, mail, etablissement, section , visite, specialite;
    ProgressDialog mProgressDialog;
    private static String url = "http://192.168.0.22/android/rostand-visit/select_etudiants.php";

    public static final String KEY_NAME = "name";
    public static final String KEY_CITY = "city";
    public static final String KEY_COUNTRY = "country";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_accueil);

        editTextannee = (EditText)findViewById(R.id.etAnnee);
        buttonfetch = (Button)findViewById(R.id.btnfetch);
        lvEtudiant = findViewById(R.id.lvEtudiant);
        buttonfetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                annee = editTextannee.getText().toString().trim();

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
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("etudiant_annee", annee);
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


        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(
                prof_accueil.this, list, R.layout.single_item,
                new String[]{"etudiant_nom", "etudiant_prenom", "etudiant_mail", "etudiant_etablissement", "etudiant_section", "etudiant_visite", "etudiant_libelle"},
                new int[]{R.id.item_nom, R.id.item_prenom, R.id.item_mail, R.id.item_etablissement, R.id.item_section, R.id.item_visite, R.id.item_specialite});

        lvEtudiant.setAdapter(adapter);

    }


}