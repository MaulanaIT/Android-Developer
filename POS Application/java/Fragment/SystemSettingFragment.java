package com.example.project.Fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.project.Activity.MainActivity;
import com.example.project.R;

import static android.app.Activity.RESULT_OK;

public class SystemSettingFragment extends Fragment {

    Button
            btnBrowse,
            btnUpdateSetting;

    EditText
            inputSiteName,
            inputTax,
            inputTimeZone,
            inputCurrency,
            inputPagination,
            inputDateFormat,
            inputDisplayProduct,
            inputDisplayKeyboard,
            inputDefaultCustomer,
            inputPathLogo;

    ImageView
            imgLogo;

    Spinner
            timezoneSpinner,
            currencySpinner,
            paginationSpinner,
            dateFormatSpinner,
            displayProductSpinner,
            displayKeyboardSpinner,
            defaultCustomerSpinner;

    public Uri
            imgUri;

    String
            PREFS_NAME = "MyPrefsFile";

    SharedPreferences
            systemSetting;

    public SystemSettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_system_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("System Setting");

        systemSetting = getActivity().getSharedPreferences(PREFS_NAME, 0);

        btnBrowse = getView().findViewById(R.id.btn_browse_logo);
        btnUpdateSetting = getView().findViewById(R.id.btn_update_system_setting);

        inputSiteName = getView().findViewById(R.id.input_site_name);
        inputTax = getView().findViewById(R.id.input_tax);
        inputTimeZone = getView().findViewById(R.id.input_system_timezone);
        inputCurrency = getView().findViewById(R.id.input_currency);
        inputPagination = getView().findViewById(R.id.input_Pagination);
        inputDateFormat = getView().findViewById(R.id.input_date_format);
        inputDisplayProduct = getView().findViewById(R.id.input_pos_display_product);
        inputDisplayKeyboard = getView().findViewById(R.id.input_pos_display_keyboard);
        inputDefaultCustomer = getView().findViewById(R.id.input_pos_default_customer);
        inputPathLogo = getView().findViewById(R.id.input_path_logo);

        imgLogo = getView().findViewById(R.id.img_logo);

        timezoneSpinner = getView().findViewById(R.id.system_timezone_spinner);
        currencySpinner = getView().findViewById(R.id.currency_spinner);
        paginationSpinner = getView().findViewById(R.id.pagination_spinner);
        dateFormatSpinner = getView().findViewById(R.id.date_format_spinner);
        displayProductSpinner = getView().findViewById(R.id.pos_display_product_spinner);
        displayKeyboardSpinner = getView().findViewById(R.id.pos_display_keyboard_spinner);
        defaultCustomerSpinner = getView().findViewById(R.id.pos_default_customer_spinner);

        inputTax.setText(systemSetting.getString("Tax", inputTax.getText().toString()));

        btnBrowse.setOnClickListener(filePicker);
        btnUpdateSetting.setOnClickListener(updateSystemSetting);
    }

    private View.OnClickListener updateSystemSetting = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences.Editor editor = systemSetting.edit();
            editor.putString("Tax", inputTax.getText().toString());
            editor.commit();
            getActivity().getSupportFragmentManager().popBackStack();
        }
    };

    private View.OnClickListener filePicker = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 1);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();

            imgLogo.setImageURI(imgUri);
        }
    }
}
