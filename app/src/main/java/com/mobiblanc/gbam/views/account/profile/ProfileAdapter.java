package com.mobiblanc.gbam.views.account.profile;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.gbam.databinding.ProfileItemLayoutBinding;
import com.mobiblanc.gbam.listeners.OnItemSelectedListener;
import com.mobiblanc.gbam.models.Item;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ITemViewHolder> {

    private final List<Item> arrayList;
    private final OnItemSelectedListener onItemSelectedListener;

    public ProfileAdapter(List<Item> arrayList, OnItemSelectedListener onItemSelectedListener) {
        this.arrayList = arrayList;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @NonNull
    @Override
    public ITemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProfileAdapter.ITemViewHolder(ProfileItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ITemViewHolder holder, int position) {

       holder.bind(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ITemViewHolder extends RecyclerView.ViewHolder {

        private final ProfileItemLayoutBinding itemBinding;

        ITemViewHolder(ProfileItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        private void bind(Item item){
            itemBinding.icon.setImageResource(item.getImage());
            itemBinding.title.setText(item.getTitle());

            itemBinding.getRoot().setOnClickListener(view -> onItemSelectedListener.onItemSelected(getAdapterPosition(), null));
        }
    }
}