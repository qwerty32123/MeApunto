package com.politecnico.meapunto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(this);
         Bundle bundle = new Bundle();
         bundle.putString("message","Integracion de Firebase completa");
         analytics.logEvent("InitScreen",bundle);

    }
}