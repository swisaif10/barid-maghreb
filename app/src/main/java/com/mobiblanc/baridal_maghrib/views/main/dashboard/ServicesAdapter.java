package com.mobiblanc.baridal_maghrib.views.main.dashboard;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.models.dashboard.Service;
import com.mobiblanc.baridal_maghrib.views.account.ConnexionActivity;
import com.mobiblanc.baridal_maghrib.views.main.MainActivity;
import com.mobiblanc.baridal_maghrib.views.main.help.HelpFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

    private Context context;
    private List<Service> arrayList;

    public ServicesAdapter(Context context, List<Service> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.services_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(arrayList.get(position).getIcon()).fitCenter().into(holder.image);
        holder.title.setText(arrayList.get(position).getTitle());

        holder.itemView.setOnClickListener(v -> {
            if (arrayList.get(position).getTitle().equalsIgnoreCase("Assistance")) {
                Intent intent = new Intent(context, ConnexionActivity.class);
                intent.putExtra("destination", 2);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.title)
        TextView title;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}