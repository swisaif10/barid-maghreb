package com.mobiblanc.baridal_maghrib.views.cart.cartdetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.listeners.OnDialogButtonsClickListener;
import com.mobiblanc.baridal_maghrib.models.CartItem;
import com.mobiblanc.baridal_maghrib.views.cart.delivery.DeliveryModeFragment;
import com.mobiblanc.baridal_maghrib.views.connexion.ConnexionActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartDetailsFragment extends Fragment implements OnDialogButtonsClickListener {

    @BindView(R.id.cartRecycler)
    RecyclerView cartRecycler;
    private CartAdapter cartAdapter;
    private Boolean started = false;

    public CartDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_details, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (started) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new DeliveryModeFragment()).addToBackStack("").commit();
            started = false;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @OnClick({R.id.backBtn, R.id.nextBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                getActivity().onBackPressed();
                break;
            case R.id.nextBtn:
                started = true;
                new LoginDialog(getActivity(), this).show();
                break;
        }
    }

    @Override
    public void onFirstButtonClick() {
        Intent intent = new Intent(getActivity(), ConnexionActivity.class);
        intent.putExtra("to_login", true);
        startActivity(intent);
    }

    @Override
    public void onSecondButtonClick() {
        Intent intent = new Intent(getActivity(), ConnexionActivity.class);
        intent.putExtra("to_login", false);
        startActivity(intent);
    }

    private void init() {
        ArrayList<CartItem> items = new ArrayList<CartItem>() {
            {
                add(new CartItem(R.drawable.timbre, "Timbre", "Véhicule circulant au Maroc", "7,50 MAD"));
                add(new CartItem(R.drawable.portrait_1, "Portrait de Sa Majesté le Roi", "20cm*30cm", "500,00 MAD"));
            }
        };
        cartRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        cartAdapter = new CartAdapter(getContext(), items);
        cartRecycler.setAdapter(cartAdapter);

        enableSwipeToDeleteAndUndo();

    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final CartItem item = cartAdapter.getData().get(position);

                cartAdapter.removeItem(position);

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(cartRecycler);
    }

}