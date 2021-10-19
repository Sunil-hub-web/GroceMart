package com.example.grocemart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class ForgotPasswordPage extends AppCompatActivity {

    AwesomeValidation awesomeValidation;

    EditText edit_NewPasword,edit_ReenterPassword;
    Button btn_ChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_page);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        edit_NewPasword = findViewById(R.id.Newpassword);
        edit_ReenterPassword = findViewById(R.id.reenterpassword);
        btn_ChangePassword = findViewById(R.id.change_Password);

        awesomeValidation.addValidation (ForgotPasswordPage.this,R.id.Newpassword,"^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$",R.string.enterpassword);
        awesomeValidation.addValidation (ForgotPasswordPage.this,R.id.reenterpassword,"^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$",R.string.enterpassword);

        btn_ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(awesomeValidation.validate()){

                    if(edit_NewPasword.getText().toString().trim().equals(edit_ReenterPassword.getText().toString().trim())){

                        edit_ReenterPassword.setError("Password Not Match");

                    }else{

                        Toast.makeText(ForgotPasswordPage.this, "Successfully", Toast.LENGTH_SHORT).show();
                    }

                }else{

                    Toast.makeText(ForgotPasswordPage.this, "Un_Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}