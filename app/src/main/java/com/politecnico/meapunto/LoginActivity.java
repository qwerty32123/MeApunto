package com.politecnico.meapunto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/mapunto2";
    private static final String USER = "dwes";
    private static final String PASSWORD = "abc123.";




    public void LoginNormal(View view) {
        // Texto en
        EditText textUsuario = (EditText) findViewById(R.id.editTextTextPersonName);
        EditText textContrasena = (EditText) findViewById(R.id.editTextTextPassword);

        String usuario = textUsuario.getText().toString();
        String contrasena = textContrasena.getText().toString();

        if (TextUtils.isEmpty(usuario)) {
            textUsuario.setError("Introduce un nombre de usuario");

        } else if (TextUtils.isEmpty(contrasena)) {

            textContrasena.setError("Introduce un nombre de usuario");
        } else if (!TextUtils.isEmpty(usuario) || TextUtils.isEmpty(contrasena)) {

            //Comprobamos si esta loggeado

            AsyncTask<Void, Void, Map<String, String>> results = new InfoAsyncTask().execute();
            Log.v("LoginActivity", results.toString());


        }


    }

    public class InfoAsyncTask extends AsyncTask<Void, Void, Map<String, String>> {
        @Override
        protected Map<String, String> doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


            EditText textUsuario = (EditText) findViewById(R.id.editTextTextPersonName);
            EditText textContrasena = (EditText) findViewById(R.id.editTextTextPassword);

            String usuario = textUsuario.getText().toString();
            String contrasena = textContrasena.getText().toString();

            Map<String, String> info = new HashMap<>();

            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                String sql = "SELECT * FROM usuario where correo = " + usuario + " and contraseña =" + contrasena + " LIMIT 1";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    info.put("dni", resultSet.getString("dni"));

                }
            } catch (Exception e) {
                Log.e("InfoAsyncTask", "Error reading school information", e);
            }

            return info;
        }

    }


        // FIREBASE Y GOOGLE

        private static final String TAG = "GoogleActivity";
        private static final int RC_SIGN_IN = 9001;

        // [START declare_auth]
        private FirebaseAuth mAuth;
        // [END declare_auth]

        private GoogleSignInClient mGoogleSignInClient;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);


            findViewById(R.id.googleLogin).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    googleLoginFirebase();
                }
            });


            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            // [END config_signin]

            // [START initialize_auth]
            // Initialize Firebase Auth
            mAuth = FirebaseAuth.getInstance();
            // [END initialize_auth]
        }

        // [START on_start_check_user]
        @Override
        public void onStart() {
            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();
            updateUI(currentUser);
        }
        // [END on_start_check_user]

        // [START onactivityresult]
        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(TAG, "Google sign in failed", e);
                }
            }
        }
        // [END onactivityresult]

        // [START auth_with_google]
        private void firebaseAuthWithGoogle(String idToken) {
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithCredential:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithCredential:failure", task.getException());
                                updateUI(null);
                            }
                        }
                    });
        }
        // [END auth_with_google]

        // [START signin]
        private void googleLoginFirebase() {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
        // [END signin]

        private void updateUI(FirebaseUser user) {
            FirebaseUser userl = FirebaseAuth.getInstance().getCurrentUser();
//        if(userl !=null){
//            Intent intent = new Intent(MainActivity.this.)

        }

    }


