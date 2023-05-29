package com.example.barbershop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
  public void Login(View view){
      Intent LoginPage = new Intent(this,LoginActivity.class);
      startActivity(LoginPage);
  }
  public void Register(View view){
        Intent RegisterPage = new Intent(this,RegisterActivity.class);
        startActivity(RegisterPage);

  }

}