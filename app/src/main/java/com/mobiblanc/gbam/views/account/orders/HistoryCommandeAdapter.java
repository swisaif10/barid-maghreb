package com.mobiblanc.gbam.views.account.orders;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.gbam.databinding.HistoryCommandeItemBinding;
import com.mobiblanc.gbam.listeners.OnItemSelectedListener;
import com.mobiblanc.gbam.models.orders.details.OrderProduct;
import com.mobiblanc.gbam.models.products.Product;

import java.util.List;

public class HistoryCommandeAdapter extends RecyclerView.Adapter<HistoryCommandeAdapter.HistoryViewHolder> {

    private final List<OrderProduct> arrayList;
    public HistoryCommandeAdapter(List<OrderProduct> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public HistoryCommandeAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryCommandeAdapter.HistoryViewHolder(HistoryCommandeItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryCommandeAdapter.HistoryViewHolder holder, int position) {
        holder.bind(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class HistoryViewHolder extends RecyclerView.ViewHolder {

        private final HistoryCommandeItemBinding itemBinding;

        HistoryViewHolder(HistoryCommandeItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        private void bind(OrderProduct orderProduct) {
            itemBinding.nameHistory.setText(orderProduct.getName());
            itemBinding.price.setText(String.format("%s MAD", orderProduct.getTotalPrice()));
            itemBinding.quantity.setText(String.format("Quantité: %s", orderProduct.getQuantity()));
            //itemBinding.getRoot().setOnClickListener(v -> onItemSelectedListener.onItemSelected(getAdapterPosition(), orderProduct));
        }
    }
}
