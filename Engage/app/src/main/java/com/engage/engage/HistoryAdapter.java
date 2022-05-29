package com.engage.engage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter  extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{
    List<String> stringList;

    public HistoryAdapter(List<String> stringList) {
        this.stringList = stringList;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_history_item,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(stringList.get(position));

    }



    @Override
    public int getItemCount() {
        if(stringList.size()>9){
            return 9;
        }else{
            return stringList.size();
        }

    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.search_hist);
        }
        void setData(String text){
            textView.setText(text);
        }
    }
}
