package com.mobiblanc.gbam.views.account.profile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.models.Item;
import com.mobiblanc.gbam.views.account.AccountActivity;
import com.mobiblanc.gbam.views.account.help.HelpFragment;
import com.mobiblanc.gbam.views.account.history.MyHistoryFragment;
import com.mobiblanc.gbam.views.tracking.TrackingActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    private Context context;
    private List<Item> arrayList;

    public ProfileAdapter(Context context, List<Item> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.profile_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.icon.setImageResource(arrayList.get(position).getImage());
        holder.title.setText(arrayList.get(position).getTitle());

        holder.itemView.setOnClickListener(v -> {
            switch (arrayList.get(position).getTitle()) {
                case "Mes informations personnelles":
                    ((AccountActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.container, new UpdatePersonalInformationFragment()).addToBackStack(null).commit();
                    break;
                case "Mon historique":
                    ((AccountActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.container, new MyHistoryFragment()).addToBackStack(null).commit();
                    break;
                case "Contact":
                    ((AccountActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.container, new HelpFragment()).addToBackStack(null).commit();
                    break;
                case "Suivi de commande":
                    context.startActivity(new Intent(context, TrackingActivity.class));
                    break;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.title)
        TextView title;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}