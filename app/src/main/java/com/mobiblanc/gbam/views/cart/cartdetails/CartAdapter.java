package com.mobiblanc.gbam.views.cart.cartdetails;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobiblanc.gbam.databinding.CartItemLayoutBinding;
import com.mobiblanc.gbam.listeners.OnItemQuantityChangedListener;
import com.mobiblanc.gbam.models.common.Item;

import java.text.DecimalFormat;
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

    public void removeItem(int position) {
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
            itemBinding.subtitle.setText(Html.fromHtml(item.getShortDescription()));
            itemBinding.quantity.setText(String.valueOf(item.getQty()));
            DecimalFormat df = new DecimalFormat("0.00");
            df.setMaximumFractionDigits(2);
            //itemBinding.price.setText(df.format(item.getPrice()));
            int[] qty = {Integer.parseInt(itemBinding.quantity.getText().toString())};
            itemBinding.decreaseBtn.setOnClickListener(v -> {

                if (qty[0] > item.getStep()) {
                    qty[0] -= item.getStep();
                    itemBinding.quantity.setText(String.valueOf(qty[0]));
                    onItemQuantityChangedListener.onItemQuantityChanged(getAdapterPosition(), qty[0]);

                }
            });

            itemBinding.increaseBtn.setOnClickListener(v -> {
                //int qty = Integer.parseInt(itemBinding.quantity.getText().toString());
                if (qty[0] < Integer.MAX_VALUE) {
                    qty[0] += item.getStep();
                    itemBinding.quantity.setText(String.valueOf(qty[0]));
                    onItemQuantityChangedListener.onItemQuantityChanged(getAdapterPosition(), qty[0]);
                    //itemBinding.price.setText(df.format(item.getPrice()*qty[0]));
                }
            });

            itemBinding.price.setText(df.format(item.getPrice() * qty[0]));

        }
    }
}