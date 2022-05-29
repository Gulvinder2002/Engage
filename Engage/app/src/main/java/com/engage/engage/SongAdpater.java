package com.engage.engage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SongAdpater extends RecyclerView.Adapter<SongAdpater.viewHolder>{
    List<SongModel> songModelList;
    private final RecyclerViewInterface recyclerViewInterface;

    public SongAdpater(List<SongModel> songModelList, RecyclerViewInterface recyclerViewInterface) {
        this.songModelList = songModelList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_song_item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.setData(songModelList.get(position).image,songModelList.get(position).name,songModelList.get(position).link);

    }

    @Override
    public int getItemCount() {
        return songModelList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
         ConstraintLayout constraintLayout;
        ImageView imageView;
        TextView textView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout=itemView.findViewById(R.id.favSongMainLayout);
            imageView=itemView.findViewById(R.id.favSongImg);
            textView=itemView.findViewById(R.id.favSongName);




        }
        void setData(String imgLink,String name,String link){
            Glide.with(itemView.getContext()).load(imgLink).into(imageView);
            textView.setText(name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(position,name,link);

                        }
                    }

                }
            });

        }
    }



}
