package com.mobiblanc.baridal_maghrib.views.cart.delivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.listeners.OnItemSelectedListener;
import com.mobiblanc.baridal_maghrib.models.CartItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeliveryModeAdapter extends RecyclerView.Adapter<DeliveryModeAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CartItem> arrayList;
    private OnItemSelectedListener onItemSelectedListener;


    public DeliveryModeAdapter(Context context, ArrayList<CartItem> arrayList, OnItemSelectedListener onItemSelectedListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.delivery_mode_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(arrayList.get(position).getTitle());
        holder.itemView.setOnClickListener(v -> onItemSelectedListener.onItemSelected(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.subtitle)
        TextView subtitle;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}