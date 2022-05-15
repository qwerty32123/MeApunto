package com.politecnico.meapunto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.politecnico.meapunto.modelos.Pista;
import com.politecnico.meapunto.modelos.PistaAdapter;
import com.politecnico.meapunto.modelos.TimeSlot;
import com.politecnico.meapunto.modelos.TimeSlotAdapter;
import com.politecnico.meapunto.modelos.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PistaChoose extends AppCompatActivity implements PistaAdapter.OnNoteListener {

    private static final String URL_PRODUCTS = URLs.URL_PISTAS_GET;
    List<Pista> listaPistas;

    RecyclerView recyclerView;

    String choosenSlot;

    String dia;


    @Override
    public void onNoteClick(int position) {

        choosenSlot = listaPistas.get(position).getNombre();

        choosenSlot = choosenSlot.replace("Pista ","");

        //creamos la interfaz de si o no
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:


                        Intent intent = new Intent(PistaChoose.this, TimeSlotChoose.class);
                        intent.putExtra("dia",dia);
                        intent.putExtra("pista",choosenSlot);
                        startActivity(intent);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // do nothing
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(PistaChoose.this);
        builder.setMessage("Estas seguro de que quieres elegir esta pista? " + choosenSlot).setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();


        // acces wich one was selected


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pista_choose);

        Intent intent = getIntent();

        dia = intent.getStringExtra("dia");


        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.pistaRecyclerVIew);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        listaPistas = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview

        loadProducts();




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
                                listaPistas.add(new Pista(
                                        product.getString("id"),
                                        product.getString("nombre")

                                ));
                            }

                            //creating adapter object and setting it to recyclerview

                            PistaAdapter adapter = new PistaAdapter(PistaChoose.this, listaPistas, PistaChoose.this);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }


}