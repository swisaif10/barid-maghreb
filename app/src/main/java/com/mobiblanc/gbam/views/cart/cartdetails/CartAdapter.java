package com.mobiblanc.gbam.views.cart.cartdetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.mobiblanc.gbam.databinding.CartItemLayoutBinding;
import com.mobiblanc.gbam.listeners.OnItemQuantityChangedListener;
import com.mobiblanc.gbam.models.common.Item;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final Context context;
    private final List<Item> arrayList;
    private final OnItemQuantityChangedListener onItemQuantityChangedListener;

    public CartAdapter(Context context, List<Item> arrayList, OnItemQuantityChangedListener onItemQuantityChangedListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.onItemQuantityChangedListener = onItemQuantityChangedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(CartItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(arrayList.get(position).getImage()).into(holder.itemBinding.image);
        holder.itemBinding.title.setText(arrayList.get(position).getName());
        holder.itemBinding.subtitle.setText(arrayList.get(position).getShortDescription());
        holder.itemBinding.quantity.setText(arrayList.get(position).getQty());
        holder.itemBinding.price.setText(String.valueOf(arrayList.get(position).getPrice()));

        holder.itemBinding.decreaseBtn.setOnClickListener(v -> {
            int qty = Integer.parseInt(holder.itemBinding.quantity.getText().toString());
            if (qty > 1) {
                qty--;
                holder.itemBinding.quantity.setText(String.valueOf(qty));
                onItemQuantityChangedListener.onItemQuantityChanged(position, qty);
            }
        });

        holder.itemBinding.increaseBtn.setOnClickListener(v -> {
            int qty = Integer.parseInt(holder.itemBinding.quantity.getText().toString());
            if (qty < 9) {
                qty++;
                holder.itemBinding.quantity.setText(String.valueOf(qty));
                onItemQuantityChangedListener.onItemQuantityChanged(position, qty);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CartItemLayoutBinding itemBinding;

        ViewHolder(CartItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }
}