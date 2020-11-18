package com.mobiblanc.gbam.views.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.ProductItemLayoutBinding;
import com.mobiblanc.gbam.databinding.ServicesItemLayoutBinding;
import com.mobiblanc.gbam.listeners.OnDashboardItemSelectedListener;
import com.mobiblanc.gbam.models.dashboard.Service;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

    private final Context context;
    private final List<Service> arrayList;
    private final OnDashboardItemSelectedListener onDashboardItemSelectedListener;

    public ServicesAdapter(Context context, List<Service> arrayList, OnDashboardItemSelectedListener onDashboardItemSelectedListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.onDashboardItemSelectedListener = onDashboardItemSelectedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ServicesItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(arrayList.get(position).getIcon()).fitCenter().into(holder.itemBinding.image);
        holder.itemBinding.title.setText(arrayList.get(position).getTitle());

        holder.itemBinding.getRoot().setOnClickListener(v -> onDashboardItemSelectedListener.onServiceItemSelected(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ServicesItemLayoutBinding itemBinding;

        ViewHolder(ServicesItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }
}