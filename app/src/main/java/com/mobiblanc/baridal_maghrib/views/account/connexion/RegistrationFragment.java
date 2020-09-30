package com.mobiblanc.baridal_maghrib.views.account.connexion;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mobiblanc.baridal_maghrib.R;
import com.mobiblanc.baridal_maghrib.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.baridal_maghrib.models.registration.RegistrationData;
import com.mobiblanc.baridal_maghrib.utilities.Connectivity;
import com.mobiblanc.baridal_maghrib.utilities.Utilities;
import com.mobiblanc.baridal_maghrib.viewmodels.AccountVM;
import com.mobiblanc.baridal_maghrib.views.account.AccountActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifImageView;

public class RegistrationFragment extends Fragment {

    @BindView(R.id.cguBtn)
    TextView cguBtn;
    @BindView(R.id.lastname)
    TextInputEditText lastname;
    @BindView(R.id.firstname)
    TextInputEditText firstname;
    @BindView(R.id.social)
    TextInputEditText social;
    @BindView(R.id.address)
    TextInputEditText address;
    @BindView(R.id.email)
    TextInputEditText email;
    @BindView(R.id.password)
    TextInputEditText password;
    @BindView(R.id.number)
    TextInputEditText number;
    @BindView(R.id.comment)
    TextInputEditText comment;
    @BindView(R.id.confirmPassword)
    TextInputEditText confirmPassword;
    @BindView(R.id.rulesBtn)
    TextView rulesBtn;
    @BindView(R.id.signUpBtn)
    MaterialButton signUpBtn;
    @BindView(R.id.loader)
    GifImageView loader;
    @BindView(R.id.cguCheck)
    CheckBox cguCheck;
    @BindView(R.id.rulesCheck)
    CheckBox rulesCheck;
    @BindView(R.id.emailError)
    TextView emailError;
    @BindView(R.id.passwordError)
    TextView passwordError;
    @BindView(R.id.confirmPasswordError)
    TextView confirmPasswordError;
    private Connectivity connectivity;
    private AccountVM accountVM;
    private PreferenceManager preferenceManager;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountVM = ViewModelProviders.of(this).get(AccountVM.class);
        connectivity = new Connectivity(getContext(), this);
        accountVM.getRegistrationLiveData().observe(this, this::handleRegistrationData);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registeration, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @OnClick({R.id.backBtn, R.id.signUpBtn, R.id.container, R.id.cguBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backBtn:
                getActivity().onBackPressed();
                break;
            case R.id.signUpBtn:
                register();
                //getActivity().finish();
                break;
            case R.id.container:
                Utilities.hideSoftKeyboard(getContext(), getView());
                break;
            case R.id.cguBtn:
                ((AccountActivity) getActivity()).replaceFragment(new CGUFragment());
                break;
        }
    }

    private void init() {
        cguBtn.setText(Html.fromHtml(getActivity().getResources().getString(R.string.cgu_text)));
        rulesBtn.setText(Html.fromHtml(getActivity().getResources().getString(R.string.rules_text)));

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

        lastname.addTextChangedListener(textWatcher);
        firstname.addTextChangedListener(textWatcher);
        social.addTextChangedListener(textWatcher);
        address.addTextChangedListener(textWatcher);
        number.addTextChangedListener(textWatcher);

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkForm();
                if (!Utilities.isEmailValid(s.toString()) && !s.toString().equalsIgnoreCase("")) {
                    emailError.setVisibility(View.VISIBLE);
                    emailError.setText("Format email invalide");
                } else {
                    emailError.setVisibility(View.GONE);
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkForm();
                if (!Utilities.isPasswordValid(s.toString()) && !s.toString().equalsIgnoreCase("")) {
                    passwordError.setText("Mot de passe invalide");
                    passwordError.setVisibility(View.VISIBLE);
                } else {
                    passwordError.setVisibility(View.GONE);
                }
            }
        });

        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkForm();
                if (!s.toString().equalsIgnoreCase(password.getText().toString()) && !s.toString().equalsIgnoreCase("")) {
                    confirmPasswordError.setText("Les mots de passe ne sont pas similaires");
                    confirmPasswordError.setVisibility(View.VISIBLE);
                } else {
                    confirmPasswordError.setVisibility(View.GONE);
                }
            }
        });

        cguCheck.setOnCheckedChangeListener((buttonView, isChecked) -> checkForm());
        rulesCheck.setOnCheckedChangeListener((buttonView, isChecked) -> checkForm());
    }

    private void register() {
        if (connectivity.isConnected()) {
            loader.setVisibility(View.VISIBLE);
            accountVM.register(email.getText().toString().trim(),
                    firstname.getText().toString().trim(),
                    lastname.getText().toString().trim(),
                    password.getText().toString().trim(),
                    number.getText().toString().trim(),
                    social.getText().toString(),
                    address.getText().toString(),
                    comment.getText().toString());
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleRegistrationData(RegistrationData registrationData) {
        loader.setVisibility(View.GONE);
        if (registrationData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = registrationData.getHeader().getCode();
            if (code == 200) {
                Utilities.showErrorPopupWithClick(getContext(), registrationData.getHeader().getMessage(), v -> getActivity().finish());
            } else {
                Utilities.showErrorPopup(getContext(), registrationData.getHeader().getMessage());
            }
        }
    }

    private void checkForm() {
        signUpBtn.setEnabled(!Utilities.isEmpty(firstname) && !Utilities.isEmpty(lastname)
                && !Utilities.isEmpty(social) && !Utilities.isEmpty(address)
                && !Utilities.isEmpty(email) && Utilities.isEmailValid(email.getText().toString())
                && !Utilities.isEmpty(password) && !Utilities.isEmpty(confirmPassword)
                && Utilities.isPasswordValid(password.toString().trim())
                && password.getText().toString().trim().equalsIgnoreCase(confirmPassword.getText().toString().trim())
                && !Utilities.isEmpty(number) && cguCheck.isChecked() && rulesCheck.isChecked());
    }
}