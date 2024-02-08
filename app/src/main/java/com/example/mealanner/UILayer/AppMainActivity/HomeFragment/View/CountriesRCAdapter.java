package com.example.mealanner.UILayer.AppMainActivity.HomeFragment.View;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealanner.DataLayer.Model.DataModels.Country;
import com.example.mealanner.R;

import java.util.List;

public class CountriesRCAdapter extends RecyclerView.Adapter<CountriesRCAdapter.CountryViewHolder> {
    private Context context;
    private static List<Country> countries;
    private OnContryClickListener onItemClickListener;

    public void setOnItemClickListener(OnContryClickListener listener) {
        this.onItemClickListener = listener;
    }
    public interface OnContryClickListener {
        void onItemClick(Country country);
    }
    public CountriesRCAdapter(Context context, List<Country> countries) {
        this.context = context;
        this.countries = countries;
    }

    @NonNull
    @Override
    public CountriesRCAdapter.CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.countries_recycler_view_row,parent,false);
        CountryViewHolder viewHolderr = new CountryViewHolder(view);
        return viewHolderr;
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesRCAdapter.CountryViewHolder holder, int position) {
        String imageName = countries.get(position).getStrArea().toString().toLowerCase();
        int resourceId = holder.itemView.getContext().getResources().getIdentifier(imageName, "drawable", holder.itemView.getContext().getPackageName());
        holder.countryImage.setImageResource(resourceId);
        if (holder.countryName != null) {
            holder.countryName.setText(imageName);
        }
    }

    @Override
    public int getItemCount() {
        return countries.size();

    }

    public class CountryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
         TextView countryName;
         ImageView countryImage;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.countryNameTV);
            countryImage = itemView.findViewById(R.id.countryImageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(countries.get(getAdapterPosition()));
            }
        }
    }
}
