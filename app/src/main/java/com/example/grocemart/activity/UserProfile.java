package com.example.grocemart.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.grocemart.R;
import com.example.grocemart.SharedPrefManager;
import com.example.grocemart.url.APPURLS;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {

    CircleImageView circleImageView;
    ImageView imageView;
    public static final int IMAGE_CODE = 1;
    ImageView image_back;
    EditText edit_Name,edit_Email,edit_Contact_no;
    Button btn_Update;

    String id,name,email,contact_no,msg,user_id,profile_photo,Name,Email,Contact_no,image_profile;

    Uri imageUri;

    AwesomeValidation awesomeValidation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.userprofile);

        circleImageView = findViewById(R.id.profile_image);
        imageView = findViewById(R.id.imageview);
        edit_Name = findViewById(R.id.fullname);
        edit_Email = findViewById(R.id.emailAddress);
        edit_Contact_no = findViewById(R.id.contactNo);
        image_back = findViewById(R.id.back);
        btn_Update = findViewById(R.id.update);

        user_id = SharedPrefManager.getInstance(UserProfile.this).getUser().getId();

        getProfile(user_id);

        awesomeValidation = new AwesomeValidation (ValidationStyle.BASIC);

        awesomeValidation.addValidation (UserProfile.this,R.id.fullname,"^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$",R.string.entername);
        awesomeValidation.addValidation (UserProfile.this,R.id.contactNo,"^[+]?[0-9]{10}$",R.string.entercontact);
        awesomeValidation.addValidation (UserProfile.this,R.id.emailAddress, Patterns.EMAIL_ADDRESS,R.string.enteremail);


        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserProfile.this,UserDashboard.class);
                startActivity(intent);
            }
        });

        circleImageView.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent ( );
                intent.setType ("image/*");
                intent.setAction (Intent.ACTION_GET_CONTENT);
                startActivityForResult (intent, IMAGE_CODE);
            }
        });

        imageView.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent ( );
                intent.setType ("image/*");
                intent.setAction (Intent.ACTION_GET_CONTENT);
                startActivityForResult (intent, IMAGE_CODE);
            }
        });

        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Name = edit_Name.getText().toString().trim();
                Email = edit_Email.getText().toString().trim();
                Contact_no = edit_Contact_no.getText().toString().trim();

                if(awesomeValidation.validate()){

                    if(imageUri != null){

                        updateProfile(user_id,Name,Email,Contact_no);
                        uploadProfileImage(user_id,profile_photo);

                    }else{

                        Toast.makeText(UserProfile.this, "Select Your Image", Toast.LENGTH_SHORT).show();
                    }

                }else{

                    Toast.makeText(UserProfile.this, "Fill The Details Properly", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void getProfile(String userId){

        ProgressDialog progressDialog = new ProgressDialog(UserProfile.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Retrive Data Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, APPURLS.getUserDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");
                    String image = jsonObject.getString("img");

                    if(message.equals("true")){

                        if(image.equals("")){

                            Toast.makeText(UserProfile.this, "Success", Toast.LENGTH_SHORT).show();

                            id = jsonObject.getString("id");
                            name = jsonObject.getString("name");
                            email = jsonObject.getString("email");
                            contact_no = jsonObject.getString("contact_no");
                            msg = jsonObject.getString("msg");

                            edit_Name.setText(name);
                            edit_Contact_no.setText(contact_no);
                            edit_Email.setText(email);
                            
                        }else{

                            id = jsonObject.getString("id");
                            name = jsonObject.getString("name");
                            email = jsonObject.getString("email");
                            contact_no = jsonObject.getString("contact_no");
                            msg = jsonObject.getString("msg");
                            image_profile = jsonObject.getString("img");

                            Log.d("image",image_profile);

                            String url = "https://"+image_profile;

                            Picasso.with(UserProfile.this).load(url)
                                    .placeholder(R.drawable.profileimage)
                                    .into(circleImageView);

                            edit_Name.setText(name);
                            edit_Contact_no.setText(contact_no);
                            edit_Email.setText(email);

                        }
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
                params.put("user_id",userId);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,5,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(UserProfile.this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_CODE && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {

            imageUri = data.getData();
            circleImageView.setImageURI(imageUri);

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageUri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap = Bitmap.createScaledBitmap(bitmap, 500, 750, true);
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, baos); //bm is the bitmap object
                byte[] img = baos.toByteArray();
                profile_photo = Base64.encodeToString(img, Base64.DEFAULT);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateProfile(String id, String name, String email, String contactno){

        ProgressDialog progressDialog = new ProgressDialog(UserProfile.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Profile Update Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APPURLS.updateUserDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("msg");

                    if(message.equals("Profile Updated Successfully..")){

                        Toast.makeText(UserProfile.this, "Profile Updated Successfully..", Toast.LENGTH_SHORT).show();

                        getProfile(user_id);
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

                params.put("id",id);
                params.put("name",name);
                params.put("email",email);
                params.put("contact_no",contactno);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,5,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(UserProfile.this);
        requestQueue.add(stringRequest);
    }

    public void uploadProfileImage(String userId,String profileImage){

        ProgressDialog progressDialog = new ProgressDialog(UserProfile.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        TextView textView = progressDialog.findViewById(R.id.text);
        textView.setText("Profile Update Please wait...");
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APPURLS.uploadProfilePic, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("success");

                    if(message.equals("true")){

                        Toast.makeText(UserProfile.this, "Profile image Updated Successfully..", Toast.LENGTH_SHORT).show();

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

                params.put("user_id",userId);
                params.put("img",profileImage);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,5,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(UserProfile.this);
        requestQueue.add(stringRequest);
    }
}
