package com.example.grocemart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class UserDashboard extends AppCompatActivity {

    TextView text_Profile,text_ChangePassword,text_AddAddress,text_MyOrder,text_Wallet,text_logout;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        text_Profile = findViewById(R.id.nav_profile);
        text_ChangePassword = findViewById(R.id.nav_changepassword);
        text_AddAddress = findViewById(R.id.nav_address);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        text_MyOrder = findViewById(R.id.nav_myorder);
        text_Wallet = findViewById(R.id.nav_wallet);
        text_logout = findViewById(R.id.nav_logout);


        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {

                switch(item.getItemId()){

                    case R.id.dashboard :
                       return true;

                    case R.id.home :
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.cart:
                        startActivity(new Intent(getApplicationContext(),CartPage.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(),SerachPage.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });

        text_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserDashboard.this,UserProfile.class);
                startActivity(intent);

            }
        });

        text_ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserDashboard.this,ChangePassword.class);
                startActivity(intent);

            }
        });

        text_AddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserDashboard.this,AddressDetails.class);
                startActivity(intent);

            }
        });

        text_MyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserDashboard.this,MyOrderDetails.class);
                startActivity(intent);

            }
        });

        text_Wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserDashboard.this,WalletPage.class);
                startActivity(intent);

            }
        });

        text_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logout_Condition();
            }
        });

    }

     @Override
    protected void onStart() {
        super.onStart ( );
        if (SharedPrefManager.getInstance (UserDashboard.this).isLoggedIn ()){ }

        else{
            Intent intent = new Intent ( UserDashboard.this, SigninPage.class );
            startActivity (intent);
        }
    }

    public void logout_Condition() {
        //Show Your Another AlertDialog
        final Dialog dialog = new Dialog(UserDashboard.this);
        dialog.setContentView(R.layout.condition_logout);
        dialog.setCancelable(false);
        Button btn_No = dialog.findViewById(R.id.no);
        TextView textView = dialog.findViewById(R.id.editText);
        Button btn_Yes = dialog.findViewById(R.id.yes);

        btn_Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPrefManager.getInstance(UserDashboard.this).logout();
                finish();
                dialog.dismiss();
            }
        });
        btn_No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.drawable.homecard_back);

    }
}