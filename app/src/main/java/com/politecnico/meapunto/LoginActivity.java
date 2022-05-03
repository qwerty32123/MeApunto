package com.politecnico.meapunto;
// https://www.javatpoint.com/android-volley-library-registration-login-logout
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import com.politecnico.meapunto.modelos.RequestHandler;
import com.politecnico.meapunto.modelos.SharedPrefManager;
import com.politecnico.meapunto.modelos.URLs;
import com.politecnico.meapunto.modelos.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {





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



            class UserLogin extends AsyncTask<Void, Void, String> {

                ProgressBar progressBar;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                   
                }

                @SuppressLint("LongLogTag")
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);


                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(s);

                        //if no error in response
                        if (!obj.getBoolean("error")) {
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            //getting the user from the response
                            JSONObject userJson = obj.getJSONObject("user");


                            //creating a new user object
                            Usuario user = new Usuario(
                                    userJson.getString("DNI"),
                                    userJson.getString("nombre"),
                                    userJson.getString("apellidos"),
                                    userJson.getString("direccion"),
                                    userJson.getString("telefono"),

                                    userJson.getString("correo"),
                                    userJson.getString("descripcion"),
                                    userJson.getString("contraseña"),
                                    userJson.getString("genero"),
                                    userJson.getString("nivel_juego"),
                                    userJson.getString("preferencia")
                            );

                            //storing the user in shared preferences
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                            Toast.makeText(getApplicationContext(), "Succes username & password", Toast.LENGTH_SHORT).show();
                            //starting the profile activity
                            finish();
                            Log.i("LoginSplashActivity","Starting to run");
                            startActivity(new Intent(getApplicationContext(), LoginSplashScreenActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid username or password22", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("OUTPUT LOGIN ERROR LoginActivity", "json error",e);
                    }
                }

                @Override
                protected String doInBackground(Void... voids) {
                    //creating request handler object
                    RequestHandler requestHandler = new RequestHandler();

                    //creating request parameters
                    HashMap<String, String> params = new HashMap<>();
                    params.put("correo", usuario);
                    params.put("contraseña", contrasena);

                    //returing the response
                    return requestHandler.sendPostRequest(URLs.URL_LOGIN, params);
                }
            }

            UserLogin ul = new UserLogin();
            ul.execute();


        }




    }

//  /////////////////////////


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

            // logout al usuario para comprobar el funcionamiento
            // todo borrar esta linea ya que deslogea cualquier usuario loggeado

            if (SharedPrefManager.getInstance(this).isLoggedIn()) {
                SharedPrefManager.getInstance(this).logout();


                finish();
                startActivity(new Intent(this, LoginSplashScreenActivity.class));
            }


            findViewById(R.id.googleLogin).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    googleLoginFirebase();
                }
            });

            findViewById(R.id.registerButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    finish();
                    startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));

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


