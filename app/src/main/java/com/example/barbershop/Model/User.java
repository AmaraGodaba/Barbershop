package com.example.barbershop.Model;
import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class User implements  Serializable {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://barbershopdb-83e5e-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

    private String PhoneNumber;
    private String Name;
    private String Email;
    private Boolean Barber;
    public User(){
        PhoneNumber = "";
        Name = "" ;
        Email = "";
        Barber = false;
    }
    public User(String P, String N,String E,Boolean B){
        this.PhoneNumber = P;
        this.Name = N;
        this.Email = E;
        this.Barber = B;
    }
    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Boolean getBarber() {
        return Barber;
    }

    public void setBarber(Boolean barber) {
        Barber = barber;
    }
    public Boolean IsNotUsed(String P){
        final Boolean[] Answer = {false};
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(P)) {
                    Answer[0] = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
       return Answer[0];
    }
    public void UpdateDetails(String NP,String CP, String N,String E){
        if(!IsNotUsed(CP)){


        }

    }
}
