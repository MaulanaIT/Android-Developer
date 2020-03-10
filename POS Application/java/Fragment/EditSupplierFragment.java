package com.example.project.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.Activity.MainActivity;
import com.example.project.R;

import java.net.IDN;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EditSupplierFragment extends Fragment {

    EditText
            inputName,
            inputEmail,
            inputTelephone,
            inputFax,
            inputAddress,
            inputTax;

    Spinner
            spinnerStatus;

    String
            name,
            email,
            telephone,
            fax,
            address,
            tax,
            time;

    Button
            btnUpdate;

    int
            status;

    public static int
            ID;

    private SimpleDateFormat
            dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa", Locale.getDefault());

    private Calendar
            calendar = Calendar.getInstance();

    private Date
            currentDate = calendar.getTime();

    private RequestQueue
            requestQueue;

    StringRequest
            stringRequest;

    ProgressBar
            progressBar;

    public EditSupplierFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_supplier, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getContext()).setMyTitle("Edit Supplier");

        inputName = getView().findViewById(R.id.edit_name);
        inputEmail = getView().findViewById(R.id.edit_email);
        inputTelephone = getView().findViewById(R.id.edit_telephone);
        inputFax = getView().findViewById(R.id.edit_fax);
        inputAddress = getView().findViewById(R.id.edit_address);
        inputTax = getView().findViewById(R.id.edit_tax);

        spinnerStatus = getView().findViewById(R.id.edit_status);

        btnUpdate = getView().findViewById(R.id.btn_update);

        progressBar = getView().findViewById(R.id.progressBar);

        ((MainActivity) getContext()).getMySqlByID("Data Supplier", null, null, null,
                ID, inputName, inputEmail, inputTelephone, inputFax, inputAddress, inputTax, null, null, null,
                null, null, null, spinnerStatus, null, null, null, null);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        btnUpdate.setOnClickListener(updateSupplier);
    }

    private View.OnClickListener updateSupplier = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dataSupplier();
        }
    };

    private void dataSupplier() {
        name = inputName.getText().toString().trim();
        email = inputEmail.getText().toString().trim();
        telephone = inputTelephone.getText().toString().trim();
        fax = inputFax.getText().toString().trim();
        address = inputAddress.getText().toString().trim();
        tax = inputTax.getText().toString().trim();
        status = spinnerStatus.getSelectedItemPosition();

        time = dateFormat.format(currentDate);

        progressBar.setVisibility(View.VISIBLE);
        btnUpdate.setVisibility(View.GONE);

        stringRequest = new StringRequest(Request.Method.POST, ((MainActivity) getContext()).setURLUpdateData("Supplier"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("Success")) {
                            progressBar.setVisibility(View.GONE);
                            btnUpdate.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Update Succesful", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().popBackStack();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            btnUpdate.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Update Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        btnUpdate.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", String.valueOf(ID));
                params.put("Name", name);
                params.put("Email", email);
                params.put("Telephone", telephone);
                params.put("Fax", fax);
                params.put("Address", address);
                params.put("Tax", tax);
                params.put("Status", String.valueOf(status));
                params.put("Time", time);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
