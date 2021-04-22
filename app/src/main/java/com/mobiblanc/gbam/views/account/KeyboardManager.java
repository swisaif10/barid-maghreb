package com.mobiblanc.gbam.views.account;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

public class KeyboardManager {

    private static final String TAG = KeyboardManager.class.getSimpleName();
    private Context mContext;
    private View mContentView;
    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener;
    private boolean mIsKeyboardVisible;
    public KeyboardManager(Context context) {
        mContext = context;
    }

    public void registerKeyboardListener(final OnKeyboardListener listener, View view) {
        mContentView = view;
        unregisterKeyboardListener();
        mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            private int mPreviousHeight;

            @Override
            public void onGlobalLayout() {
                int newHeight = mContentView.getHeight();
                if (mPreviousHeight != 0) {
                    if (mPreviousHeight < newHeight) {
                        if (mIsKeyboardVisible) {
                            mIsKeyboardVisible = false;
                            hideNavigationBar();
                            if (listener != null) {
                                listener.onKeyboardHidden();
                            }
                        }
                    } else if (mPreviousHeight > newHeight) {
                        // Will ask InputMethodManager.isAcceptingText() to detect if keyboard appeared or not.
                        InputMethodManager imm = (InputMethodManager) mContentView.getContext()
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        boolean isAcceptingText = imm.isAcceptingText();
                        if (isAcceptingText) {
                            mIsKeyboardVisible = true;
                        }
                        if (mIsKeyboardVisible) {
                            if (listener != null) {
                                listener.onKeyboardVisible();
                            }
                        }
                    }
                }
                mPreviousHeight = newHeight;
            }
        };
        mContentView.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
    }

    public void unregisterKeyboardListener() {
        if (mOnGlobalLayoutListener != null) {
            mContentView.getViewTreeObserver().removeOnGlobalLayoutListener(mOnGlobalLayoutListener);
        }
    }

    private void hideNavigationBar() {
        if (mContext instanceof Activity) {
            ((Activity) mContext).getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }

    public interface OnKeyboardListener {
        void onKeyboardVisible();

        void onKeyboardHidden();
    }
}
