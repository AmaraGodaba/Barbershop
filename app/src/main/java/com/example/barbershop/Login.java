package com.example.barbershop;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://barbershopdb-83e5e-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        final EditText InputPhone = findViewById(R.id.LoginPhone);
        final EditText InputPass = findViewById(R.id.LoginPassword);
        final Button Login = findViewById(R.id.BtnLgn);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String PN = InputPhone.getText().toString();
                final String PW = InputPass.getText().toString();
            }
        });
    }
}