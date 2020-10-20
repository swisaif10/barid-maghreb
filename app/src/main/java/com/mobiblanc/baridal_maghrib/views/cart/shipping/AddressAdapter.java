package com.mobiblanc.baridal_maghrib.views.cart.shipping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.listeners.OnObjectSelectedListener;
import com.mobiblanc.baridal_maghrib.models.shipping.address.Address;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private Context context;
    private List<Address> arrayList;
    private OnObjectSelectedListener onObjectSelectedListener;

    public AddressAdapter(Context context, List<Address> arrayList, OnObjectSelectedListener onObjectSelectedListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.onObjectSelectedListener = onObjectSelectedListener;
        //this.arrayList.add(new Address("test123", 123, "123456789", false));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.address_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == 0)
            holder.title.setText("Chez moi");
        else
            holder.title.setText("Bureau");

        if (arrayList.get(position).getSelected() == null)
            arrayList.get(position).setSelected(false);
        holder.background.setBackgroundResource(arrayList.get(position).getSelected() ? R.drawable.bg_yellow : R.drawable.bg_1);
        holder.address.setText(arrayList.get(position).getAdresse());
        holder.phoneNumber.setText(arrayList.get(position).getTelephone());

        holder.background.setOnClickListener(view -> {
            if (arrayList.get(position).getSelected()) {
                arrayList.get(position).setSelected(false);
                onObjectSelectedListener.onObjectSelected(null);
            } else {
                for (Address address : arrayList) {
                    address.setSelected(false);
                }
                arrayList.get(position).setSelected(true);
                onObjectSelectedListener.onObjectSelected(arrayList.get(position));
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.address)
        TextView address;
        @BindView(R.id.phoneNumber)
        TextView phoneNumber;
        @BindView(R.id.background)
        ConstraintLayout background;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}