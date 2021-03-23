package com.example;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.R.layout.single_item;

public class prof_accueil extends AppCompatActivity {

    private static String url = "http://192.168.0.22/android/rostand-visit/select_etudiants.php";
    ListView lvEtudiant = findViewById(R.id.lvEtudiant);

    private static String nom[];
    private static String prenom[];
    private static String mail[];
    private static String etablissement[];
    private static String section[];
    private static String visite[];
    private static String specialite[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_accueil);
        lvEtudiant=(ListView)findViewById(R.id.lvEtudiant);

        fetch_data_into_array(lvEtudiant);

        lvEtudiant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = lvEtudiant.getItemAtPosition(position).toString();

                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
        });

    }

    public void fetch_data_into_array(final View view)
    {

        class  dbManager extends AsyncTask<String,Void,String>
        {
            protected void onPostExecute(String data)
            {
                try {
                    JSONArray ja = new JSONArray(data);
                    JSONObject jo = null;

                    nom = new String[ja.length()];
                    prenom = new String[ja.length()];
                    mail = new String[ja.length()];
                    etablissement = new String[ja.length()];
                    section = new String[ja.length()];
                    visite = new String[ja.length()];
                    specialite = new String[ja.length()];

                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        nom[i] = jo.getString("etudiant_nom");;
                        prenom[i] = jo.getString("etudiant_prenom");
                        mail[i] = jo.getString("etudiant_mail");
                        etablissement[i] = jo.getString("etudiant_etablissement");
                        section[i] = jo.getString("etudiant_visite");
                        visite[i] = jo.getString("etudiant_section");
                        specialite[i] = jo.getString("etudiant_libelle");
                    }

                    myadapter adptr = new myadapter(getApplicationContext(), nom, prenom, mail, etablissement, section, visite, specialite);
                    lvEtudiant.setAdapter(adptr);

                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... strings)
            {
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuffer data = new StringBuffer();
                    String line;

                    while ((line = br.readLine()) != null) {
                        data.append(line + "\n");
                    }
                    br.close();

                    return data.toString();

                } catch (Exception ex) {
                    return ex.getMessage();
                }

            }

        }
        dbManager obj=new dbManager();
        obj.execute(url);

    }

    class myadapter extends ArrayAdapter<String>
    {
        Context context;
        String nom[];
        String prenom[];
        String mail[];
        String etablissement[];
        String section[];
        String visite[];
        String specialite[];

        myadapter(Context c, String[] nom, String[] prenom, String[] mail, String[] etablissement, String[] section, String[] visite, String[] specialite)
        {
            super(c, R.layout.single_item, R.id.item_id, nom);
            context = c;
            this.nom = nom;
            this.prenom = prenom;
            this.mail = mail;
            this.etablissement = etablissement;
            this.section = section;
            this.visite = visite;
            this.specialite = specialite;


        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {
            LayoutInflater inflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(single_item, parent,false);

            TextView item_nom=row.findViewById(R.id.item_nom);
            TextView item_prenom=row.findViewById(R.id.item_prenom);
            TextView item_mail=row.findViewById(R.id.item_mail);
            TextView item_etablissement=row.findViewById(R.id.item_etablissement);
            TextView item_section=row.findViewById(R.id.item_section);
            TextView item_visite=row.findViewById(R.id.item_visite);
            TextView item_specialite=row.findViewById(R.id.item_specialite);


            item_nom.setText(nom[position]);
            item_prenom.setText(prenom[position]);
            item_mail.setText(mail[position]);
            item_etablissement.setText(etablissement[position]);
            item_section.setText(section[position]);
            item_visite.setText(visite[position]);
            item_specialite.setText(specialite[position]);


            return row;
        }
    }

    public void garde(View view){
        Intent intent = new Intent(this, garde.class);
        startActivity(intent);
        finish();
    }
}