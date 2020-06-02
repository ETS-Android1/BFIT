package com.ansoft.bfit.Adapter;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ansoft.bfit.DiscoverActivity;
import com.ansoft.bfit.Model.ExpertModel;
import com.ansoft.bfit.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

public class ExpertAdapter extends RecyclerView.Adapter<ExpertAdapter.MyViewHolder> {
    List<ExpertModel> expertModelList;
    AppCompatActivity context;

    public ExpertAdapter(List<ExpertModel> expertModelList, AppCompatActivity context) {
        this.expertModelList = expertModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_expert, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int i) {
        final ExpertModel expert = expertModelList.get(i);
        Picasso.get().load(expert.getLink()).into(viewHolder.imgInstagramProf);
        viewHolder.tvExpertTitle.setText(expert.getName());
        viewHolder.linkInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = expert.getInstagram();
                Uri uri = Uri.parse("http://instagram.com/_u/" + username);
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    context.startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/" + username)));
                }
            }
        });
        viewHolder.linkWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(expert.getWebsite()));
                context.startActivity(browserIntent);
            }
        });
        viewHolder.linkPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + expert.getPhone()));
                context.startActivity(intent);
            }
        });
        if(expert.getWebsite().equalsIgnoreCase("null")){
            viewHolder.linkWeb.setVisibility(View.GONE);
        }
        if(expert.getPhone().equalsIgnoreCase("null")){
            viewHolder.linkPhone.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return expertModelList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView imgInstagramProf;

        TextView tvExpertTitle;

        AppCompatImageView linkInstagram, linkWeb, linkPhone;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgInstagramProf = itemView.findViewById(R.id.imgInstagramProf);
            tvExpertTitle = itemView.findViewById(R.id.tvExpertTitle);
            linkInstagram = itemView.findViewById(R.id.linkInstagram);
            linkWeb = itemView.findViewById(R.id.linkWeb);
            linkPhone = itemView.findViewById(R.id.linkPhone);

        }
    }
}
