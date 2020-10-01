package com.mobiblanc.baridal_maghrib.views.main.products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.listeners.OnProductSelectedListener;
import com.mobiblanc.baridal_maghrib.models.products.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private Context context;
    private List<Product> arrayList;
    private OnProductSelectedListener onProductSelectedListener;

    public ProductsAdapter(Context context, List<Product> arrayList, OnProductSelectedListener onProductSelectedListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.onProductSelectedListener = onProductSelectedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.prodcuts_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(arrayList.get(position).getImage().get(0)).into(holder.image);
        holder.title.setText(arrayList.get(position).getName());
        holder.size.setText(arrayList.get(position).getShortDescription());

        holder.itemView.setOnClickListener(v -> onProductSelectedListener.onProductSelected(arrayList.get(position)));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.size)
        TextView size;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}