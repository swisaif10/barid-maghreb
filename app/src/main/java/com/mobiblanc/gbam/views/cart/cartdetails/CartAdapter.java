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
        holder.bind(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void removeItem (int position){
        arrayList.remove(position);
        notifyItemRemoved(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CartItemLayoutBinding itemBinding;

        ViewHolder(CartItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        private void bind(Item item) {
            Glide.with(context).load(item.getImage()).into(itemBinding.image);
            itemBinding.title.setText(item.getName());
            itemBinding.subtitle.setText(item.getShortDescription());
            itemBinding.quantity.setText(item.getQty());
            itemBinding.price.setText(String.valueOf(item.getPrice()));

            itemBinding.decreaseBtn.setOnClickListener(v -> {
                int qty = Integer.parseInt(itemBinding.quantity.getText().toString());
                if (qty > 1) {
                    qty--;
                    itemBinding.quantity.setText(String.valueOf(qty));
                    onItemQuantityChangedListener.onItemQuantityChanged(getAdapterPosition(), qty);
                }
            });

            itemBinding.increaseBtn.setOnClickListener(v -> {
                int qty = Integer.parseInt(itemBinding.quantity.getText().toString());
                if (qty < 9) {
                    qty++;
                    itemBinding.quantity.setText(String.valueOf(qty));
                    onItemQuantityChangedListener.onItemQuantityChanged(getAdapterPosition(), qty);
                }
            });
        }
    }
}