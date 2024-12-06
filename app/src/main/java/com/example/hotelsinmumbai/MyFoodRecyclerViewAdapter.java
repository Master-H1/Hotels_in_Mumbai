package com.example.hotelsinmumbai;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyFoodRecyclerViewAdapter extends RecyclerView.Adapter<MyFoodRecyclerViewAdapter.MyViewHolder>{
//    private ArrayList<Food> foods  = new ArrayList<>();
    private Context context;
    private ArrayList food_id, food_name, food_category, food_price, is_vegetarian;

    public MyFoodRecyclerViewAdapter(Context context, ArrayList food_id, ArrayList food_name, ArrayList food_category, ArrayList food_price, ArrayList is_vegetarian) {
        this.context = context;
        this.food_id = food_id;
        this.food_name = food_name;
        this.food_category = food_category;
        this.food_price = food_price;
        this.is_vegetarian = is_vegetarian;
    }

    @SuppressLint("NotifyDataSetChanged")
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_row,parent,false);
        return new MyViewHolder(view);
    }
//    public void setFood(ArrayList<Food> food){
//        this.foods = food;
//    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        holder.txtFoodId.setText(String.valueOf(foods.get(position).getFoodId()));
//        holder.txtFoodName.setText(foods.get(position).getFoodName());
//        if (foods.get(position).getIsVegetarian()){
//            holder.txtIsVegetarian.setText("Vegetarian");
//        }else {
//            holder.txtIsVegetarian.setText("Not Vegetarian");
//        }
//        holder.txtFoodPrice.setText("$"+foods.get(position).getFoodPrice());s

        holder.txtFoodId.setText(food_id.get(position).toString());
        holder.txtFoodName.setText(food_name.get(position).toString());
        holder.txtFoodCategory.setText(food_category.get(position).toString());
        holder.txtFoodPrice.setText(String.valueOf(food_price.get(position)));

        holder.recycler_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FoodDetailsActivity.class);
                intent.putExtra("id",food_id.get(position).toString());
                intent.putExtra("name",food_name.get(position).toString());
                intent.putExtra("category",food_category.get(position).toString());
                intent.putExtra("price",food_price.get(position).toString());
                intent.putExtra("vegetarian",is_vegetarian.get(position).toString());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
//        return foods.size();
        return food_id.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtFoodId;
        TextView txtFoodName;
        TextView txtFoodCategory;
        TextView txtFoodPrice;

        ConstraintLayout recycler_row;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFoodId = itemView.findViewById(R.id.foodsId);
            txtFoodName = itemView.findViewById(R.id.foodName);
            txtFoodCategory = itemView.findViewById(R.id.foodCategory);
            txtFoodPrice = itemView.findViewById(R.id.foodPrice);

            recycler_row = itemView.findViewById(R.id.recycler_row);
        }
    }
}
