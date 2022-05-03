package com.politecnico.meapunto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity {
    private TextView date, nombre, apellidos, email, password, date_of_birth,direccion,telefono;
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
        telefono =findViewById(R.id.telefono_text);

    }
}