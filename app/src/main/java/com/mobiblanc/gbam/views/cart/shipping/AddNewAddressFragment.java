package com.mobiblanc.gbam.views.cart.shipping;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.mobiblanc.gbam.R;
import com.mobiblanc.gbam.databinding.FragmentAddNewAddressBinding;
import com.mobiblanc.gbam.datamanager.sharedpref.PreferenceManager;
import com.mobiblanc.gbam.models.shipping.address.Address;
import com.mobiblanc.gbam.models.shipping.address.AddressData;
import com.mobiblanc.gbam.models.shipping.cities.CitiesData;
import com.mobiblanc.gbam.models.shipping.cities.CitiesListData;
import com.mobiblanc.gbam.models.shipping.cities.DistrictsListData;
import com.mobiblanc.gbam.models.shipping.cities.WayListData;
import com.mobiblanc.gbam.utilities.Connectivity;
import com.mobiblanc.gbam.utilities.Constants;
import com.mobiblanc.gbam.utilities.NumericKeyBoardTransformationMethod;
import com.mobiblanc.gbam.utilities.Utilities;
import com.mobiblanc.gbam.viewmodels.CartVM;
import com.mobiblanc.gbam.views.cart.CartActivity;
import com.mobiblanc.gbam.views.main.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddNewAddressFragment extends Fragment {

    private FragmentAddNewAddressBinding fragmentBinding;
    private Connectivity connectivity;
    private CartVM cartVM;
    private PreferenceManager preferenceManager;
    private String addressType = "type business";
    private Address address;
    private Boolean isUpdate = false;
    private String selectedCity;
    private String selectedDistrict = "";
    //private String selectedWay = "";

    public AddNewAddressFragment() {
        // Required empty public constructor
    }

    public static AddNewAddressFragment newInstance(Address address) {
        AddNewAddressFragment fragment = new AddNewAddressFragment();
        Bundle args = new Bundle();
        args.putSerializable("address", address);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cartVM = ViewModelProviders.of(this).get(CartVM.class);
        connectivity = new Connectivity(requireContext(), this);
        cartVM.getAddNewAddressLiveData().observe(this, this::handleAddAddressData);
        cartVM.getUpdateAddressLiveData().observe(this, this::handleUpdateAddressData);
        //cartVM.getCitiesListLiveData().observe(this, this::handleGetCitiesData);
        //cartVM.getDistrictsLiveData().observe(this, this::handleGetDistrictsData);
        //cartVM.getWayListData().observe(this, this::handleGetWaysData);
        cartVM.getCitiesLiveData().observe(this , this::handleCitiesData);

        preferenceManager = new PreferenceManager.Builder(requireContext(), Context.MODE_PRIVATE)
                .name(Constants.SHARED_PREFS_NAME)
                .build();

        if (getArguments() != null)
            address = (Address) getArguments().getSerializable("address");

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void handleCitiesData(CitiesData citiesData) {

        fragmentBinding.loader.setVisibility(View.GONE);
        if (citiesData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = citiesData.getHeader().getCode();
            if (code == 200) {
                List<String> citiesList = new ArrayList<>();
                for (int i = 0 ; i<citiesData.getResponse().getCities().size();i++){
                    citiesList.add(citiesData.getResponse().getCities().get(i).getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter(requireContext(), R.layout.custom_dropdown_item_layout, citiesList);
                Log.d("TAG", "handleGetCitiesData: "+citiesData.getResponse().getCities());
         /*     fragmentBinding.city.setAdapter(adapter);
                fragmentBinding.city.showDropDown();
                fragmentBinding.city.setOnItemClickListener((parent, view, position, id) -> {
                    selectedCity = citiesData.getCities().get(position);
                });*/

                fragmentBinding.city.setAdapter(adapter);
                fragmentBinding.city.setOnTouchListener((v, event) -> {
                    Utilities.hideSoftKeyboard(requireContext(), requireView());
                    fragmentBinding.city.showDropDown();
                    return false;
                });
                fragmentBinding.city.setOnItemClickListener((parent, view, position, id) -> {
                    selectedCity = citiesData.getResponse().getCities().get(position).getName();
                });

            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), citiesData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    preferenceManager.clearValue(Constants.CART_ID);
                    preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                    requireActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else {
                Utilities.showErrorPopup(getContext(), citiesData.getHeader().getMessage());
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBinding = FragmentAddNewAddressBinding.inflate(inflater, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((CartActivity) requireActivity()).showUpdateBtn(View.GONE);
        fragmentBinding.phoneNumber.setText(preferenceManager.getValue(Constants.PHONE_NUMBER, null));
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            cartVM.getCities1(preferenceManager.getValue(Constants.TOKEN, ""));
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
        init();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void init() {
        fragmentBinding.phoneNumber.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        fragmentBinding.phoneNumber.setTransformationMethod(new NumericKeyBoardTransformationMethod());

        fragmentBinding.postalCode.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        fragmentBinding.postalCode.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        fragmentBinding.phoneNumber.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        fragmentBinding.phoneNumber.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        fragmentBinding.streetNumber.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        fragmentBinding.streetNumber.setTransformationMethod(new NumericKeyBoardTransformationMethod());

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!String.valueOf(fragmentBinding.phoneNumber.getText()).contains(" ")) {
                    int maxLength = 10;
                    InputFilter[] fArray = new InputFilter[1];
                    fArray[0] = new InputFilter.LengthFilter(maxLength);
                    fragmentBinding.phoneNumber.setFilters(fArray);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkForm();
            }
        };

        TextWatcher cityAutoCompleteWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s != null && String.valueOf(s).length() > 1) {
                    getCities(String.valueOf(s));
                } else {
                    fragmentBinding.city.dismissDropDown();
                }
            }
        };

        TextWatcher districtAutoCompleteWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
               /* if (s != null && String.valueOf(s).length() > 1 && selectedCity != null) {
                    getDistricts(String.valueOf(s));
                } else {
                    fragmentBinding.district.dismissDropDown();
                }*/
            }
        };

        TextWatcher wayAutoCompleteWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                /*if (s != null && String.valueOf(s).length() > 1 && selectedCity != null) {
                    getWays(String.valueOf(s));
                } else {
                    fragmentBinding.way.dismissDropDown();
                }*/
            }
        };

        fragmentBinding.container.setOnClickListener(v -> Utilities.hideSoftKeyboard(getContext(), getView()));

        fragmentBinding.companyChoice.setOnClickListener(v -> {
            fragmentBinding.companyChoice.setBackground(requireContext().getDrawable(R.drawable.selected_payment_item_background));
            fragmentBinding.particularChoice.setBackground(requireContext().getDrawable(R.drawable.unselected_payment_item_background));
            //fragmentBinding.cni.setVisibility(View.GONE);
            fragmentBinding.ice.setVisibility(View.VISIBLE);
            fragmentBinding.fiscalId.setVisibility(View.VISIBLE);
            addressType = "type business";
        });

        fragmentBinding.particularChoice.setOnClickListener(v -> {
            fragmentBinding.particularChoice.setBackground(requireContext().getDrawable(R.drawable.selected_payment_item_background));
            fragmentBinding.companyChoice.setBackground(requireContext().getDrawable(R.drawable.unselected_payment_item_background));
            //fragmentBinding.cni.setVisibility(View.VISIBLE);
            fragmentBinding.ice.setVisibility(View.GONE);
            fragmentBinding.fiscalId.setVisibility(View.GONE);
            addressType = "particular";
        });

        fragmentBinding.saveBtn.setOnClickListener(v -> {
            if (isUpdate)
                updateAddress();
            else
                addAddress();
        });

        fragmentBinding.addressName.addTextChangedListener(textWatcher);
        fragmentBinding.streetNumber.addTextChangedListener(textWatcher);
        fragmentBinding.city.addTextChangedListener(textWatcher);
        fragmentBinding.district.addTextChangedListener(textWatcher);
        //fragmentBinding.way.addTextChangedListener(textWatcher);
        fragmentBinding.postalCode.addTextChangedListener(textWatcher);
        fragmentBinding.phoneNumber.addTextChangedListener(textWatcher);
        fragmentBinding.ice.addTextChangedListener(textWatcher);
        fragmentBinding.fiscalId.addTextChangedListener(textWatcher);
        //fragmentBinding.cni.addTextChangedListener(textWatcher);

        if (address != null) {
            isUpdate = true;
            selectedCity = address.getCity();

            selectedDistrict = fragmentBinding.district.getText().toString();
            //selectedWay = fragmentBinding.way.getText().toString();

            fragmentBinding.title.setText(R.string.update_address_title);
            if (address.getType().equalsIgnoreCase("type business"))
                fragmentBinding.companyChoice.performClick();
            else
                fragmentBinding.particularChoice.performClick();

            fragmentBinding.addressName.setText(address.getName());
            fragmentBinding.streetNumber.setText(address.getStreetNumber());
            fragmentBinding.city.setText(address.getCity());
            fragmentBinding.postalCode.setText(address.getPostcode());
            fragmentBinding.phoneNumber.setText(address.getTelephone());
            fragmentBinding.ice.setText(address.getIce());
            fragmentBinding.fiscalId.setText(address.getTaxIdentification());
            //fragmentBinding.cni.setText(address.getCni());
            fragmentBinding.district.setText(selectedDistrict);
            //fragmentBinding.way.setText(selectedWay);

        }

        //fragmentBinding.way.addTextChangedListener(wayAutoCompleteWatcher);
        fragmentBinding.district.addTextChangedListener(districtAutoCompleteWatcher);
        fragmentBinding.city.addTextChangedListener(cityAutoCompleteWatcher);
    }

    private void checkForm() {
        switch (addressType) {
            case "type business":
                fragmentBinding.saveBtn.setEnabled(!Utilities.isEmpty(fragmentBinding.addressName) && !Utilities.isEmpty(fragmentBinding.streetNumber)
                        && !Utilities.isEmpty(fragmentBinding.city) && !Utilities.isEmpty(fragmentBinding.postalCode)
                        && !Utilities.isEmpty(fragmentBinding.phoneNumber) && !Utilities.isEmpty(fragmentBinding.ice)
                        && !Utilities.isEmpty(fragmentBinding.fiscalId)
                        && selectedCity.equals(String.valueOf(fragmentBinding.city.getText()))
                        && !Utilities.isEmpty(fragmentBinding.district)
                        && fragmentBinding.phoneNumber.length()>9
                                //selectedDistrict.equals(String.valueOf(fragmentBinding.district.getText()))
                        //&& selectedWay.equals(String.valueOf(fragmentBinding.way.getText()))
                );
                break;
            case "particular":
                fragmentBinding.saveBtn.setEnabled(!Utilities.isEmpty(fragmentBinding.addressName) && !Utilities.isEmpty(fragmentBinding.streetNumber)
                        && !Utilities.isEmpty(fragmentBinding.city) && !Utilities.isEmpty(fragmentBinding.postalCode)
                        && !Utilities.isEmpty(fragmentBinding.phoneNumber)
                        && selectedCity.equals(String.valueOf(fragmentBinding.city.getText()))
                        && !Utilities.isEmpty(fragmentBinding.district) && fragmentBinding.phoneNumber.length()>9
                        //selectedDistrict.equals(String.valueOf(fragmentBinding.district.getText()))
                        //&& selectedWay.equals(String.valueOf(fragmentBinding.way.getText())));
                );
                break;
        }
    }

    private void addAddress() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            cartVM.addNewAddress(preferenceManager.getValue(Constants.TOKEN, null),
                    fragmentBinding.addressName.getText().toString(),
                    fragmentBinding.streetNumber.getText().toString(),
                    fragmentBinding.district.getText().toString(),
                    selectedCity,
                    fragmentBinding.postalCode.getText().toString(),
                    fragmentBinding.phoneNumber.getText().toString(),
                    fragmentBinding.ice.getText().toString(),
                    fragmentBinding.fiscalId.getText().toString(),
                    //fragmentBinding.cni.getText().toString(),
                    "",
                    addressType
            );

            Log.d("TAG", "addressName: "+fragmentBinding.addressName.getText().toString());
            Log.d("TAG", "streetNumber: "+fragmentBinding.streetNumber.getText().toString());
            Log.d("TAG", "selectedDistrict: "+selectedDistrict);
            //Log.d("TAG", "selectedWay: "+selectedWay);
            Log.d("TAG", "selectedCity: "+selectedCity);
            Log.d("TAG", "postalCode: "+fragmentBinding.postalCode.getText().toString());
            Log.d("TAG", "ice: "+fragmentBinding.ice.getText().toString());
            Log.d("TAG", "addressType: "+addressType);

        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleAddAddressData(AddressData addressData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (addressData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = addressData.getHeader().getCode();
            if (code == 200) {
                Intent intent = new Intent();
                intent.putExtra("addresses", addressData.getResponse().getAddresses());
                try {
                    Objects.requireNonNull(getTargetFragment()).onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                requireActivity().onBackPressed();
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), addressData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    preferenceManager.clearValue(Constants.CART_ID);
                    preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                    requireActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else {
                Utilities.showErrorPopup(getContext(), addressData.getHeader().getMessage());
            }
        }
    }

    private void updateAddress() {
        if (connectivity.isConnected()) {
            fragmentBinding.loader.setVisibility(View.VISIBLE);
            cartVM.updateAddress(preferenceManager.getValue(Constants.TOKEN, null),
                    address.getId(),
                    fragmentBinding.addressName.getText().toString(),
                    fragmentBinding.streetNumber.getText().toString(),
                    selectedDistrict,
                    selectedCity,
                    fragmentBinding.postalCode.getText().toString(),
                    fragmentBinding.phoneNumber.getText().toString(),
                    fragmentBinding.ice.getText().toString(),
                    fragmentBinding.fiscalId.getText().toString(),
                    "",
                    //fragmentBinding.cni.getText().toString(),
                    addressType
            );
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void handleUpdateAddressData(AddressData addressData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (addressData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = addressData.getHeader().getCode();
            if (code == 200) {
                Intent intent = new Intent();
                intent.putExtra("addresses", addressData.getResponse().getAddresses());
                try {
                    Objects.requireNonNull(getTargetFragment()).onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                requireActivity().onBackPressed();
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), addressData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    preferenceManager.clearValue(Constants.CART_ID);
                    preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                    requireActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else {
                Utilities.showErrorPopup(getContext(), addressData.getHeader().getMessage());
            }
        }
    }

    private void getCities(String filter) {
        if (connectivity.isConnected()) {
            cartVM.getCities(filter);
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }



    private void getDistricts(String filter) {
        if (connectivity.isConnected()) {
            if (fragmentBinding.city.getText().toString().trim().length() == 0) {
                Utilities.showErrorPopup(getContext(), getString(R.string.select_city));
                return;
            }
            cartVM.getDistricts(selectedCity, filter);
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    private void getWays(String filter) {
        if (connectivity.isConnected()) {
            if (fragmentBinding.city.getText().toString().trim().length() == 0) {
                Utilities.showErrorPopup(getContext(), getString(R.string.select_city));
                return;
            }
            cartVM.getWays(selectedCity, filter);
        } else
            Utilities.showErrorPopup(getContext(), getString(R.string.no_internet_msg));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void handleGetCitiesData(CitiesListData citiesData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (citiesData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = citiesData.getHeader().getCode();
            if (code == 200) {
               ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.custom_dropdown_item_layout, citiesData.getCities());
                Log.d("TAG", "handleGetCitiesData: "+citiesData.getCities());
         /*     fragmentBinding.city.setAdapter(adapter);
                fragmentBinding.city.showDropDown();
                fragmentBinding.city.setOnItemClickListener((parent, view, position, id) -> {
                    selectedCity = citiesData.getCities().get(position);
                });*/

                fragmentBinding.city.setAdapter(adapter);
                fragmentBinding.city.setOnTouchListener((v, event) -> {
                    Utilities.hideSoftKeyboard(requireContext(), requireView());
                    fragmentBinding.city.showDropDown();
                    return false;
                });
                fragmentBinding.city.setOnItemClickListener((parent, view, position, id) -> {
                    selectedCity = citiesData.getCities().get(position);
                });

            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), citiesData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    preferenceManager.clearValue(Constants.CART_ID);
                    preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                    requireActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else {
                Utilities.showErrorPopup(getContext(), citiesData.getHeader().getMessage());
            }
        }
    }

  /*  private void handleGetDistrictsData(DistrictsListData districtsData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (districtsData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = districtsData.getHeader().getCode();
            if (code == 200) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.custom_dropdown_item_layout, districtsData.getDistricts());
                fragmentBinding.district.setAdapter(adapter);
                fragmentBinding.district.showDropDown();
                fragmentBinding.district.setOnItemClickListener((parent, view, position, id) -> {
                    selectedDistrict = districtsData.getDistricts().get(position);
                });
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), districtsData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    preferenceManager.clearValue(Constants.CART_ID);
                    preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                    requireActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else {
                Utilities.showErrorPopup(getContext(), districtsData.getHeader().getMessage());
            }
        }

    }

    private void handleGetWaysData(WayListData wayListData) {
        fragmentBinding.loader.setVisibility(View.GONE);
        if (wayListData == null) {
            Utilities.showErrorPopup(getContext(), getString(R.string.generic_error));
        } else {
            int code = wayListData.getHeader().getCode();
            if (code == 200) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.custom_dropdown_item_layout, wayListData.getWays());
                fragmentBinding.way.setAdapter(adapter);
                fragmentBinding.way.showDropDown();
                fragmentBinding.way.setOnItemClickListener((parent, view, position, id) -> {
                    selectedWay = wayListData.getWays().get(position);
                });
            } else if (code == 403) {
                Utilities.showErrorPopupWithClick(getContext(), wayListData.getHeader().getMessage(), view -> {
                    preferenceManager.clearValue(Constants.TOKEN);
                    preferenceManager.clearValue(Constants.CART_ID);
                    preferenceManager.clearValue(Constants.NB_ITEMS_IN_CART);
                    requireActivity().finishAffinity();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                });
            } else {
                Utilities.showErrorPopup(getContext(), wayListData.getHeader().getMessage());
            }
        }
    }*/
}