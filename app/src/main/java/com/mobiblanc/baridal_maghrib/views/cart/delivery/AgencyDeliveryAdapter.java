package com.mobiblanc.baridal_maghrib.views.cart.delivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.listeners.OnItemSelectedListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AgencyDeliveryAdapter extends RecyclerView.Adapter<AgencyDeliveryAdapter.ViewHolder> {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.logo)
    ImageView logo;
    private Context context;
    private OnItemSelectedListener onItemSelectedListener;

    public AgencyDeliveryAdapter(Context context, OnItemSelectedListener onItemSelectedListener) {
        this.context = context;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.agency_delivery_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == 0){
            holder.title.setText("Agence Fida - Casablanca");
            holder.logo.setImageResource(R.drawable.logo11);
        }else {
            holder.title.setText("Agence Hay Chrifa - Casablanca");
            holder.logo.setImageResource(R.drawable.logo2);
        }
        holder.itemView.setOnClickListener(v -> onItemSelectedListener.onItemSelected(position));
    }

    @Override
    public int getItemCount() {
        return 10;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.logo)
        ImageView logo;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}