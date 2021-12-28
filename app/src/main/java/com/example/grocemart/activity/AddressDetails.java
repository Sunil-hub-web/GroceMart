package com.example.grocemart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.grocemart.R;
import com.example.grocemart.SharedPrefManager;
import com.example.grocemart.adapter.CitySpinerAdapter;
import com.example.grocemart.adapter.PincodeSpinerAdapter;
import com.example.grocemart.adapter.ViewaddressDetailsAdapter;
import com.example.grocemart.modelclass.City_ModelClass;
import com.example.grocemart.modelclass.PinCode_ModelClass;
import com.example.grocemart.modelclass.ViewAddressDetails_ModelClass;
import com.example.grocemart.url.APPURLS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressDetails extends AppCompatActivity {

    ImageView image_back;
    Button btn_AddAddress;
    String str_Name,str_Email,str_MobileNo,str_City,str_Area,str_Address,
            str_PinCode,userId,cityid,City_id,city_Name,pincode_Name,
            Name,Email,MobileNo,City,Area,Address,PinCode,addressId,city_id;
    Button btn_SaveAddress,btn_cancel;
    Spinner spiner_City,spiner_Pincode;
    Dialog dialog;
    ArrayList<City_ModelClass> list_city = new ArrayList<>();
    ArrayList<PinCode_ModelClass> arrayListPincode = new ArrayList<PinCode_ModelClass>();
    HashMap<String, ArrayList<PinCode_ModelClass>> hashmap_picode = new HashMap<String, ArrayList<PinCode_ModelClass>>();
    RecyclerView AddressRecycler;
    LinearLayoutManager linearLayoutManager;
    ViewaddressDetailsAdapter viewaddressDetailsAdapter;

    ArrayList<ViewAddressDetails_ModelClass> addressDetails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_details);

        image_back = findViewById(R.id.back);
        btn_AddAddress = findViewById(R.id.addaddress);
        btn_cancel = findViewById(R.id.btn_cancel);
        AddressRecycler = findViewById(R.id.addressRecycler);
        linearLayoutManager =  new LinearLayoutManager(AddressDetails.this,LinearLayoutManager.VERTICAL,false);

        userId = SharedPrefManager.getInstance(AddressDetails.this).getUser().getId();
        getaddressDetails(userId);

        btn_AddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_Address();
            }
        });

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AddressDetails.this,UserDashboard.class);
                startActivity(intent);
            }
        });

    }

    public void add_Address(){

        //Show Your Another AlertDialog

        dialog = new Dialog(AddressDetails.this);
        dialog.setContentView(R.layout.address_details);
        dialog.setCancelable(false);
        btn_SaveAddress = dialog.findViewById(R.id.saveaddress);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);
        EditText edit_Name = dialog.findViewById(R.id.fullName);
        EditText edit_Email = dialog.findViewById(R.id.emailAddress);
        EditText edit_Address = dialog.findViewById(R.id.Address);
        EditText edit_MobileNo = dialog.findViewById(R.id.contactNo);
        spiner_City = dialog.findViewById(R.id.spinner_city);
        spiner_Pincode = dialog.findViewById(R.id.spinner_pincode);
        EditText edit_Area = dialog.findViewById(R.id.area);

        getCity();

        btn_SaveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edit_Name.getText().toString().trim().equals("")){

                    edit_Name.setError("Please Enter Name");

                }else if (TextUtils.isEmpty(edit_Email.getText())){

                    edit_Email.setError("Please Enter Email");

                }else if (TextUtils.isEmpty(edit_MobileNo.getText()) && edit_MobileNo.getText().toString().trim().length() == 10) {

                    edit_MobileNo.setError("Please Enter MobileNumber");

                }else if (TextUtils.isEmpty(edit_Area.getText())) {

                    edit_Area.setError("Please Enter Area");

                }else if (TextUtils.isEmpty(edit_Address.getText())) {

                    edit_Address.setError("Please Enter Address");

                }else{

                    str_Name = edit_Name.getText().toString().trim();
                    str_Email = edit_Email.getText().toString().trim();
                    str_MobileNo = edit_MobileNo.getText().toString().trim();
                    str_Area = edit_Area.getText().toString().trim();
                    str_Address = edit_Address.getText().toString().trim();
                    str_PinCode = pincode_Name;
                    str_City = City_id;

                    addAddress(userId,str_Name,str_MobileNo,str_City,str_Address,str_Area,str_Email,str_PinCode);

                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.drawable.dialogback);
    }

   public void addAddress(String userId,String name,String number,String city,
                                    String address,String area,String email,String pincode){

       ProgressDialog progressDialog = new ProgressDialog(AddressDetails.this);
       progressDialog.show();
       progressDialog.setContentView(R.layout.progress_dialog);
       TextView textView = progressDialog.findViewById(R.id.text);
       textView.setText("Register Please wait...");
       progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
       progressDialog.setCancelable(false);

       StringRequest stringRequest = new StringRequest(Request.Method.POST, APPURLS.addAddress, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {

               progressDialog.dismiss();
               dialog.dismiss();

               try {
                   JSONObject jsonObject = new JSONObject(response);

                   String message = jsonObject.getString("success");

                   if (message.equals("true")){

                       Toast.makeText(AddressDetails.this, "Address Insterted Success Fully..", Toast.LENGTH_SHORT).show();

                       getaddressDetails(userId);

                   }
               } catch (JSONException e) {
                   e.printStackTrace();
               }


           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {

               progressDialog.dismiss();
               error.printStackTrace ();
               Toast.makeText (AddressDetails.this, "Address not Stored Successfully", Toast.LENGTH_SHORT).show ( );

           }
       }){

           @Override
           protected Map<String, String> getParams() throws AuthFailureError {

               Map<String,String> params = new HashMap<>();

               params.put("id",userId);
               params.put("name",name);
               params.put("number",number);
               params.put("city_id",city);
               params.put("address",address);
               params.put("area_id",area);
               params.put("email",email);
               params.put("pincode",pincode);

               return params;
           }
       };

       stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,5,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
       RequestQueue requestQueue = Volley.newRequestQueue(AddressDetails.this);
       requestQueue.getCache().clear();
       requestQueue.add(stringRequest);

   }

   public void getaddressDetails(String userid){

        addressDetails.clear();

       ProgressDialog progressDialog = new ProgressDialog(AddressDetails.this);
       progressDialog.show();
       progressDialog.setContentView(R.layout.progress_dialog);
       TextView textView = progressDialog.findViewById(R.id.text);
       textView.setText("View Address Please wait...");
       progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
       progressDialog.setCancelable(false);

       StringRequest stringRequest = new StringRequest(Request.Method.POST, APPURLS.getAddressDetails, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {

               progressDialog.dismiss();

               try {
                   JSONObject jsonObject = new JSONObject(response);

                   String message = jsonObject.getString("success");

                   if(message.equals("true")){

                       String address = jsonObject.getString("All_address");

                       JSONArray jsonArray = new JSONArray(address);

                       for(int i=0;i<jsonArray.length();i++){

                           JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                           addressId = jsonObject1.getString("addres_id");
                           Name = jsonObject1.getString("name");
                           city_id = jsonObject1.getString("city_id");
                           City = jsonObject1.getString("city_name");
                           Area = jsonObject1.getString("area");
                           PinCode = jsonObject1.getString("pincode");
                           MobileNo = jsonObject1.getString("number");
                           Address = jsonObject1.getString("address");
                           Email = jsonObject1.getString("email");


                           ViewAddressDetails_ModelClass viewAddressDetails_modelClass = new ViewAddressDetails_ModelClass(
                                   addressId,city_id,Name,City,Area,PinCode,MobileNo,Address,Email
                           );

                          /* if(addressDetails.size()>0){

                               addressDetails.clear();
                           }*/

                           addressDetails.add(viewAddressDetails_modelClass);
                       }

                       String addressdetails = "addressdetails";

                       AddressRecycler.setLayoutManager(linearLayoutManager);
                       AddressRecycler.setHasFixedSize(true);
                       viewaddressDetailsAdapter = new ViewaddressDetailsAdapter(AddressDetails.this,addressDetails,addressdetails);
                       AddressRecycler.setAdapter(viewaddressDetailsAdapter);

                   }
               } catch (JSONException e) {
                   e.printStackTrace();
               }


           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {

               progressDialog.dismiss();
               error.printStackTrace ();

           }
       }){
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {

               Map<String,String> params = new HashMap<>();

               params.put("id",userid);
               return params;
           }
       };

       stringRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
       RequestQueue requestQueue = Volley.newRequestQueue(AddressDetails.this);
       requestQueue.getCache().clear();
       requestQueue.add(stringRequest);

   }

    public boolean isValideFullName(final String fullName){

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$";

        pattern = Pattern.compile (PASSWORD_PATTERN);
        matcher = pattern.matcher (fullName);

        return matcher.matches ( );
    }

    public boolean isValideMobileNumber(final String mobileno){

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^[+]?[0-9]{10}$";

        pattern = Pattern.compile (PASSWORD_PATTERN);
        matcher = pattern.matcher (mobileno);

        return matcher.matches ( );
    }

    public boolean isValideCityAreaAddress(final String address){

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$";

        pattern = Pattern.compile (PASSWORD_PATTERN);
        matcher = pattern.matcher (address);

        return matcher.matches ( );
    }

    public boolean isValideEmailAddress(final String emailaddress){

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";

        pattern = Pattern.compile (PASSWORD_PATTERN);
        matcher = pattern.matcher (emailaddress);

        return matcher.matches ( );
    }

    public void getCity() {


        ProgressDialog progressDialog = new ProgressDialog(AddressDetails.this);
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

                            City_ModelClass city_modelClass = new City_ModelClass
                                    (jsonObject1.getString("city_name"), cityid);

                            list_city.add(city_modelClass);

                        }


                        CitySpinerAdapter adapter = new CitySpinerAdapter(AddressDetails.this, android.R.layout.simple_spinner_dropdown_item
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
                            city_Name = mystate.getCity();
                            Log.d("R_Pincode", City_id);
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

        RequestQueue requestQueue = Volley.newRequestQueue(AddressDetails.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    public void GetPincode(String city_id) {

        arrayListPincode.clear();

        ProgressDialog progressDialog = new ProgressDialog(AddressDetails.this);
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

                        PincodeSpinerAdapter pincodeSpinerAdapter = new PincodeSpinerAdapter(AddressDetails.this, android.R.layout.simple_spinner_dropdown_item
                                , arrayListPincode);
                        spiner_Pincode.setAdapter(pincodeSpinerAdapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //spiner_Pincode.setSelection(-1, true);

                spiner_Pincode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        try {

                            PinCode_ModelClass mystate = (PinCode_ModelClass) parent.getSelectedItem();

                            pincode_Name = mystate.getPincode();

                            Log.d("R_Pincode", City_id);

                            //Log.d("Pinocde_array",janamam.toString());

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
        RequestQueue requestQueue = Volley.newRequestQueue(AddressDetails.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

}