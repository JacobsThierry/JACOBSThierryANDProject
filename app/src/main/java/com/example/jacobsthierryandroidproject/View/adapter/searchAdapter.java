package com.example.jacobsthierryandroidproject.View.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jacobsthierryandroidproject.Pojo.foodObjects.Recipe;
import com.example.jacobsthierryandroidproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class searchAdapter extends RecyclerView.Adapter<searchAdapter.ViewHolder>{


    ArrayList<Recipe> food;
    OnListItemClickListener listener;
    private ArrayList<Recipe> FoodListFull;

    public ArrayList<Recipe> getFood() {
        return food;
    }

    public void setFood(ArrayList<Recipe> food) {
        this.food = food;
    }

    public OnListItemClickListener getListener() {
        return listener;
    }

    public void setListener(OnListItemClickListener listener) {
        this.listener = listener;
    }

    public ArrayList<Recipe> getFoodListFull() {
        return FoodListFull;
    }

    public void setFoodListFull(ArrayList<Recipe> foodListFull) {
        FoodListFull = foodListFull;
    }



    public searchAdapter(List<Recipe> food, OnListItemClickListener listener) {
        Log.d("listener ", listener.toString());
        if(food == null){
            this.food = new ArrayList<Recipe>();
            FoodListFull = new ArrayList<Recipe>();
        }else{
        this.food = new ArrayList<>(food);
        FoodListFull = new ArrayList<>(food);
        }
        this.listener = listener;


    }

    @NonNull
    @Override
    public searchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.food_item, parent, false);

        return new searchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("food", "bind view holder");
        Log.d("food url", food.get(position).getImage());
        holder.name.setText(food.get(position).getTitle());
        Picasso.get().load(food.get(position).getImage()).into(holder.image);
    }




    @Override
    public int getItemCount() {
        return food.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(food != null) listener.onClick(food.get(getAdapterPosition()));
                }
            });
            image = itemView.findViewById(R.id.item_image);
            name = itemView.findViewById(R.id.item_name);
        }


    }



}
