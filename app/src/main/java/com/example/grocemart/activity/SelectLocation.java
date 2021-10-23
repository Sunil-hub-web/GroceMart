package com.example.grocemart.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

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
    Button btn_submit;
    String item, City_id;
    String cityid;
    ArrayList<City_ModelClass> list_city = new ArrayList<>();
    ArrayList<PinCode_ModelClass> arrayListPincode = new ArrayList<PinCode_ModelClass>();
    HashMap<String, ArrayList<PinCode_ModelClass>> hashmap_picode = new HashMap<String, ArrayList<PinCode_ModelClass>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);

        spiner_City = findViewById(R.id.spinner_city);
        spiner_pincode = findViewById(R.id.spinner_pincode);
        btn_submit = findViewById(R.id.Next);

        getCity();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SelectLocation.this, MainActivity.class);
                intent.putExtra("item", item);
                startActivity(intent);

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
}