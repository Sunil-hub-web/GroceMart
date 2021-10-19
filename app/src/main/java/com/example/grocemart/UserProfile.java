package com.example.grocemart;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {

    CircleImageView circleImageView;
    ImageView imageView;
    public static final int IMAGE_CODE = 1;
    ImageView image_back;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.userprofile);

        circleImageView = findViewById(R.id.profile_image);
        imageView = findViewById(R.id.imageview);

        image_back = findViewById(R.id.back);

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
    }
}
