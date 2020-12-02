package com.mobiblanc.gbam.views.tracking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.gbam.databinding.TrackingItemLayoutBinding;
import com.mobiblanc.gbam.models.tracking.TrackingItem;

import java.util.List;

public class TrackingAdapter extends RecyclerView.Adapter<TrackingAdapter.ViewHolder> {

    private final List<TrackingItem> arrayList;

    public TrackingAdapter(List<TrackingItem> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(TrackingItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TrackingItemLayoutBinding itemBinding;

        ViewHolder(TrackingItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        private void bind(TrackingItem trackingItem) {
            if (getAdapterPosition() == 0)
                itemBinding.top.setVisibility(View.GONE);
            else if (getAdapterPosition() == arrayList.size() - 1)
                itemBinding.bottom.setVisibility(View.GONE);

            String date = trackingItem.getDate().replace(" ", "\n");
            itemBinding.date.setText(date);
            itemBinding.status.setText(trackingItem.getStatus());
            itemBinding.agency.setText(trackingItem.getLocation());
        }
    }
}