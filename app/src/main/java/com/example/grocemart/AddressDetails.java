package com.example.grocemart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.example.grocemart.url.APPURLS;

import java.util.HashMap;
import java.util.Map;

public class AddressDetails extends AppCompatActivity {

    ImageView image_back;
    Button btn_AddAddress;
    String str_Name,str_Email,srt_MobileNo,Str_City,str_Area,str_Address,str_PinCode,userId;
    Button btn_SaveAddress;
    EditText edit_Name,edit_Email,edit_Address,edit_MobileNo,edit_Area;
    Spinner spiner_City,spiner_Pincode;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_details);

        image_back = findViewById(R.id.back);
        btn_AddAddress = findViewById(R.id.addaddress);

        userId = SharedPrefManager.getInstance(AddressDetails.this).getUser().getId();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation (AddressDetails.this,R.id.fullName,"^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$",R.string.entername);
        awesomeValidation.addValidation (AddressDetails.this,R.id.area,"^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$",R.string.entername);
        awesomeValidation.addValidation (AddressDetails.this,R.id.contactNo,"^[+]?[0-9]{10}$",R.string.entercontact);
        awesomeValidation.addValidation (AddressDetails.this,R.id.emailAddress, Patterns.EMAIL_ADDRESS,R.string.enteremail);


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

        final Dialog dialog = new Dialog(AddressDetails.this);
        dialog.setContentView(R.layout.address_details);
        dialog.setCancelable(false);
        btn_SaveAddress = dialog.findViewById(R.id.saveaddress);
        edit_Name = dialog.findViewById(R.id.fullName);
        edit_Email = dialog.findViewById(R.id.emailAddress);
        edit_Address = dialog.findViewById(R.id.Address);
        edit_MobileNo = dialog.findViewById(R.id.contactNo);
        spiner_City = dialog.findViewById(R.id.spinner_city);
        spiner_Pincode = dialog.findViewById(R.id.spinner_pincode);
        edit_Area = dialog.findViewById(R.id.area);


        btn_SaveAddress.setOnClickListener(new View.OnClickListener() {
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

       StringRequest stringRequest = new StringRequest(Request.Method.POST, APPURLS.addAddress, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {

           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {

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
       requestQueue.add(stringRequest);

   }

    public  void deleteAddress(String AddressId){

    }
}