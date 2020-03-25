package com.task.coronavirusupdate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.task.coronavirusupdate.R;
import com.task.coronavirusupdate.models.CountryDetail;

import java.util.List;

public class CustomRVAdapter extends RecyclerView.Adapter {
    Context context;
    List<CountryDetail> list;

    public CustomRVAdapter(Context context, List<CountryDetail> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.rv_layout,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder=(MyViewHolder) holder;
        myViewHolder.country.setText(list.get(position).getCountry());
        myViewHolder.others.setText(list.get(position).getOthers());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView country,others;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            country=itemView.findViewById(R.id.country);
            others=itemView.findViewById(R.id.others);
        }
    }
}
