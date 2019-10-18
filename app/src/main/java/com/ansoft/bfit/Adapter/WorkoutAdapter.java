package com.ansoft.bfit.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.ansoft.bfit.Model.Workout;
import com.ansoft.bfit.R;
import com.ansoft.bfit.WorkoutMoreActivity;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.MyViewHolder> {
    List<Workout> workoutList;
    AppCompatActivity context;

    int day,  level;

    public WorkoutAdapter(List<Workout> workoutList, AppCompatActivity context, int day, int level) {
        this.workoutList = workoutList;
        this.context = context;
        this.day = day;
        this.level = level;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_workout, viewGroup, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int i) {
        Workout data= workoutList.get(i);
        viewHolder.workoutTitle.setText(data.getTitle());
        viewHolder.workoutRep.setText(data.getRep());
        viewHolder.workoutImg.setImageResource(data.getDrawable());
        viewHolder.outerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, WorkoutMoreActivity.class);
                intent.putExtra(context.getString(R.string.index), i);
                intent.putExtra(context.getString(R.string.day), day);
                intent.putExtra(context.getString(R.string.level), level);
                context.startActivity(intent);
            }
        });
        PushDownAnim.setPushDownAnimTo(viewHolder.outerView);

    }
    @Override
    public int getItemCount() {
        return workoutList.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView workoutTitle, workoutRep;
        ImageView workoutImg;
        RelativeLayout outerView;
        public MyViewHolder(View itemView) {
            super(itemView);
            workoutImg=itemView.findViewById(R.id.workoutImg);
            workoutTitle=itemView.findViewById(R.id.workoutTitle);
            workoutRep=itemView.findViewById(R.id.workOutRep);
            outerView=itemView.findViewById(R.id.outerView);
        }
    }
}
