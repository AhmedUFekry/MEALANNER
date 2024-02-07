package com.example.mealanner.UILayer.AppMainActivity.HomeFragment.View;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mealanner.DataLayer.Model.DataModels.Category;
import com.example.mealanner.DataLayer.Model.DataModels.Country;
import com.example.mealanner.R;

import java.util.List;

public class CategoriesRCAdapter extends RecyclerView.Adapter<CategoriesRCAdapter.CategoryViewHolder>{
    private Context context;
    private List<Category> categoriesy;

    public CategoriesRCAdapter(Context context, List<Category> categoriesy) {
        this.context = context;
        this.categoriesy = categoriesy;
    }

    @NonNull
    @Override
    public CategoriesRCAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.categories_recycler_view_row,parent,false);
        CategoriesRCAdapter.CategoryViewHolder viewHolderr = new CategoriesRCAdapter.CategoryViewHolder(view);
        return viewHolderr;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesRCAdapter.CategoryViewHolder holder, int position) {
        String catName = categoriesy.get(position).getStrCategory().toString().toLowerCase();
         Log.i("image", "onBindViewHolder: " + categoriesy.get(position).getStrCategoryThumb());
        Glide.with(context).load(categoriesy.get(position).getStrCategoryThumb()).apply(new RequestOptions().override(200,200).placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground)).into(holder.caregoryImage);
        if (holder.caregoryImage != null) {
            holder.categoryName.setText(catName);
        }
    }

    @Override
    public int getItemCount() {
        return categoriesy.size();

    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{
        TextView categoryName;
        ImageView caregoryImage;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryNameTV);
            caregoryImage = itemView.findViewById(R.id.categoryImageView);
        }
    }
}
