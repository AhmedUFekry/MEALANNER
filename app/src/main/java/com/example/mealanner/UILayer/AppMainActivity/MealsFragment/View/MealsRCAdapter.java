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
import com.example.mealanner.DataLayer.Model.DataModels.Meals;
import com.example.mealanner.DataLayer.Model.Services.Local.LocalDataSourceImpl;
import com.example.mealanner.DataLayer.Model.Services.Remote.RemoteDataSourceImpl;
import com.example.mealanner.DataLayer.Model.Services.Remote.RepositoryImpl;
import com.example.mealanner.R;
import com.example.mealanner.UILayer.AppMainActivity.HomeFragment.Presenter.HomePresenter;
import com.example.mealanner.UILayer.AppMainActivity.MealsFragment.Presenter.MealsPresenter;

import java.util.ArrayList;
import java.util.List;

public class MealsRCAdapter extends RecyclerView.Adapter<MealsRCAdapter.MealsViewHolder> implements MealsView{
    private Context context;
    private List<Meal> meals;
    private List<Meal> mealsFromLocal;
    MealsPresenter mealsPresenter;
    RepositoryImpl repository;
    boolean isSaved;

    public MealsRCAdapter(Context context, List<Meal> meals) {
        this.context = context;
        this.meals = meals;
         repository = RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(Void.class), LocalDataSourceImpl.getInstance(context));
         mealsPresenter = new MealsPresenter(repository, this);
        //mealsFragment.mealsPresenter.getFromLocal();
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
        isSaved = false;
        String mealName = meals.get(position).getStrMeal().toString().toLowerCase();
         Log.i("image", "onBindViewHolder: " + meals.get(position).getStrMealThumb());
        Glide.with(context).load(meals.get(position).getStrMealThumb()).apply(new RequestOptions().override(200,200).placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground)).into(holder.mealImage);
        if (holder.mealImage != null) {
            holder.mealName.setText(mealName);
        }
        if(mealsFromLocal.size() != 0) {
            for (int i = 0; i < mealsFromLocal.size(); i++) {
                Log.i("TAG", "checking DataBase ");
                if (meals.get(position).getIdMeal() == mealsFromLocal.get(i).getIdMeal()) {
                    isSaved = true;
                    holder.favBtn.setImageResource(android.R.drawable.btn_star_big_on);
                }
            }
        }
        holder.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSaved == false) {
                    mealsPresenter.saveToLocal(meals.get(holder.getAdapterPosition()));
                    holder.favBtn.setImageResource(android.R.drawable.btn_star_big_on);
                    Log.i("TAG", "onSuccess Saved TO Local: " + meals.get(holder.getAdapterPosition()).getStrMeal());
                    isSaved = true;
                }else {
                    isSaved = false;
                    holder.favBtn.setImageResource(android.R.drawable.btn_star_big_off);
                    mealsPresenter.deleteFromLocal(meals.get(holder.getAdapterPosition()));
                    Log.i("TAG", "onSuccess: " + meals.get(holder.getAdapterPosition()).getStrMeal());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void getMealsFromLocal(List<Meal> localMeals) {
        mealsFromLocal = new ArrayList<>();
       // Log.i("TAG", "Local Meals Size " + localMeals.size());
        if(localMeals != null)
        mealsFromLocal.addAll(localMeals);

    }

    @Override
    public void showMealsByCategory(Meals result) {

    }

    @Override
    public void showMealsByCountry(Meals result) {

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
