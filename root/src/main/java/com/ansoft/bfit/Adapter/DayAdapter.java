package com.ansoft.bfit.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ansoft.bfit.DayActivity;
import com.ansoft.bfit.Model.Day;
import com.ansoft.bfit.R;
import com.ansoft.bfit.Util.SquareRelativeLayout;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.MyViewHolder> {
    List<Day> dayList;
    Context context;
    int current;
    int level;

    public DayAdapter(List<Day> dayList, Context context, int current, int level) {
        this.dayList = dayList;
        this.context = context;
        this.current = current;
        this.level = level;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_day, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int i) {
        final Day data = dayList.get(i);
        viewHolder.dayName.setText(data.getDayNum() + "");

        if (data.getProgress() < 100) {
            viewHolder.outerView.setBackgroundResource(R.drawable.bg_day_incomplete);
        } else {
            viewHolder.outerView.setBackgroundResource(R.drawable.bg_day_complete);
            viewHolder.dayName.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        }
        if (i == current) {
            viewHolder.outerView.setBackgroundResource(R.drawable.bg_day_current);
            viewHolder.dayName.setTextColor(ContextCompat.getColor(context, android.R.color.black));
        }
        viewHolder.outerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DayActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(context.getString(R.string.level), level);
                intent.putExtra(context.getString(R.string.day), data.getDayNum());
                if (i <= current) {
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Please complete previous day's activity first", Toast.LENGTH_SHORT).show();
                }
            }
        });
        PushDownAnim.setPushDownAnimTo(viewHolder.outerView);

    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dayName;
        SquareRelativeLayout outerView;

        public MyViewHolder(View itemView) {
            super(itemView);
            dayName = itemView.findViewById(R.id.dayName);
            outerView = itemView.findViewById(R.id.outerView);
        }

    }
}
