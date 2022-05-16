package com.politecnico.meapunto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.politecnico.meapunto.modelos.SharedPrefManager;
import com.politecnico.meapunto.modelos.Usuario;

public class LoginSplashScreenActivity extends AppCompatActivity {
    TextView usuarioTextView;


    LottieAnimationView lottie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_splash_screen);
        if(SharedPrefManager.getInstance(this).isLoggedIn()){

            Usuario user = SharedPrefManager.getInstance(this).getUser();

            usuarioTextView = findViewById(R.id.usuarioView);
            usuarioTextView.setText(user.getNombre());

            lottie = findViewById(R.id.lotie);



            usuarioTextView.animate().translationY(-1200).setDuration(5000).setStartDelay(0);
            lottie.animate().setDuration(2000).setStartDelay(2900);

        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();

                startActivity(new Intent(getApplicationContext(), MenuPrincipal.class));


            }
        },6000);



    }
}