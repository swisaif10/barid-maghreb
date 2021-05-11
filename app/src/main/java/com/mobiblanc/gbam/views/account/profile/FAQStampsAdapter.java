package com.mobiblanc.gbam.views.account.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.gbam.databinding.FaqStampItemLayoutBinding;
import com.mobiblanc.gbam.models.faqstamp.FAQItem;

import java.util.List;

public class FAQStampsAdapter extends RecyclerView.Adapter<FAQStampsAdapter.ITemViewHolder> {

    private final List<FAQItem> items;

    public FAQStampsAdapter(List<FAQItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ITemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ITemViewHolder(FaqStampItemLayoutBinding.inflate(
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

    static class ITemViewHolder extends RecyclerView.ViewHolder {

        private final FaqStampItemLayoutBinding itemBinding;

        ITemViewHolder(FaqStampItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        private void bind(FAQItem item) {
            itemBinding.question.setText(item.getQuestion());
            itemBinding.response.setText(item.getResponse().replace("\\n ", "\n"));

            itemBinding.getRoot().setOnClickListener(view -> {
                if (itemBinding.response.getVisibility() == View.VISIBLE) {
                    itemBinding.separator.setVisibility(View.GONE);
                    itemBinding.response.setVisibility(View.GONE);
                } else {
                    itemBinding.separator.setVisibility(View.VISIBLE);
                    itemBinding.response.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}