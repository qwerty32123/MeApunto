package com.politecnico.meapunto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.politecnico.meapunto.modelos.TimeSlot;
import com.politecnico.meapunto.modelos.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class TimeSlotChoose extends AppCompatActivity{     //this is the JSON Data URL
        //make sure you are using the correct ip else it will not work
        private static final String URL_PRODUCTS = URLs.URL_TIMESLOTGET;

        //a list to store all the products
        List<TimeSlot> productList;

        //the recyclerview
        RecyclerView recyclerView;


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
                                TimeSlotAdapter adapter = new TimeSlotAdapter(TimeSlotChoose.this, productList);
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