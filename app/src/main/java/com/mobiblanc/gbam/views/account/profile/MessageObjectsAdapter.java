package com.mobiblanc.gbam.views.account.profile;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.ContactObjectItemLayoutBinding;
import com.mobiblanc.gbam.listeners.OnItemSelectedListener;
import com.mobiblanc.gbam.models.contact.objects.Object;

import java.util.List;

public class MessageObjectsAdapter extends RecyclerView.Adapter<MessageObjectsAdapter.ITemViewHolder> {

    private final List<Object> items;
    private final OnItemSelectedListener onItemSelectedListener;

    public MessageObjectsAdapter(List<Object> items, OnItemSelectedListener onItemSelectedListener) {
        this.items = items;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @NonNull
    @Override
    public ITemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessageObjectsAdapter.ITemViewHolder(ContactObjectItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ITemViewHolder holder, int position) {

        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ITemViewHolder extends RecyclerView.ViewHolder {

        private final ContactObjectItemLayoutBinding itemBinding;

        ITemViewHolder(ContactObjectItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        private void bind(Object item) {
            itemBinding.name.setText(item.getSubject());

            itemBinding.name.setBackgroundResource(item.isSelected() ? R.drawable.selected_contact_background : R.drawable.contact_background
            );

            itemBinding.getRoot().setOnClickListener(view -> {
                if (!item.isSelected()) {
                    for (Object object : items) {
                        object.setSelected(false);
                    }
                    item.setSelected(true);
                    onItemSelectedListener.onItemSelected(getAdapterPosition(), item);
                }
                notifyDataSetChanged();
            });
        }
    }
}