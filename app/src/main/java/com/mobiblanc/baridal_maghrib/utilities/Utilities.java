package com.mobiblanc.baridal_maghrib.utilities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.listeners.OnDialogButtonsClickListener;

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

    static void showUpdateDialog(Context context, OnDialogButtonsClickListener onDialogButtonsClickListener) {

        if (context == null) {
            return;
        }

        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);

        View view = LayoutInflater.from(context).inflate(R.layout.update_dialog, null, false);
        Button update = view.findViewById(R.id.updateBtn);
        Button cancel = view.findViewById(R.id.notNowBtn);
        update.setOnClickListener(v -> {
            dialog.dismiss();
            onDialogButtonsClickListener.onFirstButtonClick();
        });
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
            onDialogButtonsClickListener.onSecondButtonClick();
        });
        dialog.setContentView(view);
        dialog.show();
    }

    static Boolean isEmpty(TextInputEditText textInputEditText) {
        return textInputEditText.getText().toString().equalsIgnoreCase("");
    }

    static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    static boolean isPasswordValid(String password) {
        String expression = "(?=.{8,})((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])|(?=.*\\d)(?=.*[a-zA-Z])(?=.*[\\W_])|(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_])).*";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
