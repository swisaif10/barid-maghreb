package com.mobiblanc.gbam.views.account.history;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.models.HistoryItem;
import com.mobiblanc.gbam.views.account.AccountActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyHistoryAdapter extends RecyclerView.Adapter<MyHistoryAdapter.ViewHolder> {

    private Context context;
    private ArrayList<HistoryItem> arrayList;

    public MyHistoryAdapter(Context context, ArrayList<HistoryItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_history_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.status.setText(arrayList.get(position).getStatus());
        holder.color.setColorFilter(ContextCompat.getColor(context, arrayList.get(position).getColor()), PorterDuff.Mode.SRC_ATOP);

        holder.itemView.setOnClickListener(v -> {
            ((AccountActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.container, new OrderDetailsFragment()).addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.color)
        ImageView color;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}