package com.mobiblanc.baridal_maghrib.views.main.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.models.dashboard.DashboardResponseData;
import com.mobiblanc.baridal_maghrib.views.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardFragment extends Fragment {


    @BindView(R.id.categoriesRecycler)
    SnappingRecyclerView categoriesRecycler;
    @BindView(R.id.servicesRecycler)
    RecyclerView servicesRecycler;
    DashboardResponseData responseData;


    public static DashboardFragment newInstance(DashboardResponseData responseData) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putSerializable("response", responseData);
        fragment.setArguments(args);
        return fragment;
    }

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
            responseData = (DashboardResponseData) getArguments().getSerializable("response");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        categoriesRecycler.enableViewScaling(true);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        //categoriesRecycler.setLayoutManager(new ScaleLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        categoriesRecycler.setAdapter(new CategoriesAdapter(getContext(), responseData.getCategories()));

        servicesRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        servicesRecycler.setAdapter(new ServicesAdapter(getContext(), responseData.getServices()));
    }

}

class ScaleLayoutManager extends LinearLayoutManager {

    private final float mShrinkAmount = 0.15f;
    private final float mShrinkDistance = 0.9f;

    public ScaleLayoutManager(Context context) {
        super(context);
    }

    public ScaleLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {

        int orientation = getOrientation();

        if (orientation == HORIZONTAL) {
            int scrolled = super.scrollHorizontallyBy(dx, recycler, state);
            float midpoint = getWidth() / 2.f;
            float d0 = 0.f;
            float d1 = mShrinkDistance * midpoint;
            float s0 = 1.f;
            float s1 = 1.f - mShrinkAmount;

            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                float childMidpoint = (getDecoratedRight(child) + getDecoratedLeft(child)) / 2.f;
                float d = Math.min(d1, Math.abs(midpoint - childMidpoint));
                float scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0);
                child.setScaleX(scale);
                child.setScaleY(scale);
            }

            return scrolled;
        } else {
            return 0;
        }
    }
}
