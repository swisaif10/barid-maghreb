package com.mobiblanc.gbam.views.account.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.FragmentContactBinding;
import com.mobiblanc.gbam.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.gbam.models.contact.message.SendMessageData;
import com.mobiblanc.gbam.models.contact.objects.MessageObjectsData;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.AccountVM;

import java.util.List;

public class ContactFragment extends Fragment {

    private FragmentContactBinding fragmentBinding;
    private Connectivity connectivity;
    private AccountVM accountVM;
    private PreferenceManager preferenceManager;
    private String selectedObject = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accountVM = ViewModelProviders.of(this).get(AccountVM.class);

        accountVM.getMessageObjectsListLiveData().observe(this, this::handleMessageObjectsListData);
        accountVM.getSendMessageLiveData().observe(this, this::handleSendContactMessageData);

        connectivity = new Connectivity(requireContext(), this);

        preferenceManager = new PreferenceManager.Builder(requireContext(), Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentContactBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMessageObjects();
    }


    private void getMessageObjects() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            accountVM.getMessageObjects();
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleMessageObjectsListData(MessageObjectsData messageObjectsData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (messageObjectsData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = messageObjectsData.getHeader().getCode();
            if (code == 200) {
                init(messageObjectsData.getObjectsNames());
            } else {
                Utilities.showErrorPopup(getContext(), messageObjectsData.getHeader().getMessage());
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(List<String> items) {
        fragmentBinding.backBtn.setOnClickListener(v -> requireActivity().onBackPressed());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.custom_dropdown_item_layout, items);
        fragmentBinding.object.setAdapter(adapter);
        fragmentBinding.object.setOnTouchListener((v, event) -> {
            Utilities.hideSoftKeyboard(requireContext(), requireView());
            fragmentBinding.object.showDropDown();
            return false;
        });
        fragmentBinding.object.setOnItemClickListener((parent, view, position, id) -> {
            selectedObject = items.get(position);
        });


        fragmentBinding.subject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fragmentBinding.sendBtn.setEnabled(checkForm());
            }
        });

        fragmentBinding.container.setOnClickListener(v -> Utilities.hideSoftKeyboard(requireContext(), requireView()));
        fragmentBinding.sendBtn.setOnClickListener(v -> sendContactMessage());

        fragmentBinding.container.setVisibility(View.VISIBLE);
    }

    private Boolean checkForm() {
        return !selectedObject.equalsIgnoreCase("") && !Utilities.isEmpty(fragmentBinding.subject);
    }

    private void sendContactMessage() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            accountVM.sendContactMessage(preferenceManager.getValue(Constants.TOKEN, ""), selectedObject, fragmentBinding.subject.getText().toString(), fragmentBinding.orderNumber.getText().toString());
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleSendContactMessageData(SendMessageData sendMessageData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (sendMessageData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = sendMessageData.getHeader().getCode();
            if (code == 200) {
                Utilities.showErrorPopupWithClick(getContext(), sendMessageData.getHeader().getMessage(), v -> requireActivity().onBackPressed());
            } else {
                Utilities.showErrorPopup(getContext(), sendMessageData.getHeader().getMessage());
            }
        }
    }
}