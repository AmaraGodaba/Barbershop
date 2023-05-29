package com.example.barbershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;



import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.barbershop.Adapter.MyViewPagerAdapter;
import com.example.barbershop.Common.Common;
import com.example.barbershop.Common.NonSwipeViewPager;
import com.example.barbershop.Model.Barber;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class BookingActivity extends AppCompatActivity {
    @BindView(R.id.btn_next_step)
    Button Next;
    CollectionReference barberRef;
    StepView stepView;
    Button Previous;
    NonSwipeViewPager  viewPager;
    LocalBroadcastManager localBroadcastManager;
    @OnClick(R.id.btn_next_step)
    void nextClick(){
        Log.d("Click","The click is still not working what am i doing wrong");
        Toast.makeText(this,"Test123" + Common.currentSalon.getSalonId(),Toast.LENGTH_SHORT).show();

    }

    private BroadcastReceiver buttonNextReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int step = intent.getIntExtra(Common.KEY_STEP,0);
            if(step == 1 )
                Common.currentSalon = intent.getParcelableExtra(Common.KEY_SALON_STORE);
            else if(step == 2)
                Common.currentBaber = intent.getParcelableExtra(Common.KEY_BARBER_SELECTED);
            Common.currentSalon = intent.getParcelableExtra(Common.KEY_SALON_STORE);
            Next.setEnabled(true);
            setcolorButton();
        }
    };

    @Override
    protected void onDestroy() {
        localBroadcastManager.unregisterReceiver(buttonNextReceiver);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(buttonNextReceiver,new IntentFilter(Common.KEY_ENABLE_BUTTON_NEXT));
        stepView = findViewById(R.id.step_view);
        Next = findViewById(R.id.btn_next_step);
        Previous = findViewById(R.id.btn_previous);
        viewPager = findViewById(R.id.view_pager);
        setupStepUpView();
        setcolorButton();
        Previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Common.step ==3 || Common.step >0 ){
                    Common.step--;
                    viewPager.setCurrentItem(Common.step);
                }

            }
        });
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Common.step < 3 ||Common.step == 0 ){
                    Common.step++;
                    if(Common.step == 1){
                        if(Common.currentSalon != null)
                            loadBarberBySalon(Common.currentSalon.getSalonId());

                    }
                    else if(Common.step == 2)
                    {
                        if(Common.currentBaber != null){
                            loadTimeSlotOfBarber(Common.currentBaber.getBarberId());
                        }
                    }
                    viewPager.setCurrentItem(Common.step);
                }
            }

            private void loadTimeSlotOfBarber(String barberId) {
                Intent intent = new Intent(Common.KEY_DISPLAY_TIME_SLOT);
                localBroadcastManager.sendBroadcast(intent);

            }

            private void loadBarberBySalon(String salonId) {
                if (!TextUtils.isEmpty(Common.city)) {
                    barberRef = FirebaseFirestore.getInstance()
                            .collection("AllSalon")
                            .document(Common.city)
                            .collection("Branch")
                            .document(salonId)
                            .collection("Barber");
                    barberRef.get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    ArrayList<Barber> barbers = new ArrayList<>();
                                            for(QueryDocumentSnapshot barberSnapShot:task.getResult())
                                            {
                                                Barber barber = barberSnapShot.toObject(Barber.class);
                                                barber.setPassword("");
                                                barber.setBarberId(barberSnapShot.getId());
                                                barbers.add(barber);
                                            }
                                            Intent intent = new Intent(Common.KEY_BARBER_LOAD_DONE);
                                            intent.putParcelableArrayListExtra(Common.KEY_BARBER_LOAD_DONE,barbers);
                                            localBroadcastManager.sendBroadcast(intent);

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                }
            }

        });
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int ind) {
                stepView.go(ind,true);
                if (ind == 0) {
                    Previous.setEnabled(false);
                } else {
                    Previous.setEnabled(true);
                }
                Next.setEnabled(false);
                setcolorButton();
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        }


    private void setcolorButton() {

        if(Next.isEnabled()){
            Next.setBackgroundResource(R.drawable.buttoncolors);
        }
        else
        {
            Next.setBackgroundResource(R.drawable.graybutton);
        }
        if(Previous.isEnabled()){
            Previous.setBackgroundResource(R.drawable.buttoncolors);
        }
        else
        {
            Previous.setBackgroundResource(R.drawable.graybutton);
        }
    }

    private void setupStepUpView(){
        List<String> stepList = new ArrayList<>();
        stepList.add("Salon");
        stepList.add("Barber");
        stepList.add("Time");
        stepList.add("Confirm");
        stepView.setSteps(stepList);
    }
}
