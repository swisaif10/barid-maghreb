package com.mobiblanc.gbam.views.cart.payment;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobiblanc.gbam.databinding.ConfirmationItemLayoutBinding;
import com.mobiblanc.gbam.models.common.Item;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.List;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.ItemViewHolder> {

    private final Context context;
    private final List<Item> items;

    public CartItemsAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartItemsAdapter.ItemViewHolder(ConfirmationItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(items.get(position));
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private final ConfirmationItemLayoutBinding itemBinding;

        ItemViewHolder(ConfirmationItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        private void bind(Item item) {
            Glide.with(context).load(item.getImage()).into(itemBinding.image);
            itemBinding.title.setText(item.getName());
            itemBinding.subtitle.setText(Html.fromHtml(item.getShortDescription()));
            itemBinding.quantity.setText(item.getQty());
            DecimalFormat df = new DecimalFormat("0.00");
            df.setMaximumFractionDigits(2);
            itemBinding.price.setText(MessageFormat.format("PU = {0}", df.format(item.getPrice())));

            if (getAdapterPosition() == items.size() - 1)
                itemBinding.separator.setVisibility(View.GONE);
        }
    }
}