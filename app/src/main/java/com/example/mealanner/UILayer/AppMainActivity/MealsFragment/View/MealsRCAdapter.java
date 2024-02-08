package com.example.mealanner.UILayer.AppMainActivity.MealsFragment.View;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealanner.DataLayer.Model.DataModels.Category;
import com.example.mealanner.DataLayer.Model.DataModels.Meal;
import com.example.mealanner.R;

import java.util.List;

public class MealsRCAdapter extends RecyclerView.Adapter<MealsRCAdapter.MealsViewHolder>{
    private Context context;
    private List<Meal> meals;

    public MealsRCAdapter(Context context, List<Meal> meals) {
        this.context = context;
        this.meals = meals;
    }

    @NonNull
    @Override
    public MealsRCAdapter.MealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.meals_recycler_view_row,parent,false);
        MealsRCAdapter.MealsViewHolder viewHolderr = new MealsRCAdapter.MealsViewHolder(view);
        return viewHolderr;
    }

    @Override
    public void onBindViewHolder(@NonNull MealsRCAdapter.MealsViewHolder holder, int position) {
        String mealName = meals.get(position).getStrMeal().toString().toLowerCase();
         Log.i("image", "onBindViewHolder: " + meals.get(position).getStrMealThumb());
        Glide.with(context).load(meals.get(position).getStrMealThumb()).apply(new RequestOptions().override(200,200).placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground)).into(holder.mealImage);
        if (holder.mealImage != null) {
            holder.mealName.setText(mealName);
        }
    }

    @Override
    public int getItemCount() {
        return meals.size();

    }

    public static class MealsViewHolder extends RecyclerView.ViewHolder{
        TextView mealName;
        ImageView mealImage;

        ImageButton favBtn;

        public MealsViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.mealNameTV);
            mealImage = itemView.findViewById(R.id.mealImageView);
            favBtn = itemView.findViewById(R.id.addmealToFavimageBtn);
        }
    }
}
