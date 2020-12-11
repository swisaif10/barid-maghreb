package com.mobiblanc.gbam.views.account.history;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.gbam.databinding.MyHistoryItemLayoutBinding;
import com.mobiblanc.gbam.listeners.OnItemSelectedListener;
import com.mobiblanc.gbam.models.history.History;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private final List<History> arrayList;
    private final OnItemSelectedListener onItemSelectedListener;

    public HistoryAdapter(List<History> arrayList, OnItemSelectedListener onItemSelectedListener) {
        this.arrayList = arrayList;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryViewHolder(MyHistoryItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.bind(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class HistoryViewHolder extends RecyclerView.ViewHolder {

        private final MyHistoryItemLayoutBinding itemBinding;

        HistoryViewHolder(MyHistoryItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        private void bind(History history) {
            itemBinding.title.setText(history.getId());
            itemBinding.date.setText(history.getDate());
            itemBinding.productsNumber.setText(history.getProductsNumber());
            itemBinding.totalPrice.setText(history.getTotalPrice());
            itemBinding.status.setText(history.getStatus());

            history.setColor(history.getColor().replace("##","#"));
            itemBinding.color.setColorFilter(Color.parseColor(history.getColor()));

            itemBinding.getRoot().setOnClickListener(v -> onItemSelectedListener.onItemSelected(getAdapterPosition(), history));
        }
    }
}