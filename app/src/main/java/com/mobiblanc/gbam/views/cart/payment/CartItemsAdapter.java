package com.mobiblanc.gbam.views.cart.payment;

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
import com.mobiblanc.gbam.models.common.Item;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.ViewHolder> {

    private Context context;
    private List<Item> items;

    public CartItemsAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.confirmation_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        /*Glide.with(context).load(items.get(position).getImage()).into(holder.image);
        holder.title.setText(items.get(position).getName());
        holder.subtitle.setText(items.get(position).getShortDescription());
        holder.quantity.setText(items.get(position).getQty());
        holder.price.setText(String.valueOf(items.get(position).getPrice()));*/
        switch (position){
            case 0:
            case 2:
                holder.image.setImageResource(R.drawable.portrait);
                holder.title.setText("Portrait de Sa Majesté le Roi");
                holder.subtitle.setText("20cm*30cm");
                holder.quantity.setText("1");
                holder.price.setText("120,00");
                break;
            case 1:
                holder.image.setImageResource(R.drawable.timbre_4);
                holder.title.setText("Timbre");
                holder.subtitle.setText("1er Anniversaire de l’Intronisation de S.M.");
                holder.quantity.setText("2");
                holder.price.setText("2,50");
                break;
        }
        if (position == 2)
            holder.separator.setVisibility(View.GONE);
    }


    @Override
    public int getItemCount() {
        return 3;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.subtitle)
        TextView subtitle;
        @BindView(R.id.quantity)
        TextView quantity;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.separator)
        View separator;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}