package com.example.mealanner.UILayer.AppMainActivity.HomeFragment.View;

import android.content.Context;
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
import com.example.mealanner.R;

import java.util.List;

public class CategoriesRCAdapter extends RecyclerView.Adapter<CategoriesRCAdapter.CategoryViewHolder> {

    private Context context;
    private List<Category> categoriesy;
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Category category);
    }

    public CategoriesRCAdapter(Context context, List<Category> categoriesy) {
        this.context = context;
        this.categoriesy = categoriesy;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.categories_recycler_view_row, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoriesy.get(position);

        // Null check for Glide load
        if (category.getStrCategoryThumb() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(category.getStrCategoryThumb())
                    .apply(new RequestOptions().override(200, 200)
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .error(R.drawable.ic_launcher_foreground))
                    .into(holder.caregoryImage);
        }

        // Set category name
        holder.categoryName.setText(category.getStrCategory());
    }

    @Override
    public int getItemCount() {
        return categoriesy.size();
    }



    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView categoryName;
        ImageView caregoryImage;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryNameTV);
            caregoryImage = itemView.findViewById(R.id.categoryImageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(categoriesy.get(getAdapterPosition()));
            }
        }
    }
}
