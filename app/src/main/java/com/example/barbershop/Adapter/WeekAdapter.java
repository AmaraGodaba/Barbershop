package com.example.barbershop.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershop.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.ViewHolder> {
    private ArrayList<Date> dates;
    private OnDateClickListener onDateClickListener;

    public WeekAdapter(ArrayList<Date> dates, OnDateClickListener onDateClickListener) {
        this.dates = dates;
        this.onDateClickListener = onDateClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date, parent, false);
        return new ViewHolder(view, onDateClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Date date = dates.get(position);
        // Format the date as you wish
        String formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
        holder.tvDate.setText(formattedDate);
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvDate;
        OnDateClickListener onDateClickListener;

        public ViewHolder(@NonNull View itemView, OnDateClickListener onDateClickListener) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date);
            this.onDateClickListener = onDateClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onDateClickListener.onDateClick(getAdapterPosition());
        }
    }

    public interface OnDateClickListener {
        void onDateClick(int position);
    }
}
