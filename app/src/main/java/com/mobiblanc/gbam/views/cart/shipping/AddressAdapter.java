package com.mobiblanc.gbam.views.cart.shipping;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.AddressItemLayoutBinding;
import com.mobiblanc.gbam.listeners.OnItemSelectedListener;
import com.mobiblanc.gbam.models.shipping.address.Address;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    private final List<Address> arrayList;
    private final OnItemSelectedListener onItemSelectedListener;

    public AddressAdapter(List<Address> arrayList, OnItemSelectedListener onItemSelectedListener) {
        this.arrayList = arrayList;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddressAdapter.AddressViewHolder(AddressItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        holder.bind(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class AddressViewHolder extends RecyclerView.ViewHolder {

        private final AddressItemLayoutBinding itemBinding;

        AddressViewHolder(AddressItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        private void bind(Address address) {
            itemBinding.title.setText(address.getName());
            itemBinding.address.setText(address.getAdresse());
            itemBinding.phoneNumber.setText(address.getTelephone());

            if (address.getSelected() == null)
                address.setSelected(false);
            itemBinding.background.setBackgroundResource(address.getSelected() ? R.drawable.bg_yellow : R.drawable.bg_1);

            itemBinding.background.setOnClickListener(view -> {
                if (!address.getSelected()) {
                    for (Address item : arrayList) {
                        item.setSelected(false);
                    }
                    address.setSelected(true);
                    onItemSelectedListener.onItemSelected(getAdapterPosition(), true);
                }
                notifyDataSetChanged();
            });
        }
    }
}