package com.example.mealanner.UILayer.AppMainActivity.SearchFragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealanner.DataLayer.Model.DataModels.Category;
import com.example.mealanner.DataLayer.Model.DataModels.Ingrediant;

import com.example.mealanner.DataLayer.Model.DataModels.Meal;
import com.example.mealanner.DataLayer.Model.DataModels.Meals;
import com.example.mealanner.DataLayer.Model.Services.Local.LocalDataSourceImpl;
import com.example.mealanner.DataLayer.Model.Services.Remote.RemoteDataSourceImpl;
import com.example.mealanner.DataLayer.Model.Services.Remote.RepositoryImpl;
import com.example.mealanner.R;
import com.example.mealanner.UILayer.AppMainActivity.HomeFragment.View.CategoriesRCAdapter;
import com.example.mealanner.UILayer.AppMainActivity.MealsFragment.Presenter.MealsPresenter;
import com.example.mealanner.UILayer.AppMainActivity.MealsFragment.View.MealsView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class IngredientsRCAdapter extends RecyclerView.Adapter<IngredientsRCAdapter.MealsViewHolder> {
    private Context context;
    private List<Ingrediant> ingrediants;
    private List<Ingrediant> mealsFromLocal;
    MealsPresenter mealsPresenter;
    RepositoryImpl repository;
    private OnIngItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnIngItemClickListener listener) {
        Log.i("TAG", "setOnItemClicvvvvvvvvvvvkListener: ");
        this.onItemClickListener = listener;
    }
    public interface OnIngItemClickListener {

        void onItemClick(Ingrediant ingrediant);
    }

    public IngredientsRCAdapter(Context context, List<Ingrediant> meals) {
        this.context = context;
        this.ingrediants = meals;
    }
    public void setList(List<Ingrediant> mealsList){
        this.ingrediants = mealsList;
    }

    @NonNull
    @Override
    public IngredientsRCAdapter.MealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.meals_recycler_view_row,parent,false);
        IngredientsRCAdapter.MealsViewHolder viewHolderr = new IngredientsRCAdapter.MealsViewHolder(view);
        return viewHolderr;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsRCAdapter.MealsViewHolder holder, int position) {
        String mealName = ingrediants.get(position).getStrIngredient().toString().toLowerCase();
        Log.i("image", "onBindViewHolder: " + ingrediants.get(position).getStrIngredient());
        Glide.with(context).load("https://www.themealdb.com/images/ingredients/"+mealName+"-small.png").apply(new RequestOptions().override(200,200).placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground)).into(holder.mealImage);
        if (holder.mealImage != null) {
            holder.mealName.setText(mealName);
        }
            holder.favBtn.setVisibility(View.INVISIBLE);

        /*if(mealsFromLocal.size() != 0) {
            for (int i = 0; i < mealsFromLocal.size(); i++) {
                Log.i("TAG", "checking DataBase ");
                if (meals.get(position).getIdMeal() == mealsFromLocal.get(i).getIdMeal()) {
                    isSaved = true;
                    holder.favBtn.setImageResource(android.R.drawable.btn_star_big_on);
                }
            }
        }*/

    }

    @Override
    public int getItemCount() {
        return ingrediants.size();
    }






    public class MealsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mealName;
        ImageView mealImage;

        ImageButton favBtn;

        public MealsViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.mealNameTV);
            mealImage = itemView.findViewById(R.id.mealImageView);
            favBtn = itemView.findViewById(R.id.addmealToFavimageBtn);

        }



        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(ingrediants.get(getAdapterPosition()));
            }
        }
    }
}

