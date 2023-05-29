package com.example.barbershop.Model;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.barbershop.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HorizontalCalendarView extends LinearLayout {

    private Context mContext;
    private List<Date> mDates;
    private OnDateSelectedListener mListener;
    private int mSelectedPosition = -1;


    public HorizontalCalendarView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public HorizontalCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
    }

    public void setDates(List<Date> dates) {
        mDates = dates;
        notifyDataSetChanged();
    }

    public void setOnDateSelectedListener(OnDateSelectedListener listener) {
        mListener = listener;
    }

    private void notifyDataSetChanged() {
        removeAllViews();
        for (int i = 0; i < mDates.size(); i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_date, this, false);
            TextView tvDate = view.findViewById(R.id.tv_date);
            Date date = mDates.get(i);
            SimpleDateFormat sdf = new SimpleDateFormat("EEE\nMMM dd", Locale.getDefault());
            String dateString = sdf.format(date);
            tvDate.setText(dateString);
            if (i == mSelectedPosition) {
                tvDate.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                tvDate.setBackgroundResource(R.drawable.bg_calendar_date_selected);
            } else {
                tvDate.setTextColor(ContextCompat.getColor(mContext, R.color.colorcode));
                tvDate.setBackgroundResource(R.drawable.bg_calendar_date_unselected);
            }
            final int position = i;
            tvDate.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedPosition = position;
                    notifyDataSetChanged();
                    if (mListener != null) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(mDates.get(position));
                        mListener.onDateSelected(calendar);
                    }
                }
            });

            addView(view);
        }
    }

    public interface OnDateSelectedListener {
        void onDateSelected(Calendar calendar);
    }

}
