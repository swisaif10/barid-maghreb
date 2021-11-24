package com.mobiblanc.gbam.views.account.orders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.gbam.databinding.OrderDetailRowBinding;
import com.mobiblanc.gbam.databinding.OrderDetailRowBinding;
import com.mobiblanc.gbam.listeners.OnItemSelectedListener;
import com.mobiblanc.gbam.models.orders.details.OrderDetail;
import com.mobiblanc.gbam.models.orders.details.OrderDetail;
import com.mobiblanc.gbam.models.orders.details.TrackingNumber;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.HistoryViewHolder> {

    private final List<OrderDetail> arrayList;
    OrdersDetailsItemsAdapter adapter;
    private final OnItemSelectedListener onItemSelectedListener;
    Context context;


    public OrderDetailsAdapter(List<OrderDetail> arrayList, Context context, OnItemSelectedListener onItemSelectedListener) {
        this.arrayList = arrayList;
        this.onItemSelectedListener = onItemSelectedListener;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryViewHolder(OrderDetailRowBinding.inflate(
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

        private final OrderDetailRowBinding itemBinding;

        HistoryViewHolder(OrderDetailRowBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        @SuppressLint("SimpleDateFormat")
        private void bind(OrderDetail orderDetail) {
            itemBinding.orderNumber.setText(String.format("N°%s", orderDetail.getOrderId()));
            itemBinding.orderDetailsRecycler.setLayoutManager(new LinearLayoutManager(context));
            adapter = new OrdersDetailsItemsAdapter(orderDetail.getTrackingNumbers(),onItemSelectedListener);
            itemBinding.orderDetailsRecycler.setAdapter(adapter);
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format2 = new SimpleDateFormat("dd MMMM yyy", new Locale("fr"));
        }
    }
}
