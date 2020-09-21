package com.mobiblanc.baridal_maghrib.views.cart.cartdetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.listeners.OnItemQuantityChangedListener;
import com.mobiblanc.baridal_maghrib.models.cart.items.CartItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private List<CartItem> arrayList;
    private OnItemQuantityChangedListener onItemQuantityChangedListener;

    public CartAdapter(Context context, List<CartItem> arrayList, OnItemQuantityChangedListener onItemQuantityChangedListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.onItemQuantityChangedListener = onItemQuantityChangedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(arrayList.get(position).getImage()).into(holder.image);
        holder.title.setText(arrayList.get(position).getName());
        holder.subtitle.setText(arrayList.get(position).getShortDescription());
        holder.quantity.setText(arrayList.get(position).getQty());
        holder.price.setText(String.valueOf(arrayList.get(position).getPrice()));

        holder.decreaseBtn.setOnClickListener(v -> {
            if (Integer.valueOf(holder.quantity.getText().toString()) > 1) {
                int qty = Integer.valueOf(holder.quantity.getText().toString()) - 1;
                holder.quantity.setText(String.valueOf(qty));
                onItemQuantityChangedListener.onItemQuantityChanged(position, Integer.valueOf(qty));
            }
        });

        holder.increaseBtn.setOnClickListener(v -> {
            if (Integer.valueOf(holder.quantity.getText().toString()) < 9) {
                int qty = Integer.valueOf(holder.quantity.getText().toString()) + 1;
                holder.quantity.setText(String.valueOf(qty));
                onItemQuantityChangedListener.onItemQuantityChanged(position, Integer.valueOf(qty));
            }
        });
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
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.subtitle)
        TextView subtitle;
        @BindView(R.id.decreaseBtn)
        RelativeLayout decreaseBtn;
        @BindView(R.id.quantity)
        TextView quantity;
        @BindView(R.id.increaseBtn)
        RelativeLayout increaseBtn;
        @BindView(R.id.price)
        TextView price;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}