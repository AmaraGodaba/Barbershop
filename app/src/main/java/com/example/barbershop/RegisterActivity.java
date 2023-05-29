package com.example.barbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {

    private static final String UK_PHONE_NUMBER_PATTERN = "^\\+(44)[1-9]\\d{8,14}$";
    private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://barbershopdb-83e5e-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText InputFirstName = findViewById(R.id.EDFirstName);
        final EditText  InputSecondName = findViewById(R.id.EDSecondName);
        final EditText InputPassword = findViewById(R.id.EDPassword);
        final EditText InputPhone = findViewById(R.id.EDPh);
        final EditText InputEmail = findViewById(R.id.EDEmail);
        final CheckBox InputBarber = findViewById(R.id.IsBarber);
        final TextView HaveAccount = findViewById(R.id.AccountHaver);
        Button Save = findViewById(R.id.BtnSignUp);
        HaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginPage = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(LoginPage);
            }
        });
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(RegisterActivity.this, "Stage 1 passed", Toast.LENGTH_LONG).show();

                final String FN = InputFirstName.getText().toString();
                final String SN = InputSecondName.getText().toString();
                final String Phone = InputPhone.getText().toString();
                final String Password = InputPassword.getText().toString();
                final String Email = InputEmail.getText().toString();
                final boolean IsB = InputBarber.isChecked();
                if (FN.isEmpty() || SN.isEmpty() || Phone.isEmpty() || Password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill all the fields", Toast.LENGTH_LONG).show();

                }
                else if(!isValid(Phone)) {
                  Toast.makeText(RegisterActivity.this, "Enter correct Phone Number", Toast.LENGTH_SHORT).show();
                }
                else if(!isValidEmail(Email)){
                    Toast.makeText(RegisterActivity.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Toast.makeText(RegisterActivity.this, "Stage 2 passed", Toast.LENGTH_LONG).show();

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override

                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.hasChild(Phone)) {
                                Toast.makeText(RegisterActivity.this, "Phone already exist", Toast.LENGTH_SHORT).show();
                            } else {

                                databaseReference.child("users").child(Phone).child("Name").setValue(FN + " "+ SN);
                                databaseReference.child("users").child(Phone).child("Password").setValue(Password);
                                databaseReference.child("users").child(Phone).child("Email").setValue(Email);
                                databaseReference.child("users").child(Phone).child("IsBarber").setValue(IsB);
                                Toast.makeText(RegisterActivity.this, "Register successful", Toast.LENGTH_SHORT).show();
                                finish();
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
    public static boolean isValid(String phoneNumber) {
        Pattern pattern = Pattern.compile(UK_PHONE_NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
    public static boolean isValidEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
}





