package com.mobiblanc.baridal_maghrib.views.main.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.models.Item;
import com.mobiblanc.baridal_maghrib.views.main.MainActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Item> arrayList;

    public CategoriesAdapter(Context context, ArrayList<Item> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_categories_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.background.setBackground(context.getResources().getDrawable(arrayList.get(position).getImage()));
        holder.title.setText(arrayList.get(position).getTitle());
        holder.itemView.setOnClickListener(v -> {
            if (position == 0) ((MainActivity) context).selectTab(1);
            else if(position == 1) ((MainActivity) context).selectTab(2);
            else ((MainActivity) context).selectTab(3);
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.background)
        ConstraintLayout background;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}