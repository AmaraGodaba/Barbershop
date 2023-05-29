package com.example.barbershop.Adapter;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barbershop.Common.Common;
import com.example.barbershop.Interface.IRecyclerItemSelectedListener;
import com.example.barbershop.Model.Salon;
import com.example.barbershop.R;

import java.util.ArrayList;
import java.util.List;


public class MySalonAdapter extends RecyclerView.Adapter<MySalonAdapter.MyViewHolder> {
    Context context;
    List<Salon> salonList;
    List<CardView> cardViewList;
    LocalBroadcastManager localBroadcastManager;
    public MySalonAdapter(Context context, List<Salon> salonList) {
        this.context = context;
        this.salonList = salonList;
        cardViewList = new ArrayList<>();
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_salon,viewGroup,false);

        return new MySalonAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.SalonName.setText(salonList.get(position).getName());
        holder.SalonAddress.setText(salonList.get(position).getAddress());

        if (!cardViewList.contains(holder.card_salon))
            cardViewList.add(holder.card_salon);
        holder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @Override
            public void onItemSelectedListener(View view, int pos) {
                // Reset the background color of all CardViews to default
                for (CardView c : cardViewList) {
                    c.setCardBackgroundColor(Color.parseColor("#2B3654"));
                }

                // Set the background color of the clicked CardView to holo_orange_light
                holder.card_salon.setCardBackgroundColor(context.getResources().getColor(android.R.color.holo_orange_light));
                System.out.println(holder.SalonName.getText());
                Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                intent.putExtra(Common.KEY_SALON_STORE,salonList.get(pos));
                intent.putExtra(Common.KEY_STEP,1);
                localBroadcastManager.sendBroadcast(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return salonList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView SalonName,SalonAddress;
        CardView card_salon;
        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;
        public void setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener){
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
        }
        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            card_salon = (CardView)itemView.findViewById(R.id.card_salon);
            SalonAddress = (TextView)itemView.findViewById(R.id.txtSalonAddress);
            SalonName = (TextView)itemView.findViewById(R.id.txtSalonName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            System.out.println(iRecyclerItemSelectedListener);
            iRecyclerItemSelectedListener.onItemSelectedListener(v,getAdapterPosition());
        }
    }
}
