package com.example.mealanner.UILayer.AppMainActivity.FavouriteFragment.view;

import android.content.Context;
import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.lifecycle.LiveData;
        import androidx.recyclerview.widget.RecyclerView;

        import com.bumptech.glide.Glide;
        import com.bumptech.glide.request.RequestOptions;
import com.example.mealanner.DataLayer.Model.DataModels.Meal;
        import com.example.mealanner.DataLayer.Model.DataModels.Meals;
        import com.example.mealanner.DataLayer.Model.Services.Local.LocalDataSourceImpl;
        import com.example.mealanner.DataLayer.Model.Services.Remote.RemoteDataSourceImpl;
        import com.example.mealanner.DataLayer.Model.Services.Remote.RepositoryImpl;
        import com.example.mealanner.R;
import com.example.mealanner.UILayer.AppMainActivity.FavouriteFragment.presenter.FavouritsPresenter;
import com.example.mealanner.UILayer.AppMainActivity.MealsFragment.View.MealsView;

import java.util.List;

public class FavRCAdapter extends RecyclerView.Adapter<FavRCAdapter.FavViewHolder> implements MealsView {
    private Context context;
    private List<Meal> meals;
    private List<Meal> mealsFromLocal;
    FavouritsPresenter mealsPresenter;
    RepositoryImpl repository;
    boolean isSaved;


    public FavRCAdapter(Context context, List<Meal> meals) {
        this.context = context;
        this.meals = meals;
        repository = RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(Void.class), LocalDataSourceImpl.getInstance(context));
        mealsPresenter = new FavouritsPresenter(repository, this);

    }
    public void setList(List<Meal> mealsList){
        this.meals = mealsList;
    }

    @NonNull
    @Override
    public FavRCAdapter.FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.meals_recycler_view_row,parent,false);
        FavRCAdapter.FavViewHolder viewHolderr = new FavViewHolder(view);
        return viewHolderr;
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        String mealName = meals.get(position).getStrMeal().toString().toLowerCase();
        Log.i("image", "onBindViewHolder: " + meals.get(position).getStrMealThumb());
        Glide.with(context).load(meals.get(position).getStrMealThumb()).apply(new RequestOptions().override(200,200).placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground)).into(holder.mealImage);
        if (holder.mealImage != null) {
            holder.mealName.setText(mealName);
        }
        holder.favBtn.setImageResource(android.R.drawable.btn_star_big_on);

        holder.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    holder.favBtn.setImageResource(android.R.drawable.btn_star_big_off);
                    mealsPresenter.deleteFromLocal(meals.get(holder.getAdapterPosition()));
                    Log.i("TAG", "onSuccess: " + meals.get(holder.getAdapterPosition()).getStrMeal());
            }
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }



    @Override
    public void showMealsByCategory(Meals result) {

    }

    @Override
    public void showMealsByCountry(Meals result) {

    }

    @Override
    public void showMealsByIngredients(Meals result) {

    }

    @Override
    public void showSavedMeals(LiveData<List<Meal>> meals) {

    }

    public static class FavViewHolder extends RecyclerView.ViewHolder{
        TextView mealName;
        ImageView mealImage;
        ImageButton favBtn;

        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.mealNameTV);
            mealImage = itemView.findViewById(R.id.mealImageView);
            favBtn = itemView.findViewById(R.id.addmealToFavimageBtn);

        }
    }
}

