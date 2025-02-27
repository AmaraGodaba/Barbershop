package com.example.barbershop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershop.Common.Common;
import com.example.barbershop.Interface.IRecyclerItemSelectedListener;
import com.example.barbershop.Model.Barber;
import com.example.barbershop.R;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class MyBarberAdapter extends RecyclerView.Adapter<MyBarberAdapter.MyViewHolder> {
    Context context;
    List<Barber> barberList;
    LocalBroadcastManager localBroadcastManager;
    List<CardView> cardViewList;
    public MyBarberAdapter(Context context,List<Barber> barberList){
        this.context = context;
        this.barberList = barberList;
        cardViewList = new ArrayList<>();
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }
    @androidx.annotation.NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_barber,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull MyViewHolder holder, int position) {
        holder.txt_barber_name.setText(barberList.get(position).getName());
        Long rating = barberList.get(position).getRating();
        if (rating != null) {
            holder.ratingBar.setRating(rating.floatValue());
        }
        if(cardViewList.contains(holder.card_barber)){
            cardViewList.add(holder.card_barber);
        }
        holder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @Override
            public void onItemSelectedListener(View view, int pos) {
                for(CardView c : cardViewList){
                    c.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));
                }
                holder.card_barber.setCardBackgroundColor(
                context.getResources()
                        .getColor(android.R.color.holo_orange_light)
                );

                Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                intent.putExtra(Common.KEY_BARBER_SELECTED,barberList.get(pos));
                intent.putExtra(Common.KEY_STEP,2);
                localBroadcastManager.sendBroadcast(intent);
;            }
        });
    }

    @Override
    public int getItemCount() {
        return barberList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_barber_name;
        RatingBar ratingBar;
        CardView card_barber;
        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;

        public void setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener) {
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
        }

        public MyViewHolder(@NonNull View ItemView){
            super(ItemView);
            card_barber = (CardView)ItemView.findViewById(R.id.card_barber);
            txt_barber_name = (TextView)ItemView.findViewById(R.id.txt_barber_name);
            ratingBar = (RatingBar)ItemView.findViewById(R.id.rtb_barber);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            iRecyclerItemSelectedListener.onItemSelectedListener(view,getAdapterPosition());
        }

    }
}
