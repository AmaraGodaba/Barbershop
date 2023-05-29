package com.example.barbershop.Fragments;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershop.Interface.ITimeSlotLoadListener;
import com.example.barbershop.Model.HorizontalCalendarView;
import com.example.barbershop.R;
import com.google.firebase.firestore.DocumentReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.Unbinder;

public class BookingStep3Fragment extends Fragment {
    static BookingStep3Fragment instance;
    DocumentReference barberDoc;
    ITimeSlotLoadListener iTimeSlotLoadListener;
    Unbinder unbinder;
    LocalBroadcastManager localBroadcastManager;
    Calendar selected_date;
    @BindView(R.id.recycler_time_Slot)
    RecyclerView recycler_time_slot;
    @BindView(R.id.calendarView)
    HorizontalCalendarView calendarView;
    SimpleDateFormat simpleDateFormat;
    BroadcastReceiver displayTimeSlot
    public static  BookingStep3Fragment getInstance() {
        if (instance == null) {
            instance = new BookingStep3Fragment();
        }
        return instance;

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_booking_step_three,container,false);

    }
}
