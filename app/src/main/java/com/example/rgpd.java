package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class rgpd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgpd);
    }

    public void retour(View view){
        Intent intent = new Intent(this, garde.class);
        startActivity(intent);
        finish();
    }
}