package com.example.barbershop.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershop.R;

import java.time.LocalDate;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

    private List<LocalDate> days;

    public CalendarAdapter(List<LocalDate> days) {
        this.days = days;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CalendarViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        LocalDate date = days.get(position);
        holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
        holder.dayOfWeek.setText(date.getDayOfWeek().toString());
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    class CalendarViewHolder extends RecyclerView.ViewHolder {
        TextView dayOfMonth, dayOfWeek;

        CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            dayOfMonth = itemView.findViewById(R.id.dayOfMonth);
            dayOfWeek = itemView.findViewById(R.id.dayOfWeek);
        }
    }
}
