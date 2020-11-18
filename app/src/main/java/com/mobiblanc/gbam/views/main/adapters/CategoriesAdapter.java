package com.mobiblanc.gbam.views.main.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mobiblanc.gbam.databinding.CategoryItemLayoutBinding;
import com.mobiblanc.gbam.listeners.OnDashboardItemSelectedListener;
import com.mobiblanc.gbam.models.dashboard.Category;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private final List<Category> arrayList;
    private final OnDashboardItemSelectedListener onDashboardItemSelectedListener;

    public CategoriesAdapter(List<Category> arrayList, OnDashboardItemSelectedListener onDashboardItemSelectedListener) {
        this.arrayList = arrayList;
        this.onDashboardItemSelectedListener = onDashboardItemSelectedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(CategoryItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemBinding.title.setText(arrayList.get(position).getName());

        holder.itemBinding.getRoot().setOnClickListener(v -> onDashboardItemSelectedListener.onCategoryItemSelected(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CategoryItemLayoutBinding itemBinding;

        ViewHolder(CategoryItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

    }
}