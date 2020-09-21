package com.mobiblanc.baridal_maghrib.views.main.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.listeners.OnDashboardItemSelectedListener;
import com.mobiblanc.baridal_maghrib.models.dashboard.Category;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private Context context;
    private List<Category> arrayList;
    private OnDashboardItemSelectedListener onDashboardItemSelectedListener;

    public CategoriesAdapter(Context context, List<Category> arrayList, OnDashboardItemSelectedListener onDashboardItemSelectedListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.onDashboardItemSelectedListener = onDashboardItemSelectedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categories_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (arrayList.get(position).getName().equalsIgnoreCase("Timbres"))
            holder.background.setBackground(context.getResources().getDrawable(R.drawable.timbres));
        else
            holder.background.setBackground(context.getResources().getDrawable(R.drawable.portrait));

        holder.title.setText(arrayList.get(position).getName());

        holder.itemView.setOnClickListener(v -> onDashboardItemSelectedListener.onDashboardItemSelected(position, 0));
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