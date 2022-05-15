package com.politecnico.meapunto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

public class MenuJugarActivity extends AppCompatActivity {
    CalendarView calendario;
    Button jugarButton;
    String curDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_jugar);
        calendario = findViewById(R.id.calendarView);
        calendario.setMinDate(System.currentTimeMillis() - 1000);

        long now = System.currentTimeMillis() - 1000;
        calendario.setMaxDate(now+(1000*60*60*24*14)); //After 14 Days from Now


        jugarButton = findViewById(R.id.botonJugar);

        jugarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(curDate == null)) {
                    Intent intent = new Intent(MenuJugarActivity.this, PistaChoose.class);
                    intent.putExtra("dia",curDate);
                    startActivity(intent);
                }else{
                    new AlertDialog.Builder(MenuJugarActivity.this)
                            .setTitle("ERROR")
                            .setMessage("Tienes que elegir una fecha")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            }
        });

        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {

                String  curfecha = String.valueOf(dayOfMonth);
                String  Year = String.valueOf(year);
                String  Month = String.valueOf(month);
                if(Integer.parseInt(Month)<= 9){
                    Month = "0"+Month;
                }

                curDate = Year+"-"+Month+"-"+curfecha;
            }
        });
    }
}