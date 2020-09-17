package com.mobiblanc.baridal_maghrib.views.cart.cartdetails;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.mobiblanc.baridal_maghrib.R;

abstract public class SwipeToDeleteCallback extends ItemTouchHelper.Callback {

    Context mContext;
    private Paint mClearPaint;
    private GradientDrawable mBackground;
    private int backgroundColor;


    SwipeToDeleteCallback(Context context) {
        mContext = context;
        mBackground = new GradientDrawable();
        backgroundColor = Color.parseColor("#D93535");
        mClearPaint = new Paint();
        mClearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.LEFT);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;
        int itemHeight = itemView.getHeight();

        boolean isCancelled = dX == 0 && !isCurrentlyActive;

        if (isCancelled) {
            clearCanvas(c, itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            return;
        }

        mBackground.setColor(backgroundColor);
        mBackground.setBounds(itemView.getRight() + (int) dX - 100, itemView.getTop() + 20, itemView.getRight(), itemView.getBottom() - 20);
        mBackground.draw(c);
        mBackground.setCornerRadius(45f);

        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setTextSize(30);
        p.setTypeface(ResourcesCompat.getFont(mContext, R.font.montserrat_bold));
        p.setTextAlign(Paint.Align.CENTER);

        int yPos = (int) ((itemView.getTop() + itemView.getHeight() / 2) - ((p.descent() + p.ascent()) / 2));
        c.drawText("Éliminer", itemView.getRight() - 100, yPos, p);


        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);


    }

    private void clearCanvas(Canvas c, Float left, Float top, Float right, Float bottom) {
        c.drawRect(left, top, right, bottom, mClearPaint);

    }

    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        return 0.7f;
    }
}
