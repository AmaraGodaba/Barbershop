package com.example.barbershop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.barbershop.Model.User;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Intent intent = getIntent();

        User CurrentUser = (User) intent.getSerializableExtra("User");
        ImageButton Booking = findViewById(R.id.BookingImage);



        Booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Main = new Intent(HomePage.this,BookingActivity.class);
                startActivity(Main);
            }
        });
    }
}