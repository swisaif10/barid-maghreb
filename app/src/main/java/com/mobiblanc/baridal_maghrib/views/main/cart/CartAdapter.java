package com.mobiblanc.baridal_maghrib.views.main.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.models.CartItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CartItem> arrayList;

    public CartAdapter(Context context, ArrayList<CartItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.image.setImageResource(arrayList.get(position).getImage());
        holder.title.setText(arrayList.get(position).getTitle());
        holder.subtitle.setText(arrayList.get(position).getDescription());
        holder.total.setText(arrayList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void removeItem(int position) {
        arrayList.remove(position);
        notifyItemRemoved(position);
    }

    public ArrayList<CartItem> getData() {
        return arrayList;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.subtitle)
        TextView subtitle;
        @BindView(R.id.decreaseBtn)
        ImageView decreaseBtn;
        @BindView(R.id.quantity)
        TextView quantity;
        @BindView(R.id.increaseBtn)
        ImageView increaseBtn;
        @BindView(R.id.total)
        TextView total;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}