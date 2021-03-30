package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class update_password extends AppCompatActivity {

    String url = "http://172.30.31.1/rostand-visit/update_password.php";
    TextInputEditText editTextPassword, editTextConfirmPassword, editTextMail;
    TextView update_psw;
    String password, confirmPassword, mail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        editTextPassword = findViewById(R.id.etPassword);
        editTextConfirmPassword = findViewById(R.id.etPasswordConfirm);
        editTextMail = findViewById(R.id.etMail);
        update_psw = findViewById(R.id.update_psw);
    }

    public void majPassword(View view){
        password = Objects.requireNonNull(editTextPassword.getText()).toString().trim();
        confirmPassword = Objects.requireNonNull(editTextConfirmPassword.getText()).toString().trim();
        mail = Objects.requireNonNull(editTextMail.getText()).toString().trim();

        if(!editTextConfirmPassword.equals("") && !password.equals("") && !mail.equals("") ){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("modif")) {
                        update_psw.setText("Votre mot de passe à été modifié avec succès.");
                        editTextPassword.setText("");
                        editTextConfirmPassword.setText("");
                        editTextMail.setText("");
                    } else if (response.equals("fail")) {
                        Toast.makeText(update_password.this, "Erreur...", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(update_password.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(update_password.this, error.toString().trim(), Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("password", password);
                    data.put("mail", mail);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }

        else {
            Toast.makeText(this, "Les champs sont vides ou les mot de passes ne correspondent pas !", Toast.LENGTH_SHORT).show();
        }
    }

    public void listeEtud(View view){
        Intent intent = new Intent(this, prof_accueil.class);
        startActivity(intent);
        finish();
    }

    public void logout(View view){
        Intent intent = new Intent(this, garde.class);
        startActivity(intent);
        finish();
    }
}