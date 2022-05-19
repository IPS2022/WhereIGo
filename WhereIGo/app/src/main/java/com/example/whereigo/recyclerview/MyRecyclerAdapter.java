package com.example.whereigo.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whereigo.R;

import java.util.ArrayList;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private ArrayList<Data> data;

    @NonNull
    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(data.get(position));
    }

    public void setData(ArrayList<Data> list){
        this.data = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView disease;
        TextView doctor_part;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            disease = (TextView) itemView.findViewById(R.id.disease);
            doctor_part = (TextView) itemView.findViewById(R.id.doctor_part);
        }

        void onBind(Data item){
            disease.setText(item.getDisease());
            doctor_part.setText(item.getDoctorpart());
        }
    }
}

