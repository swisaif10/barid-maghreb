package com.mobiblanc.gbam.views.account.orders;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.gbam.databinding.OrderItemLayoutBinding;
import com.mobiblanc.gbam.listeners.OnItemSelectedListener;
import com.mobiblanc.gbam.models.orders.Order;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.HistoryViewHolder> {

    private final List<Order> arrayList;
    private final OnItemSelectedListener onItemSelectedListener;

    public OrdersAdapter(List<Order> arrayList, OnItemSelectedListener onItemSelectedListener) {
        this.arrayList = arrayList;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryViewHolder(OrderItemLayoutBinding.inflate(
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

        private final OrderItemLayoutBinding itemBinding;

        HistoryViewHolder(OrderItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        @SuppressLint("SimpleDateFormat")
        private void bind(Order order) {
            itemBinding.title.setText(String.format("Commande N°%s", order.getOrderId()));
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            SimpleDateFormat format2 = new SimpleDateFormat("dd MMMM yyy", new Locale("fr"));
            Date date = null;
            try {
                date = format1.parse(order.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println();
            itemBinding.date.setText(format2.format(date));
            itemBinding.productsNumber.setText(order.getTotalItem());
            Float price = Float.parseFloat(order.getTotalAmount());
            DecimalFormat df = new DecimalFormat("0.00");
            df.setMaximumFractionDigits(2);
            itemBinding.totalPrice.setText(String.format("%s MAD", df.format(price)));

            itemBinding.getRoot().setOnClickListener(v -> onItemSelectedListener.onItemSelected(getAdapterPosition(), order));
        }
    }
}