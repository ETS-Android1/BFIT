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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ansoft.bfit.DiscoverActivity;
import com.ansoft.bfit.Model.Discover;
import com.ansoft.bfit.Model.Workout;
import com.ansoft.bfit.R;
import com.ansoft.bfit.WorkoutMoreActivity;
import com.daimajia.easing.Glider;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.MyViewHolder> {
    List<Discover> discoverList;
    AppCompatActivity context;


    public DiscoverAdapter(List<Discover> discoverList, AppCompatActivity context) {
        this.discoverList = discoverList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_discover, viewGroup, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int i) {
        final Discover discover=discoverList.get(i);
        Picasso.get().load(discover.getThumbnailLink()).into(viewHolder.imgDisThumb);
        viewHolder.tvDisTitle.setText(discover.getName());
        viewHolder.tvDisDesc.setText(discover.getDescription());
        Log.e("DIfficulty", discover.getDifficulty()+"");
        switch (discover.getDifficulty()){
            case 1:
                viewHolder.difStar1.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
                viewHolder.difStar2.setColorFilter(ContextCompat.getColor(context, R.color.mildgrey), android.graphics.PorterDuff.Mode.MULTIPLY);
                viewHolder.difStar3.setColorFilter(ContextCompat.getColor(context, R.color.mildgrey), android.graphics.PorterDuff.Mode.MULTIPLY);
                break;

            case 2:
                viewHolder.difStar1.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
                viewHolder.difStar2.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
                viewHolder.difStar3.setColorFilter(ContextCompat.getColor(context, R.color.mildgrey), android.graphics.PorterDuff.Mode.MULTIPLY);
                break;



            case 3:
                viewHolder.difStar1.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
                viewHolder.difStar2.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
                viewHolder.difStar3.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
                break;


            default:
                viewHolder.difStar1.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
                viewHolder.difStar2.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
                viewHolder.difStar3.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
                break;

        }

        PushDownAnim.setPushDownAnimTo(viewHolder.outerView);

        viewHolder.outerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, DiscoverActivity.class);
                intent.putExtra("id", discover.getId());
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return discoverList.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView imgDisThumb;

        ImageView difStar1, difStar2, difStar3;

        TextView tvDisTitle, tvDisDesc;

        RelativeLayout outerView;

        public MyViewHolder(View itemView) {
            super(itemView);

            imgDisThumb=itemView.findViewById(R.id.imgDisThumb);
            difStar1=itemView.findViewById(R.id.difStar1);
            difStar2=itemView.findViewById(R.id.difStar2);
            difStar3=itemView.findViewById(R.id.difStar3);
            tvDisTitle=itemView.findViewById(R.id.tvDisTitle);
            tvDisDesc=itemView.findViewById(R.id.tvDisDesc);
            outerView=itemView.findViewById(R.id.outerView);
        }
    }
}
