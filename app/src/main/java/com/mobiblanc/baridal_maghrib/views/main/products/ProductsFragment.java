package com.mobiblanc.baridal_maghrib.views.main.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.models.products.Product;
import com.mobiblanc.baridal_maghrib.models.products.ProductsData;
import com.mobiblanc.baridal_maghrib.utilities.Connectivity;
import com.mobiblanc.baridal_maghrib.utilities.Utilities;
import com.mobiblanc.baridal_maghrib.viewmodels.MainVM;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsFragment extends Fragment {

    @BindView(R.id.portraitsRecycler)
    RecyclerView portraitsRecycler;
    @BindView(R.id.title)
    TextView title;
    private String id;
    private String titleTxt;
    private Connectivity connectivity;
    private MainVM mainVM;

    public static ProductsFragment newInstance(String id, String title) {
        ProductsFragment fragment = new ProductsFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    public ProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainVM = ViewModelProviders.of(this).get(MainVM.class);
        connectivity = new Connectivity(getContext(), this);
        mainVM.getProductsLiveData().observe(this, this::handleProductsData);

        if (getArguments() != null) {
            id = getArguments().getString("id");
            titleTxt = getArguments().getString("title");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getProducts();
    }

    private void init(List<Product> products) {
        title.setText(titleTxt);
        portraitsRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        portraitsRecycler.setAdapter(new ProductsAdapter(getContext(), products));
    }

    private void getProducts() {
        if (connectivity.isConnected())
            mainVM.getProducts(Integer.valueOf(id));
        else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleProductsData(ProductsData productsData) {

        if (productsData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = productsData.getHeader().getCode();
            if (code == 200) {
                init(productsData.getResponse());
            } else {
                Utilities.showErrorPopup(getContext(), productsData.getHeader().getMessage());
            }
        }
    }
}