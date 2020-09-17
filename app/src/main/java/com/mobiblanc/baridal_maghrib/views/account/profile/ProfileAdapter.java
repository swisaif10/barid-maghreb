package com.mobiblanc.baridal_maghrib.views.account.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.models.Item;
import com.mobiblanc.baridal_maghrib.views.account.ConnexionActivity;
import com.mobiblanc.baridal_maghrib.views.account.history.MyHistoryFragment;
import com.mobiblanc.baridal_maghrib.views.main.help.HelpFragment;

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
                    ((ConnexionActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.container, new UpdatePersonalInformationsFragment()).addToBackStack(null).commit();
                    break;
                case "Mon historique":
                    ((ConnexionActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.container, new MyHistoryFragment()).addToBackStack(null).commit();
                    break;
                case "Assistance":
                    ((ConnexionActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.container, new HelpFragment()).addToBackStack(null).commit();
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