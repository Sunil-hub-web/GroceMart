package com.example.grocemart.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.grocemart.url.APPURLS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterPage extends AppCompatActivity {

    TextView text_Signin;
    EditText edit_fullName,edit_contactNo,edit_emailAddress,edit_userName,edit_setPassword;
    Button btn_register;
    String fullName,contactNo,emailAddress,userName,setPassword;
    private AwesomeValidation awesomeValidation;
    CheckBox check;

    @Override
    public void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        text_Signin = findViewById(R.id.signin);
        edit_fullName = findViewById(R.id.edit_fullName);
        edit_contactNo = findViewById(R.id.edit_contactNo);
        edit_emailAddress = findViewById(R.id.edit_emailAddress);
        edit_userName = findViewById(R.id.edit_userName);
        edit_setPassword = findViewById(R.id.edit_setPassword);
        btn_register = findViewById(R.id.btn_register);
        check = findViewById(R.id.check);

        awesomeValidation = new AwesomeValidation (ValidationStyle.BASIC);

       awesomeValidation.addValidation (RegisterPage.this,R.id.edit_fullName,"^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$",R.string.entername);
       awesomeValidation.addValidation (RegisterPage.this,R.id.edit_userName,"^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$",R.string.entername);
       awesomeValidation.addValidation (RegisterPage.this,R.id.edit_contactNo,"^[+]?[0-9]{10}$",R.string.entercontact);
       awesomeValidation.addValidation (RegisterPage.this,R.id.edit_emailAddress, Patterns.EMAIL_ADDRESS,R.string.enteremail);
       awesomeValidation.addValidation (RegisterPage.this,R.id.edit_setPassword,"^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$",R.string.enterpassword);


        text_Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegisterPage.this,SigninPage.class);
                startActivity(intent);

            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fullName = edit_fullName.getText().toString().trim();
                contactNo = edit_contactNo.getText().toString().trim();
                emailAddress = edit_emailAddress.getText().toString().trim();
                userName = edit_userName.getText().toString().trim();
                setPassword = edit_setPassword.getText().toString().trim();

                if(awesomeValidation.validate()){

                    if(check.isChecked()){

                        Toast.makeText(RegisterPage.this, "Validation Successfully", Toast.LENGTH_SHORT).show();

                        userRegister(fullName,contactNo,emailAddress,userName,setPassword);

                    }else{
                        Toast.makeText(RegisterPage.this, "Please select Terms and Conditions", Toast.LENGTH_SHORT).show();
                    }
                }else{

                    Toast.makeText(RegisterPage.this, "validation Un successFull", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void userRegister(String name,String contact,String email,String username,String password){

        ProgressDialog progressDialog = new ProgressDialog(RegisterPage.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Register Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APPURLS.Register, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("success");
                    if(message.equals("true")){
                        Toast.makeText(RegisterPage.this, "Successfully Register..", Toast.LENGTH_SHORT).show();
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
                Toast.makeText (RegisterPage.this, "Register not Successfully, userName or email exits", Toast.LENGTH_SHORT).show ( );

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("fullname",name);
                params.put("contact",contact);
                params.put("email",email);
                params.put("username",username);
                params.put("password",password);

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterPage.this);
        requestQueue.add(stringRequest);

    }

}
