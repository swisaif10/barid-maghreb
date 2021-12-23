package com.mobiblanc.gbam.views.account.profile;

import android.annotation.SuppressLint;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.gbam.databinding.FaqStampItemLayoutBinding;
import com.mobiblanc.gbam.models.faqstamp.FAQItem;

import java.util.ArrayList;
import java.util.List;

public class FAQStampsAdapter extends RecyclerView.Adapter<FAQStampsAdapter.ITemViewHolder> {

    private final List<FAQItem> items;
    ArrayList<ITemViewHolder> holders;

    public FAQStampsAdapter(List<FAQItem> items) {
        holders = new ArrayList<>();
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
    public void onBindViewHolder(@NonNull ITemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (holders.size() < items.size())
            holders.add(holder);
        holder.bind(items.get(position), holders, position);
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

        private void bind(FAQItem item, ArrayList<ITemViewHolder> holders, int position) {
            itemBinding.question.setText(item.getQuestion());
            Spanned response = Html.fromHtml(item.getResponse().replace("\\n", "<br>"));
            itemBinding.response.setText(response);
            itemBinding.getRoot().setOnClickListener(view -> {
                for (int i = 0; i < holders.size(); i++) {
                    Log.d("TAG", "bind: " + holders.get(i).itemBinding.response.getText().toString());
                    if (i == position) {
                        if (holders.get(i).itemBinding.response.getVisibility() == View.GONE) {
                            holders.get(i).itemBinding.separator.setVisibility(View.VISIBLE);
                            holders.get(i).itemBinding.response.setVisibility(View.VISIBLE);
                        } else {
                            holders.get(i).itemBinding.separator.setVisibility(View.GONE);
                            holders.get(i).itemBinding.response.setVisibility(View.GONE);
                        }
                    } else {
                        holders.get(i).itemBinding.separator.setVisibility(View.GONE);
                        holders.get(i).itemBinding.response.setVisibility(View.GONE);
                    }
                }
            });

        }
    }
}