package com.example.grocemart.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.grocemart.R;
import com.example.grocemart.SharedPrefManager;
import com.example.grocemart.url.APPURLS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {

    ImageView image_back;
    TextView password,newPassword,conformPassword;
    Button btn_Update;
    String str_password,str_newPassword,str_conformPassword,str_pwd,str_userId;
    private static final String TAG = "MyActivity";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);

        image_back = findViewById(R.id.back);
        btn_Update = findViewById(R.id.btn_Update);
        password = findViewById(R.id.password);
        newPassword = findViewById(R.id.newPassword);
        conformPassword = findViewById(R.id.conformPassword);

        str_pwd = SharedPrefManager.getInstance (ChangePassword.this).getUser ().getPassword ();
        str_userId = SharedPrefManager.getInstance (ChangePassword.this).getUser ().getId ();

        password.setText (str_pwd);

        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ChangePassword.this,UserDashboard.class);
                startActivity(intent);
            }
        });

        btn_Update.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                str_password = password.getText ().toString ().trim ();
                str_newPassword = newPassword.getText ().toString ().trim ();
                str_conformPassword = conformPassword.getText ().toString ().trim ();

                if(str_newPassword.equals (str_conformPassword)){

                    changePassword (str_userId,str_password,str_newPassword,str_conformPassword);

                }else{

                    conformPassword.setError ("Password is not match");
                    Toast.makeText (ChangePassword.this, "Password is not match", Toast.LENGTH_SHORT).show ( );

                }
            }
        });
    }

    public void changePassword(String userid,String oldPassword,String new_Password,String conform_Password){

        ProgressDialog progressDialog = new ProgressDialog(ChangePassword.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Change Password Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APPURLS.changePassword, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");

                    if(message.equals("true")){

                        String msg = jsonObject.getString("msg");

                        Toast.makeText(ChangePassword.this, msg, Toast.LENGTH_SHORT).show();

                        password.setText("");
                        newPassword.setText("");
                        conformPassword.setText("");

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

                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("user_id",userid);
                params.put("old_password",oldPassword);
                params.put("new_password",new_Password);
                params.put("cnf_password",conform_Password);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ChangePassword.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
}
