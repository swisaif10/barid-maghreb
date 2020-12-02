package com.mobiblanc.gbam.views.cart.shipping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobiblanc.gbam.databinding.AgencyItemLayoutBinding;
import com.mobiblanc.gbam.listeners.OnItemSelectedListener;
import com.mobiblanc.gbam.models.shipping.agencies.Agency;

import java.util.ArrayList;
import java.util.List;

public class AgenciesAdapter extends RecyclerView.Adapter<AgenciesAdapter.AgencyViewHolder> {

    private final Context context;
    private final List<Agency> agencies;
    private final OnItemSelectedListener onItemSelectedListener;

    public AgenciesAdapter(Context context, List<Agency> agencies, OnItemSelectedListener onItemSelectedListener) {
        this.context = context;
        this.agencies = agencies;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @NonNull
    @Override
    public AgencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AgenciesAdapter.AgencyViewHolder(AgencyItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull AgencyViewHolder holder, int position) {
        holder.bind(agencies.get(position));
    }

    @Override
    public int getItemCount() {
        return agencies.size();
    }


    class AgencyViewHolder extends RecyclerView.ViewHolder {

        private final AgencyItemLayoutBinding itemBinding;

        AgencyViewHolder(AgencyItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        private void bind(Agency agency) {

            itemBinding.title.setText(agency.getTitle());
            itemBinding.address.setText(agency.getAddress());
            itemBinding.phone.setText(agency.getTelephone());
            itemBinding.fax.setText(agency.getFaxe());
            Glide.with(context).load(agency.getLogo()).into(itemBinding.logo);
            itemBinding.getRoot().setOnClickListener(v -> onItemSelectedListener.onItemSelected(getAdapterPosition(), agency));
        }
    }
}