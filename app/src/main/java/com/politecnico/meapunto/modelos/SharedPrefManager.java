package com.politecnico.meapunto.modelos;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.politecnico.meapunto.LoginActivity;

/**
 * Created by Belal on 9/5/2017.
 */

//here for this class we are using a singleton pattern

public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_DNI = "keydni";
    private static final String KEY_CORREO = "keycorreo";
    private static final String KEY_NOMBRE = "keynombre";
    private static final String KEY_APELLIDOS = "keyapellidos";
    private static final String KEY_DIRECCION = "keydireccion";
    private static final String KEY_TELEFONO = "keytelefono";
    private static final String KEY_DESCRIPCION = "keydescripcion";
    private static final String KEY_CONTRASENA = "keycontrasena";
    private static final String KEY_GENERO = "keygenero";
    private static final String KEY_NIVEL = "keynivel";
    private static final String KEY_PREFERENCIA = "keypreferencia";
    private static final String KEY_FNACIMIENTO = "keyfnacimiento";





    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(Usuario user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_APELLIDOS, user.getApellidos());
        editor.putString(KEY_DNI, user.getDNI());
        editor.putString(KEY_CORREO, user.getCorreo());
        editor.putString(KEY_DIRECCION, user.getDireccion());
        editor.putString(KEY_TELEFONO, user.getTelefono());
        editor.putString(KEY_DESCRIPCION, user.getDescripcion());
        editor.putString(KEY_NOMBRE, user.getNombre());
        editor.putString(KEY_CONTRASENA, user.getContraseña());

        //enum igual no funciona, no se si mappear los enums como string va salir bien
        editor.putString(KEY_GENERO, user.getGenero().toString());
        editor.putString(KEY_NIVEL, user.getNivelDeJuego().toString());
        editor.putString(KEY_PREFERENCIA, user.getPreferencia().toString());
        editor.putString(KEY_FNACIMIENTO, user.getFecha_nacimiento().toString());





        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_DNI, null) != null;
    }

    //this method will give the logged in user
    public Usuario getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Usuario(
                sharedPreferences.getString(KEY_DNI, null),
                sharedPreferences.getString(KEY_NOMBRE, null),
                sharedPreferences.getString(KEY_APELLIDOS, null),
                sharedPreferences.getString(KEY_DIRECCION, null),
                sharedPreferences.getString(KEY_TELEFONO, null),

                sharedPreferences.getString(KEY_CORREO, null),
                sharedPreferences.getString(KEY_DESCRIPCION, null),
                sharedPreferences.getString(KEY_CONTRASENA, null),
                sharedPreferences.getString(KEY_GENERO, null),
                sharedPreferences.getString(KEY_NIVEL, null),
                sharedPreferences.getString(KEY_PREFERENCIA, null),
                sharedPreferences.getString(KEY_FNACIMIENTO, null)


        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}