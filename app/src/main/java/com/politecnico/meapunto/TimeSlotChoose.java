package com.politecnico.meapunto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.politecnico.meapunto.modelos.Pista;
import com.politecnico.meapunto.modelos.SharedPrefManager;
import com.politecnico.meapunto.modelos.TimeSlot;
import com.politecnico.meapunto.modelos.TimeSlotAdapter;
import com.politecnico.meapunto.modelos.URLs;
import com.politecnico.meapunto.modelos.Usuario;
import com.politecnico.meapunto.modelos.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeSlotChoose extends AppCompatActivity implements TimeSlotAdapter.OnNoteListener {     //this is the JSON Data URL
        //make sure you are using the correct ip else it will not work
        private static final String URL_PRODUCTS = URLs.URL_TIMESLOTGET;

        //a list to store all the products
        List<TimeSlot> productList;

        //the recyclerview
        RecyclerView recyclerView;

        TextView elegirHora;

    String choosenSlot;


    @Override
    public void onNoteClick(int position) {
        choosenSlot = productList.get(position).getDescription();

        //creamos la interfaz de si o no
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:



                        Intent intent = new Intent(TimeSlotChoose.this,MenuPrincipal.class);
                        startActivity(intent);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // do nothing
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(TimeSlotChoose.this);
        builder.setMessage("Estas seguro de que quieres elegir esta hora? " + choosenSlot).setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    //

//        SELECT
//        timeslot.description
//        FROM agenda
//        INNER JOIN timeslot
//        ON agenda.timeSlot = timeslot.id
//        WHERE agenda.disponible <> 0
//        AND agenda.dia = agenda.dia
//        AND agenda.id_pista = 'idPista'
        //

        // acces wich one was selected


    }

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_time_slot_choose);

            //getting the recyclerview from xml
            recyclerView = findViewById(R.id.recylcerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            //initializing the productlist
            productList = new ArrayList<>();

            //this method will fetch and parse json
            //to display it in recyclerview

            loadProducts();


            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

//            elegirHora = (TextView) findViewById(R.id.textViewShortDesc);
//
//
//            elegirHora.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                    builder.setMessage("Estas seguro de que quieres elegir esta hora: ?"+ elegirHora.getText().toString()).setPositiveButton("Yes", dialogClickListener)
//                            .setNegativeButton("No", dialogClickListener).show();
//                }
//            });


        }

        private void loadProducts() {

            /*
             * Creating a String Request
             * The request type is GET defined by first parameter
             * The URL is defined in the second parameter
             * Then we have a Response Listener and a Error Listener
             * In response listener we will get the JSON response as a String
             * */
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PRODUCTS,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                //converting the string to json array object
                                JSONArray array = new JSONArray(response);

                                //traversing through all the object
                                for (int i = 0; i < array.length(); i++) {

                                    //getting product object from json array
                                    JSONObject product = array.getJSONObject(i);

                                    //adding the product to product list
                                    productList.add(new TimeSlot(
                                            product.getInt("id"),
                                            product.getString("description")

                                    ));
                                }

                                //creating adapter object and setting it to recyclerview

                                TimeSlotAdapter adapter = new TimeSlotAdapter(TimeSlotChoose.this, productList, TimeSlotChoose.this);
                                recyclerView.setAdapter(adapter);
                            } catch (JSONException error) {
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    })




                    ;

            //adding our stringrequest to queue
            Volley.newRequestQueue(this).add(stringRequest);
        }

    private void removeTimeSlots() {




        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_TIMESLOT_TOREMOVE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                productList.remove(new TimeSlot(
                                        product.getInt("id"),
                                        product.getString("description")

                                ));
                            }

                            //creating adapter object and setting it to recyclerview

//                            TimeSlotAdapter adapter = new TimeSlotAdapter(TimeSlotChoose.this, productList, TimeSlotChoose.this);
//                            recyclerView.setAdapter(adapter);
                        } catch (JSONException error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

                Intent intent = getIntent();
                params.put("dia", intent.getStringExtra("dia"));
                params.put("pista", intent.getStringExtra("pista"));



                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }



}