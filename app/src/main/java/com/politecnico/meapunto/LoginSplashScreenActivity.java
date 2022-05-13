package com.politecnico.meapunto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.politecnico.meapunto.modelos.SharedPrefManager;
import com.politecnico.meapunto.modelos.Usuario;

public class LoginSplashScreenActivity extends AppCompatActivity {
    TextView usuarioTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_splash_screen);
        if(SharedPrefManager.getInstance(this).isLoggedIn()){

            Usuario user = SharedPrefManager.getInstance(this).getUser();

            usuarioTextView = findViewById(R.id.usuarioView);
            usuarioTextView.setText(user.getNombre());

        }
        finish();

        startActivity(new Intent(getApplicationContext(), MenuPrincipal.class));


    }
}