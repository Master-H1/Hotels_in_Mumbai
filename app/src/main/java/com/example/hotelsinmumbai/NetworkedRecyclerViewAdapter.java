package com.example.hotelsinmumbai;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NetworkedRecyclerViewAdapter extends RecyclerView.Adapter<NetworkedRecyclerViewAdapter.MyViewHolder>{
    private ArrayList<Food> foods;

    public NetworkedRecyclerViewAdapter(ArrayList<Food> foods) {
        this.foods = foods;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.networked_recycler_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.food_id_networked_rec_txt.setText(String.valueOf(foods.get(position).getFoodId()));
        holder.food_name_networked_rec_txt.setText(foods.get(position).getFoodName());
        holder.food_category_networked_rec.setText(foods.get(position).getFood_category());
        holder.food_price_networked_rec.setText(String.valueOf(foods.get(position).getFoodPrice()));
        holder.is_vegetarian_networked_rec.setText(String.valueOf(foods.get(position).getIsVegetarian()));
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView food_id_networked_rec_txt, food_name_networked_rec_txt, food_category_networked_rec, food_price_networked_rec, is_vegetarian_networked_rec;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            food_id_networked_rec_txt = itemView.findViewById(R.id.food_id_networked_rec);
            food_name_networked_rec_txt = itemView.findViewById(R.id.food_name_networked_rec);
            food_category_networked_rec = itemView.findViewById(R.id.food_category_networked_rec);
            food_price_networked_rec = itemView.findViewById(R.id.food_price_networked_rec);
            is_vegetarian_networked_rec = itemView.findViewById(R.id.is_vegetarian_networked_rec);
        }
    }
}
