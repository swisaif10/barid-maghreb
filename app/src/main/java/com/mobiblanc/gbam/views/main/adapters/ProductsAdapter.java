package com.mobiblanc.gbam.views.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.mobiblanc.gbam.databinding.ProductItemLayoutBinding;
import com.mobiblanc.gbam.listeners.OnItemQuantityChangedListener;
import com.mobiblanc.gbam.listeners.OnItemSelectedListener;
import com.mobiblanc.gbam.models.products.Product;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private final Context context;
    private final List<Product> arrayList;
    private final OnItemSelectedListener onItemSelectedListener;
    private final OnItemQuantityChangedListener onItemQuantityChangedListener;

    public ProductsAdapter(Context context, List<Product> arrayList, OnItemSelectedListener onItemSelectedListener, OnItemQuantityChangedListener onItemQuantityChangedListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.onItemSelectedListener = onItemSelectedListener;
        this.onItemQuantityChangedListener = onItemQuantityChangedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ProductItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        Glide.with(context).load(arrayList.get(position).getImage()).placeholder(circularProgressDrawable).into(holder.itemBinding.image);
        Glide.with(context).load(arrayList.get(position).getImage()).into(holder.itemBinding.copy);
        holder.itemBinding.title.setText(arrayList.get(position).getName());
        holder.itemBinding.price.setText(arrayList.get(position).getPrice());

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

        holder.itemBinding.addBtn.setOnClickListener(view -> onItemSelectedListener.onItemSelected(position, holder.itemBinding.copy));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ProductItemLayoutBinding itemBinding;

        ViewHolder(ProductItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }
}