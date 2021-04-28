package com.mobiblanc.gbam.views.account.orders;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.gbam.databinding.OrderDetailsItemLayoutBinding;
import com.mobiblanc.gbam.listeners.OnItemSelectedListener;
import com.mobiblanc.gbam.models.orders.details.OrderDetail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrdersDetailsAdapter extends RecyclerView.Adapter<OrdersDetailsAdapter.HistoryViewHolder> {

    private final List<OrderDetail> arrayList;
    private final OnItemSelectedListener onItemSelectedListener;

    public OrdersDetailsAdapter(List<OrderDetail> arrayList, OnItemSelectedListener onItemSelectedListener) {
        this.arrayList = arrayList;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryViewHolder(OrderDetailsItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.bind(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class HistoryViewHolder extends RecyclerView.ViewHolder {

        private final OrderDetailsItemLayoutBinding itemBinding;

        HistoryViewHolder(OrderDetailsItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        @SuppressLint("SimpleDateFormat")
        private void bind(OrderDetail orderDetail) {
            itemBinding.title.setText(String.format("N°%s", orderDetail.getTrackingNumber()));
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format2 = new SimpleDateFormat("dd MMMM yyy", new Locale("fr"));
            Date date = null;
            try {
                date = format1.parse(orderDetail.getCreatedAt());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println();
            itemBinding.date.setText(format2.format(date));

            itemBinding.getRoot().setOnClickListener(v -> onItemSelectedListener.onItemSelected(getAdapterPosition(), orderDetail));
        }
    }
}