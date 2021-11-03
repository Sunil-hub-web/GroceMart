package com.example.grocemart.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.grocemart.R;
import com.example.grocemart.adapter.CitySpinerAdapter;
import com.example.grocemart.adapter.PincodeSpinerAdapter;
import com.example.grocemart.modelclass.City_ModelClass;
import com.example.grocemart.modelclass.PinCode_ModelClass;
import com.example.grocemart.url.APPURLS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectLocation extends AppCompatActivity {

    Spinner spiner_City, spiner_pincode;
    Button btn_submit,btn_restartapp;
    String item, City_id, City_Name, Pincode_id, Pincode;
    String cityid;
    ArrayList<City_ModelClass> list_city = new ArrayList<>();
    ArrayList<PinCode_ModelClass> arrayListPincode = new ArrayList<PinCode_ModelClass>();
    HashMap<String, ArrayList<PinCode_ModelClass>> hashmap_picode = new HashMap<String, ArrayList<PinCode_ModelClass>>();
    private Boolean exit = false;
    RelativeLayout networkConnection,showDetails;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);

        spiner_City = findViewById(R.id.spinner_city);
        spiner_pincode = findViewById(R.id.spinner_pincode);
        btn_submit = findViewById(R.id.Next);
        networkConnection = findViewById(R.id.networkConnection);
        showDetails = findViewById(R.id.showDetails);
        btn_restartapp = findViewById(R.id.restartapp);

        connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()){

            networkConnection.setVisibility(View.VISIBLE);
            showDetails.setVisibility(View.GONE);

        }else{

            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
            networkConnection.setVisibility(View.GONE);
            showDetails.setVisibility(View.VISIBLE);
            getCity();
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SelectLocation.this, MainActivity.class);
               /* intent.putExtra("item", City_Name);
                intent.putExtra("Pincode_id", Pincode_id);
                intent.putExtra("City_id", City_id);*/
                startActivity(intent);


                SharedPreferences sp = getSharedPreferences("details",MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("City_Name",City_Name);
                editor.putString("Pincode_id",Pincode_id);
                editor.putString("City_id",City_id);
                editor.commit();

            }
        });

        btn_restartapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                networkInfo = connectivityManager.getActiveNetworkInfo();

                if(networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()){

                    networkConnection.setVisibility(View.VISIBLE);
                    showDetails.setVisibility(View.GONE);

                }else{

                    Toast.makeText(SelectLocation.this, "Connected", Toast.LENGTH_SHORT).show();
                    networkConnection.setVisibility(View.GONE);
                    showDetails.setVisibility(View.VISIBLE);
                    getCity();
                }
            }
        });

    }

    public void getCity() {


        ProgressDialog progressDialog = new ProgressDialog(SelectLocation.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, APPURLS.selectLocation, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("success");
                    if (message.equals("true")) {

                        String allLocation = jsonObject.getString("All_loc");

                        JSONArray jsonArray = new JSONArray(allLocation);

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            JSONArray jsinArrayPincode = jsonObject1.getJSONArray("pincode_list");
                            cityid = jsonObject1.getString("city_id");

                            /*for(int j=0;j<jsinArrayPincode.length();j++) {
                                
                                JSONObject jsonObjPincode = jsinArrayPincode.getJSONObject(j);
                                String pin_id = jsonObjPincode.getString("pin_id");
                                String pincode = jsonObjPincode.getString("pincode");

                                //create module class for the pin id and pincode

                                PinCode_ModelClass pincodeSetgger = new PinCode_ModelClass(pincode,pin_id);
                                arrayListPincode.add(pincodeSetgger);
                            }*/

                            City_ModelClass city_modelClass = new City_ModelClass
                                    (jsonObject1.getString("city_name"), cityid);

                            list_city.add(city_modelClass);
                            //hashmap_picode.put(cityid, arrayListPincode);


//                            Log.d("arrayListPincode",arrayListPincode.toString());

                        }


                        CitySpinerAdapter adapter = new CitySpinerAdapter(SelectLocation.this, android.R.layout.simple_spinner_dropdown_item
                                , list_city);
                        spiner_City.setAdapter(adapter);

                        Log.d("citylist", list_city.toString());

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                spiner_City.setSelection(-1, true);

                spiner_City.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        try {

                            City_ModelClass mystate = (City_ModelClass) parent.getSelectedItem();

                            City_id = mystate.getCity_id();
                            City_Name = mystate.getCity();
                            Log.d("R_Pincode", City_id);

                          /*  PinCode_ModelClass cityidpinname= arrayListPincode.get(position);
                            String pincode= cityidpinname.getPincode();
                            ArrayList<PinCode_ModelClass> janamam = hashmap_picode.get(City_id);*/

                           /* for (Map.Entry<String, ArrayList<PinCode_ModelClass>> ee : hashmap_picode.entrySet()) {
                                String key = ee.getKey();
                                ArrayList<PinCode_ModelClass> janamama = ee.getValue();
                                // TODO: Do something.

                                if(key.equalsIgnoreCase(City_id)) {
                                    for (int k = 0; k < janamama.size(); k++) {

                                        String pincodeDropDown = janamama.get(k).getPincode();
                                        ArrayList<String> arraypincode = new ArrayList<>();
                                        arraypincode.add(pincodeDropDown);
                                        Log.d("Pinocde_array", pincodeDropDown);
                                        Log.d("Pinocde_array", arraypincode.size() + "");
                                    }
                                }

                            }*/


                            //Log.d("Pinocde_array",janamam.toString());

                            GetPincode(City_id);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } // to close the onItemSelected

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(SelectLocation.this);
        requestQueue.add(stringRequest);
    }

    public void GetPincode(String city_id) {

        arrayListPincode.clear();

        ProgressDialog progressDialog = new ProgressDialog(SelectLocation.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, APPURLS.selectLocation, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("success");
                    if (message.equals("true")) {

                        String allLocation = jsonObject.getString("All_loc");

                        JSONArray jsonArray = new JSONArray(allLocation);

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String cityid = jsonObject1.getString("city_id");

                            if (cityid.equals(city_id)) {

                                String pincode = jsonObject1.getString("pincode_list");
                                JSONArray jsonArray1 = new JSONArray(pincode);

                                for (int j = 0; j < jsonArray1.length(); j++) {

                                    JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
                                    String pin_code = jsonObject2.getString("pincode");
                                    String pin_id = jsonObject2.getString("pin_id");

                                    PinCode_ModelClass pinCode_modelClass = new PinCode_ModelClass(pin_code, pin_id);
                                    arrayListPincode.add(pinCode_modelClass);
                                }

                            }
                        }

                        PincodeSpinerAdapter pincodeSpinerAdapter = new PincodeSpinerAdapter(SelectLocation.this, android.R.layout.simple_spinner_dropdown_item
                                , arrayListPincode);
                        spiner_pincode.setAdapter(pincodeSpinerAdapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                spiner_pincode.setSelection(-1, true);

                spiner_pincode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        try {

                            PinCode_ModelClass mypincode = (PinCode_ModelClass) parent.getSelectedItem();

                            Pincode_id = mypincode.getPin_id();
                            Pincode = mypincode.getPincode();
                            Log.d("R_Pincode", Pincode_id);

                            //Log.d("Pinocde_array",janamam.toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(SelectLocation.this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Intent a = new Intent(Intent.ACTION_MAIN);
                    a.addCategory(Intent.CATEGORY_HOME);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(a);
                }
            }, 3 * 1000);
        }
    }
}