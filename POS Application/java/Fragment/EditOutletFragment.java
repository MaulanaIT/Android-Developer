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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EditOutletFragment extends Fragment {

    String
            name,
            contact,
            address,
            receipt,
            status,
            time;

    public static int
            ID;

    EditText
            inputName,
            inputContactNumber,
            inputAddress,
            inputReceipt;

    Button
            btnUpdate;

    Spinner
            spinnerStatus;

    ProgressBar
            progressBar;

    SimpleDateFormat
            dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa", Locale.getDefault());

    Calendar
            calendar = Calendar.getInstance();

    Date
            currentDate = calendar.getTime();

    private RequestQueue
            requestQueue;

    StringRequest
            stringRequest;

    public EditOutletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_outlet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("Edit Outlet");

        inputName = getView().findViewById(R.id.edit_outlet_name);
        inputContactNumber = getView().findViewById(R.id.edit_outlet_contact_number);
        inputAddress = getView().findViewById(R.id.edit_outlet_address);
        inputReceipt = getView().findViewById(R.id.edit_outlet_receipt_footer);

        progressBar = getView().findViewById(R.id.progressBar);

        spinnerStatus = getView().findViewById(R.id.edit_status);

        btnUpdate = getView().findViewById(R.id.btn_update);

        ((MainActivity) getContext()).getMySqlByID("Data Outlet", null, null, null,
                ID, inputName, inputContactNumber, inputAddress, inputReceipt, null, null, null, null,
                null, null, null, null, spinnerStatus, null, null, null, null);

        btnUpdate.setOnClickListener(updateOutlet);
    }

    private View.OnClickListener updateOutlet = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dataOutlet();
        }
    };

    private void dataOutlet() {
        name = inputName.getText().toString().trim();
        contact = inputContactNumber.getText().toString().trim();
        address = inputAddress.getText().toString().trim();
        receipt = inputReceipt.getText().toString().trim();
        status = spinnerStatus.getSelectedItem().toString().trim();

        final int statusCode;
        if (status.equals("Not Active")) {
            statusCode = 0;
        } else {
            statusCode = 1;
        }

        time = dateFormat.format(currentDate);

        progressBar.setVisibility(View.VISIBLE);
        btnUpdate.setVisibility(View.GONE);

        stringRequest = new StringRequest(Request.Method.POST, ((MainActivity) getContext()).setURLUpdateData("Outlet"),
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
                params.put("Contact", contact);
                params.put("Address", address);
                params.put("Footer", receipt);
                params.put("Status", String.valueOf(statusCode));
                params.put("Time", time);
                return params;
            }
        };

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        requestQueue.add(stringRequest);
    }
}
