package com.example.grocemart.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.grocemart.R;
import com.example.grocemart.SharedPrefManager;
import com.example.grocemart.modelclass.Login_ModelClass;
import com.example.grocemart.url.APPURLS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SigninPage extends AppCompatActivity {

    TextView text_CreateAccount, text_ForgotPassword;
    EditText edit_fullname, edit_password;
    Button signin;
    String fullname, password, userId, userName, userMobileNo, userPassword, userEmailID;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_page);

        text_CreateAccount = findViewById(R.id.createaccount);
        text_ForgotPassword = findViewById(R.id.forgtPassword);
        edit_fullname = findViewById(R.id.edit_fullname);
        edit_password = findViewById(R.id.edit_password);
        signin = findViewById(R.id.signin);

        text_CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SigninPage.this, RegisterPage.class);
                startActivity(intent);

            }
        });

        text_ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SigninPage.this, ForgotPassword.class);
                startActivity(intent);

            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fullname = edit_fullname.getText().toString().trim();
                password = edit_password.getText().toString().trim();

                if (TextUtils.isEmpty(edit_fullname.getText())) {
                    edit_fullname.setError("name is no empty");
                } else if (TextUtils.isEmpty(edit_password.getText())) {
                    edit_password.setError("password is not empty");
                } else {
                    userLogin(fullname, password);
                }

            }
        });
    }


    public void userLogin(String userNmae, String password) {

        ProgressDialog progressDialog = new ProgressDialog(SigninPage.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Login Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, APPURLS.Login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("success");

                    if (message.equals("true")) {
                        Toast.makeText(SigninPage.this, "Login successFully", Toast.LENGTH_SHORT).show();

                        userId = jsonObject.getString("id");
                        userName = jsonObject.getString("name");
                        userEmailID = jsonObject.getString("email");
                        userMobileNo = jsonObject.getString("contact_no");
                        userPassword = edit_password.getText().toString().trim();

                        Login_ModelClass login_modelClass = new Login_ModelClass(userId,userName,userEmailID,userMobileNo,userPassword);
                        SharedPrefManager.getInstance(SigninPage.this).userLogin(login_modelClass);

                        Intent intent = new Intent ( SigninPage.this, MainActivity.class );
                        startActivity (intent);
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
                Toast.makeText(SigninPage.this, "userName or Password not exits", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("usr_name", userNmae);
                params.put("password", password);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(SigninPage.this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent ( SigninPage.this, MainActivity.class );
        startActivity (intent);
    }
}