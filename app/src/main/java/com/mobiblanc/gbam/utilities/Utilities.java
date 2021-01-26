package com.mobiblanc.gbam.utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.provider.Settings.Secure;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.listeners.OnDialogButtonsClickListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Utilities {

    static void hideSoftKeyboard(Context context, View view) {
        if (context == null || view == null) {
            return;
        }
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            Log.e("", e.getMessage());
        }
    }

    static void showErrorPopup(Context context, String message) {

        if (context == null) {
            return;
        }

        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);

        View view = LayoutInflater.from(context).inflate(R.layout.server_error_dialog, null, false);
        Button ok = view.findViewById(R.id.okBtn);
        TextView msg = view.findViewById(R.id.message);
        ConstraintLayout container = view.findViewById(R.id.container);

        msg.setText(message);

        ok.setOnClickListener(v -> dialog.dismiss());
        container.setOnClickListener(v -> dialog.dismiss());
        dialog.setContentView(view);
        dialog.show();
    }

    static void showErrorPopupWithClick(Context context, String message, View.OnClickListener onClickListener) {

        if (context == null) {
            return;
        }

        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);

        View view = LayoutInflater.from(context).inflate(R.layout.server_error_dialog, null, false);
        Button ok = view.findViewById(R.id.okBtn);
        TextView msg = view.findViewById(R.id.message);
        ConstraintLayout container = view.findViewById(R.id.container);

        msg.setText(message);

        ok.setOnClickListener(v -> {
            dialog.dismiss();
            onClickListener.onClick(v);
        });
        //container.setOnClickListener(v -> dialog.dismiss());
        dialog.setContentView(view);
        dialog.show();
    }

    static void showTrackingDialog(Context context, String message, View.OnClickListener onClickListener, Boolean canSendCall) {

        if (context == null) {
            return;
        }

        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);

        View view = LayoutInflater.from(context).inflate(R.layout.tracking_dialog, null, false);
        Button ok = view.findViewById(R.id.callBtn);
        TextView msg = view.findViewById(R.id.message);
        ConstraintLayout container = view.findViewById(R.id.container);

        if (canSendCall)
            ok.setVisibility(View.VISIBLE);

        msg.setText(message);

        ok.setOnClickListener(v -> {
            dialog.dismiss();
            onClickListener.onClick(v);
        });
        container.setOnClickListener(v -> dialog.dismiss());
        dialog.setContentView(view);
        dialog.show();
    }

    static void showConfirmationDialog(Context context, View.OnClickListener onClickListener) {

        if (context == null) {
            return;
        }

        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);

        View view = LayoutInflater.from(context).inflate(R.layout.confirmation_dialog, null, false);
        Button next = view.findViewById(R.id.nextBtn);

        next.setOnClickListener(onClickListener);
        dialog.setContentView(view);
        dialog.show();
    }

    static void showUpdateDialog(Context context, String status, OnDialogButtonsClickListener onDialogButtonsClickListener) {

        if (context == null) {
            return;
        }

        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);

        View view = LayoutInflater.from(context).inflate(R.layout.update_dialog, null, false);
        Button update = view.findViewById(R.id.updateBtn);
        Button cancel = view.findViewById(R.id.notNowBtn);
        update.setOnClickListener(v -> {
            dialog.dismiss();
            onDialogButtonsClickListener.onFirstButtonClick(null);
        });
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
            onDialogButtonsClickListener.onSecondButtonClick();
        });
        if (status.equalsIgnoreCase("blocked"))
            cancel.setVisibility(View.GONE);
        dialog.setContentView(view);
        dialog.show();
    }

    static void showDashboardDialog(Context context,String title, String description, View.OnClickListener onClickListener) {

        if (context == null) {
            return;
        }

        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);

        View view = LayoutInflater.from(context).inflate(R.layout.dashboard_dialog, null, false);
        Button next = view.findViewById(R.id.nextBtn);
        TextView titleTV = view.findViewById(R.id.title);
        TextView descriptionTV = view.findViewById(R.id.description);
        ConstraintLayout container = view.findViewById(R.id.container);

        titleTV.setText(title);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            descriptionTV.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY));
        } else {
            descriptionTV.setText(Html.fromHtml(description));
        }

        next.setOnClickListener(v -> {
            dialog.dismiss();
            onClickListener.onClick(v);
        });
        container.setOnClickListener(v -> dialog.dismiss());
        dialog.setContentView(view);
        dialog.show();
    }

    static void showCommentDialog(Context context) {

        if (context == null) {
            return;
        }

        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);

        View view = LayoutInflater.from(context).inflate(R.layout.fragment_add_new_comment, null, false);
        Button addBtn = view.findViewById(R.id.addBtn);
        ConstraintLayout container = view.findViewById(R.id.container);

        addBtn.setOnClickListener(v -> {
            dialog.dismiss();
        });
        container.setOnClickListener(v -> dialog.dismiss());
        dialog.setContentView(view);
        dialog.show();
    }


    static Boolean isEmpty(TextInputEditText textInputEditText) {
        return textInputEditText.getText().toString().equalsIgnoreCase("");
    }

    static Boolean isEmpty(EditText editText) {
        return editText.getText().toString().equalsIgnoreCase("");
    }

    static boolean isEmailValid(String email) {
        return email.equals("") || Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    static boolean isPasswordValid(String password) {
        String expression = "(?=.{8,})((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])|(?=.*\\d)(?=.*[a-zA-Z])(?=.*[\\W_])|(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_])).*";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    static float dpToPx(Context context, float dp) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    static String getUUID(Context context) {

        return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
    }
}
