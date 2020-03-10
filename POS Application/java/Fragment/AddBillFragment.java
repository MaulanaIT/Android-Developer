package com.example.project.Fragment;


import android.app.ProgressDialog;
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

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AddBillFragment extends Fragment {

    Spinner
            spinnerCustomer;

    EditText
            inputRefNumber;

    Button
            btnSubmit;

    RequestQueue
            requestQueue;

    StringRequest
            stringRequest;

    ProgressBar
            progressBar;

    SimpleDateFormat
            dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa", Locale.getDefault());

    Calendar
            calendar = Calendar.getInstance();

    Date
            currentDate = calendar.getTime();

    String
            refNumber,
            customer,
            outletID,
            totalPrice,
            discount,
            tax,
            totalPayable,
            totalItem,
            time;

    public AddBillFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hold_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getContext()).setMyTitle("Hold Payment");

        spinnerCustomer = getView().findViewById(R.id.spinner_customer);

        inputRefNumber = getView().findViewById(R.id.input_ref_number);

        btnSubmit = getView().findViewById(R.id.btn_submit);

        progressBar = getView().findViewById(R.id.progressBar);

        ((MainActivity) getContext()).setSpinner(((MainActivity) getContext()).setURLReadData("Customer"), "customer", spinnerCustomer);

        btnSubmit.setOnClickListener(addBill);
    }

    private View.OnClickListener addBill = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                progressBar.setVisibility(View.VISIBLE);
                btnSubmit.setVisibility(View.GONE);
                dataBill();
                TimeUnit.SECONDS.sleep(1);
                dataOrder();
                TimeUnit.SECONDS.sleep(1);
                getActivity().getSupportFragmentManager().popBackStack();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void dataBill() {
        refNumber = inputRefNumber.getText().toString().trim();
        customer = spinnerCustomer.getSelectedItem().toString().trim();
        outletID = String.valueOf(PosCashierFragment.ID);
        totalPrice = String.valueOf(PosCashierFragment.totalPrice);
        discount = String.valueOf(PosCashierFragment.discount);
        tax = String.valueOf(PosCashierFragment.tax);
        totalPayable = String.valueOf(PosCashierFragment.totalPayable);
        totalItem = String.valueOf(PosCashierFragment.totalItem);
        time = dateFormat.format(currentDate);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        stringRequest = new StringRequest(Request.Method.POST, ((MainActivity) getContext()).setURLAddData("Bill"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("Success")) {
                            progressBar.setVisibility(View.GONE);
                            btnSubmit.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Insert Succesful", Toast.LENGTH_SHORT).show();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            btnSubmit.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Insert Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("RefNumber", refNumber);
                params.put("OutletID", outletID);
                params.put("Customer", customer);
                params.put("Price", totalPrice);
                params.put("Discount", discount);
                params.put("Tax", tax);
                params.put("Payable", totalPayable);
                params.put("Item", totalItem);
                params.put("Time", time);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    public void dataOrder() {
        refNumber = inputRefNumber.getText().toString().trim();
        time = dateFormat.format(currentDate);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        stringRequest = new StringRequest(Request.Method.POST, ((MainActivity) getContext()).setURLAddData("Bill Item"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("Success")) {
                            progressBar.setVisibility(View.GONE);
                            btnSubmit.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Insert Succesful", Toast.LENGTH_SHORT).show();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            btnSubmit.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Insert Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("RefNumber", refNumber);
                params.put("Time", time);
                params.put("Size", String.valueOf(PosCashierFragment.productName.size()));
                for (int i = 0; i < PosCashierFragment.productName.size(); i++) {
                    params.put("Code["+i+"]", PosCashierFragment.productCode.get(i));
                    params.put("Product["+i+"]", PosCashierFragment.productName.get(i));
                    params.put("Quantity["+i+"]", PosCashierFragment.productQuantity.get(i));
                }
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
