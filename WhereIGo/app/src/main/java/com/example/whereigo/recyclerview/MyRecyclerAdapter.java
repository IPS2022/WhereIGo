package com.example.whereigo.recyclerview;

import static android.content.ContentValues.TAG;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whereigo.Detail2Activity;
import com.example.whereigo.FragmentSearch;
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

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView disease;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            disease = (TextView) itemView.findViewById(R.id.disease);

            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos=getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Intent intent=new Intent(view.getContext(), Detail2Activity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("detail_disease",disease.getText().toString());
                        Log.d(TAG, "Value ispleasefsfaf " +disease.getText().toString());
                        view.getContext().startActivity(intent);
                    }
                }
            });


        }

        void onBind(Data item){
            disease.setText(item.getDisease());
        }

    }
}

