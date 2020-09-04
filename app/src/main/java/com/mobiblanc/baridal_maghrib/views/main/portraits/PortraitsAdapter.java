package com.mobiblanc.baridal_maghrib.views.main.portraits;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.models.Item;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class PortraitsAdapter extends RecyclerView.Adapter<PortraitsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Item> arrayList;

    public PortraitsAdapter(Context context, ArrayList<Item> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.portraits_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> context.startActivity(new Intent(context, PortraitDetailsActivity.class)));
    }

    @Override
    public int getItemCount() {
        return 8;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}