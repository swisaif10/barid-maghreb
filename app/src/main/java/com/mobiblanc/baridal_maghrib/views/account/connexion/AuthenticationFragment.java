package com.mobiblanc.baridal_maghrib.views.account.connexion;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.baridal_maghrib.models.authentication.login.LoginData;
import com.mobiblanc.baridal_maghrib.utilities.Connectivity;
import com.mobiblanc.baridal_maghrib.utilities.Constants;
import com.mobiblanc.baridal_maghrib.utilities.Utilities;
import com.mobiblanc.baridal_maghrib.viewmodels.AccountVM;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifImageView;

public class AuthenticationFragment extends Fragment {

    @BindView(R.id.error)
    TextView error;
    @BindView(R.id.email)
    TextInputEditText email;
    @BindView(R.id.password)
    TextInputEditText password;
    @BindView(R.id.loginBtn)
    MaterialButton loginBtn;
    @BindView(R.id.loader)
    GifImageView loader;
    private Connectivity connectivity;
    private AccountVM accountVM;
    private PreferenceManager preferenceManager;

    public AuthenticationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountVM = ViewModelProviders.of(this).get(AccountVM.class);
        connectivity = new Connectivity(getContext(), this);
        accountVM.getLoginLiveData().observe(this, this::handleLoginData);

        preferenceManager = new PreferenceManager.Builder(getContext(), Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authentication, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
    }

    @OnClick({R.id.backBtn, R.id.siginBtn, R.id.loginBtn, R.id.container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                getActivity().onBackPressed();
                break;
            case R.id.siginBtn:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new RegistrationFragment()).addToBackStack(null).commit();
                break;
            case R.id.loginBtn:
                login();
            case R.id.container:
                Utilities.hideSoftKeyboard(getContext(), getView());
                break;
        }
    }

    private void init() {

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkForm();
            }
        };

        email.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
    }

    private void login() {
        if (connectivity.isConnected()) {
            loader.setVisibility(View.VISIBLE);
            accountVM.login(email.getText().toString(), password.getText().toString(), preferenceManager.getValue(Constants.CART_ID, null), preferenceManager);
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleLoginData(LoginData loginData) {
        loader.setVisibility(View.GONE);
        if (loginData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = loginData.getHeader().getCode();
            if (code == 200) {
                if (loginData.getResponse().getAssignToCart())
                    preferenceManager.putValue(Constants.CART_ID, loginData.getResponse().getQuoteId());
                getActivity().finish();
            } else {
                Utilities.showErrorPopup(getContext(), loginData.getHeader().getMessage());
            }
        }
    }

    private void checkForm() {
        loginBtn.setEnabled(!Utilities.isEmpty(email) && !Utilities.isEmpty(password));
    }

}