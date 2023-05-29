package com.example.barbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barbershop.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class LoginActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://barbershopdb-83e5e-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button Login = findViewById(R.id.BtnLgn);
        EditText Number = findViewById(R.id.EDPh);
        EditText Password = findViewById(R.id.EDPass);
        TextView NewUser = findViewById(R.id.txtNewUser);

        NewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginPage = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(LoginPage);
            }

        });
        Login.setOnClickListener(new View.OnClickListener() {


            @Override

            public void onClick(View v) {
                final String PN = Number.getText().toString();
                final String PW = Password.getText().toString();
            if(PN.isEmpty() || PW.isEmpty()){

                System.out.println("The phone number" + PN + "The Password is" + PW);

                Toast.makeText(LoginActivity.this, "Please enter password and phone number", Toast.LENGTH_SHORT).show();

            }
            else{
                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        System.out.println("The phone number" + PN + "The Password is" + PW);

                        System.out.println(snapshot.child("+447500295750").child("Password").toString());
                        if(snapshot.hasChild(PN)){
                            final String getP = snapshot.child(PN).child("Password").getValue(String.class);
                            if (getP.equals(PW)){
                                Toast.makeText(LoginActivity.this, "Logged in successful", Toast.LENGTH_SHORT).show();
                                User CurrentUser = new User(PN,snapshot.child("+447500295750").child("Name").toString(),snapshot.child("+447500295750").child("Email").toString(),Boolean.valueOf(snapshot.child("+447500295750").child("IsBarber").toString()));
                                if (snapshot.child(PN).child("IsBarber").getValue(Boolean.class)){

                                    Intent Main = new Intent(LoginActivity.this,BarberHomePage.class);
                                   // Main.putExtra("Users",CurrentUser);
                                    startActivity(Main);

                                }else{

                                    Intent Main = new Intent(LoginActivity.this,HomePage.class);
                                    //Main.putExtra("Users",CurrentUser);
                                    startActivity(Main);
                                }

                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();

                            }
                        }
                        else{

                            Toast.makeText(LoginActivity.this, "The number does not exist in our system", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
            }
        });

    }
}