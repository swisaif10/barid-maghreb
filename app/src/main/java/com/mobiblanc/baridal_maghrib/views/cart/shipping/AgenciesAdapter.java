package com.mobiblanc.baridal_maghrib.views.cart.shipping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.listeners.OnObjectSelectedListener;
import com.mobiblanc.baridal_maghrib.models.shipping.agencies.Agency;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AgenciesAdapter extends RecyclerView.Adapter<AgenciesAdapter.ViewHolder> {

    private Context context;
    private OnObjectSelectedListener onObjectSelectedListener;
    private List<Agency> agencies;

    public AgenciesAdapter(Context context, OnObjectSelectedListener onObjectSelectedListener, List<Agency> agencies) {
        this.context = context;
        this.onObjectSelectedListener = onObjectSelectedListener;
        this.agencies = agencies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.agency_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Agency agency = agencies.get(position);

        holder.title.setText(agency.getTitle());
        holder.address.setText(agency.getAddress());
        holder.phone.setText(agency.getTelephone());
        holder.fax.setText(agency.getFaxe());
        holder.logo.setImageResource(R.drawable.logo11);
        holder.itemView.setOnClickListener(v -> onObjectSelectedListener.onObjectSelected(agency));
    }

    @Override
    public int getItemCount() {
        return agencies.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.logo)
        ImageView logo;
        @BindView(R.id.address)
        TextView address;
        @BindView(R.id.phone)
        TextView phone;
        @BindView(R.id.fax)
        TextView fax;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}