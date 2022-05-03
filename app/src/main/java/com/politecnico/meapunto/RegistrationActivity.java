package com.politecnico.meapunto;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Calendar;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.politecnico.meapunto.modelos.SharedPrefManager;
import com.politecnico.meapunto.modelos.URLs;
import com.politecnico.meapunto.modelos.Usuario;
import com.politecnico.meapunto.modelos.VolleySingleton;

public class RegistrationActivity extends AppCompatActivity {
    private TextView date, nombre, apellidos, email, password, date_of_birth,direccion,telefono,dni;
    private CheckBox policy;
    private RadioGroup gender_group;
    private RadioButton gender_button;
    private Button select_date;


    private Calendar c;
    private DatePickerDialog dpd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        gender_group = findViewById(R.id.genderGroup);

        select_date = findViewById(R.id.select_date);

        nombre = findViewById(R.id.nombre);
        apellidos = findViewById(R.id.apellidos);
        email = findViewById(R.id.emailText);
        date_of_birth = findViewById(R.id.fecha_nacimiento);
        password = findViewById(R.id.password_Text);
        policy = findViewById(R.id.policy);
        direccion = findViewById(R.id.direccion_text);
        dni = findViewById(R.id.dni_text);
        telefono =findViewById(R.id.telefono_text);


        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c = Calendar.getInstance();
                final int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(RegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date_of_birth.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, day, month, year);
                dpd.show();
            }
        });


        Button b = findViewById(R.id.signUpButtonId);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selected_gender = gender_group.getCheckedRadioButtonId();
                gender_button = findViewById(selected_gender);

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String datePattern = "^(1[0-2]|0[1-9])/(3[01]" + "|[12][0-9]|0[1-9])/[0-9]{4}$";


                if (nombre.getText().toString().equals("")) {
                    nombre.setError("Empty first name");
                }  else if (apellidos.getText().toString().equals("")) {
                    apellidos.setError("Empty last name");
                } else if (email.getText().toString().equals("")) {
                    email.setError("Empty email address");
                } else if (!email.getText().toString().trim().matches(emailPattern)) {
                    email.setError("Invalid email address");
                } else if (date_of_birth.getText().toString().equals("")) {
                    date_of_birth.setError("Select date of birth");
                } else if (!date_of_birth.getText().toString().trim().matches(datePattern)) {
                    date_of_birth.setError("Date format should be 'mm/dd/yyyy'");
                } else if (password.getText().toString().equals("")) {
                    password.setError("Empty password");
                } else if(!policy.isChecked()) {
                    policy.setError("Policy must be accepted");
                } else {

                    if (nombre.getError() == null && nombre.getError() == null && email.getError() == null &&
                            date_of_birth.getError() == null && password.getError() == null && policy.isChecked()) {
//  codigo para registrar a un nuevo usuario aqui

                        registerUser();



                    }

                }
            }
        });
    }




    private void registerUser() {
        final String nombre = this.nombre.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        final String apellidos = this.apellidos.getText().toString().trim();
        final String fecha_nacimiento = this.date_of_birth.getText().toString().trim();
        final String direccion = this.direccion.getText().toString().trim();
        final String telefono = this.email.getText().toString().trim();
        final String DNI = this.dni.getText().toString().trim();






        final String gender = ((RadioButton) findViewById(this.gender_group.getCheckedRadioButtonId())).getText().toString();



        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
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

                                //starting the profile activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), LoginSplashScreenActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("DNI", DNI);
                params.put("nombre", nombre);
                params.put("apellidos", apellidos);
                params.put("direccion", direccion);
                params.put("telefono", telefono);
                params.put("genero", gender);
                params.put("fecha_nacimiento", fecha_nacimiento);
                params.put("correo", email);
                params.put("nivel_juego", "Iniciacion");
                params.put("preferencia", "Indiferente");
                params.put("descripcion", "N/A");
                params.put("validado", "0");
                params.put("contraseña", password);
                params.put("activo", "1");


                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
