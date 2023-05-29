package com.example.barbershop.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershop.Adapter.MyBarberAdapter;
import com.example.barbershop.Common.Common;
import com.example.barbershop.Common.SpacesItemDecoration;
import com.example.barbershop.Model.Barber;
import com.example.barbershop.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BookingStep2Fragment extends Fragment {

    Unbinder unbinder;
    LocalBroadcastManager localBroadcastManager;
    @BindView(R.id.recycler_barber)
    RecyclerView recyler_barber;
    private BroadcastReceiver barberDonereceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Barber> barberArrayList = intent.getParcelableArrayListExtra(Common.KEY_BARBER_LOAD_DONE);
            MyBarberAdapter adapter = new MyBarberAdapter(getContext(),barberArrayList);
            recyler_barber.setAdapter(adapter);
        }
    };
    static BookingStep2Fragment instance;
    public static  BookingStep2Fragment getInstance() {
        if (instance == null) {
            instance = new BookingStep2Fragment();
        }
        return instance;

    }
        @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(barberDonereceiver,new IntentFilter(Common.KEY_BARBER_LOAD_DONE));
    }
    @Override
    public void onDestroy(){
        localBroadcastManager.unregisterReceiver(barberDonereceiver);
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View itemView = inflater.inflate(R.layout.fragment_booking_step_two,container,false);
        unbinder = ButterKnife.bind(this,itemView);
        initView();
        return itemView ;

    }
    private  void initView(){
        recyler_barber.setHasFixedSize(true);
        recyler_barber.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyler_barber.addItemDecoration(new SpacesItemDecoration(4));
    }
}
