package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class Login extends AppCompatActivity {

    private TextInputEditText textInputEditTextUsername, textInputEditTextPassword;
    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = password = "";
        textInputEditTextUsername = findViewById(R.id.username_login_edText);
        textInputEditTextPassword = findViewById(R.id.password_login_edText);
    }

    public void login(View view){
        username = Objects.requireNonNull(textInputEditTextUsername.getText()).toString().trim();
        password = Objects.requireNonNull(textInputEditTextPassword.getText()).toString().trim();

        if(!username.equals("") && !password.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigUrl.KEY_URL_LOGIN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("Conn")) {
                        Intent intent = new Intent(Login.this, prof_accueil.class);
                        startActivity(intent);
                        finish();
                    } else if (response.equals("Err")) {
                        Toast.makeText(Login.this, "Erreur de connexion...", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Login.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("username", username);
                    data.put("password", password);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
        else {
            Toast.makeText(this, "Les champs sont vides !", Toast.LENGTH_SHORT).show();
        }
    }

    public void retour(View view){
        Intent intent = new Intent(this, garde.class);
        startActivity(intent);
        finish();
    }
}