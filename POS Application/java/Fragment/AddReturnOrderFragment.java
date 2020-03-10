package com.example.project.Fragment;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.Activity.MainActivity;
import com.example.project.Adapter.EditInventoryListAdapter;
import com.example.project.Adapter.ReturnOrderListAdapter;
import com.example.project.Model.ListReturnOrder;
import com.example.project.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AddReturnOrderFragment extends Fragment {

    List<ListReturnOrder>
            listReturnOrders;

    Button
            btnSubmit,
            btnAddToReturn;

    EditText
            inputCustomers,
            inputOutlets,
            inputRemark,
            inputRefundAmount,
            inputRefundTax,
            inputRefundTotal,
            inputRefundBy,
            inputMethod,
            inputSeach;

    TextView
            textTax;

    Spinner
            customersSpinner,
            outletsSpinner,
            refundBySpinner,
            refundMethodSpinner,
            searchSpinner;

    double
            refundTax,
            refundTotal,
            totalItem;

    String
            PREFS_NAME = "MyPrefsFile",
            customer,
            outlet,
            remark,
            refundAmount,
            refundBy,
            refundMethod,
            time;

    RecyclerView
            recyclerView;

    RecyclerView.LayoutManager
            layoutManager;

    RecyclerView.Adapter
            adapter;

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

    DecimalFormat decimalFormat = new DecimalFormat("#.##");


    public AddReturnOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_return_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getContext()).setMyTitle("Return Order");

        SharedPreferences systemSetting = getActivity().getSharedPreferences(PREFS_NAME, 0);

        btnSubmit = getView().findViewById(R.id.btn_submit);
        btnAddToReturn = getView().findViewById(R.id.btn_add_item);

        inputCustomers = getView().findViewById(R.id.input_customers);
        inputOutlets = getView().findViewById(R.id.input_outlets);
        inputRemark = getView().findViewById(R.id.input_remark);
        inputRefundAmount = getView().findViewById(R.id.input_refund_amount);
        inputRefundTax = getView().findViewById(R.id.input_refund_tax);
        inputRefundTotal = getView().findViewById(R.id.input_refund_grand_total);
        inputRefundBy = getView().findViewById(R.id.input_refund_by);
        inputMethod = getView().findViewById(R.id.input_refund_method);
        inputSeach = getView().findViewById(R.id.input_search);

        textTax = getView().findViewById(R.id.text_tax);

        customersSpinner = getView().findViewById(R.id.customer_spinner);
        outletsSpinner = getView().findViewById(R.id.outlets_spinner);
        refundBySpinner = getView().findViewById(R.id.refund_by_spinner);
        refundMethodSpinner = getView().findViewById(R.id.refund_method_spinner);
        searchSpinner = getView().findViewById(R.id.search_spinner);

        progressBar = getView().findViewById(R.id.progressBar);

        recyclerView = getView().findViewById(R.id.recycler_view);

        listReturnOrders = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        ReturnOrderListAdapter.productID.clear();
        ReturnOrderListAdapter.productName.clear();
        ReturnOrderListAdapter.productCode.clear();
        ReturnOrderListAdapter.productQuantity.clear();
        ReturnOrderListAdapter.productCondition.clear();

        ((MainActivity) getContext()).setSpinner(((MainActivity) getContext()).setURLReadData("Outlet"), "outlet", outletsSpinner);
        ((MainActivity) getContext()).setSpinner(((MainActivity) getContext()).setURLReadData("Customer"), "customer", customersSpinner);
        ((MainActivity) getContext()).setSpinner(((MainActivity) getContext()).setURLReadData("Payment Method"), "payment", refundBySpinner);
        ((MainActivity) getContext()).setSpinner(((MainActivity) getContext()).setURLReadData("Product"), "product", searchSpinner);

        inputRefundAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @SuppressLint("DefaultLocale")
            @Override
            public void afterTextChanged(Editable s) {
                if (inputRefundAmount.getText().toString().equals("")) {
                    inputRefundTax.setText("0");
                    inputRefundTotal.setText("0");
                } else {
                    refundTax = (Double.valueOf(inputRefundAmount.getText().toString()) * 7) / 100;
                    refundTotal = Double.valueOf(inputRefundAmount.getText().toString()) - refundTax;

                    inputRefundTax.setText(String.format("%.2f", (Double) refundTax));
                    inputRefundTotal.setText(String.format("%.2f", (Double) refundTotal));
                }
            }
        });

        textTax.setText("Refund Tax (" + Double.valueOf(systemSetting.getString("Tax", "0")) + "%)");

        btnAddToReturn.setOnClickListener(addReturnOrderList);
        btnSubmit.setOnClickListener(addReturnOrder);
    }

    private View.OnClickListener addReturnOrderList = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MainActivity) getContext()).getMySqlReturnOrder(listReturnOrders, adapter, recyclerView, searchSpinner);
        }
    };

    private View.OnClickListener addReturnOrder = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                dataReturnOrder();
                TimeUnit.SECONDS.sleep(1);
                dataReturnOrderItem();
            } catch (Exception e) {
                e.printStackTrace();
            }
            getActivity().getSupportFragmentManager().popBackStack();
        }
    };

    private void dataReturnOrder() {
        customer = customersSpinner.getSelectedItem().toString().trim();
        outlet = outletsSpinner.getSelectedItem().toString().trim().trim();
        remark = inputRemark.getText().toString().trim();
        refundAmount = inputRefundAmount.getText().toString().trim();
        refundTax = Double.valueOf(inputRefundTax.getText().toString().trim());
        refundTotal = Double.valueOf(inputRefundTotal.getText().toString().trim());
        refundBy = refundBySpinner.getSelectedItem().toString().trim();
        refundMethod = String.valueOf(refundMethodSpinner.getSelectedItemPosition());
        totalItem = ReturnOrderListAdapter.productID.size();
        time = dateFormat.format(currentDate);

        progressBar.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.GONE);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        stringRequest = new StringRequest(Request.Method.POST, ((MainActivity) getContext()).setURLAddData("Return Order"),
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
                },
                new Response.ErrorListener() {
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
                params.put("Customer", customer);
                params.put("Outlet", outlet);
                params.put("Remark", remark);
                params.put("RefundAmount", refundAmount);
                params.put("RefundTax", String.valueOf(refundTax));
                params.put("RefundTotal", String.valueOf(refundTotal));
                params.put("RefundBy", refundBy);
                params.put("RefundMethod", refundMethod);
                params.put("TotalItem", String.valueOf(ReturnOrderListAdapter.productID.size()));
                params.put("Time", time);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void dataReturnOrderItem() {
        final int
                size = ReturnOrderListAdapter.productID.size();

        progressBar.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.GONE);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        stringRequest = new StringRequest(Request.Method.POST, ((MainActivity) getContext()).setURLAddData("Return Order Item"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("Success")) {
                            progressBar.setVisibility(View.GONE);
                            btnSubmit.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Insert Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            btnSubmit.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Insert Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
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
                params.put("Size", String.valueOf(size));
                for (int i = 0; i < size; i++) {
                    params.put("Code["+i+"]", ReturnOrderListAdapter.productCode.get(i));
                    params.put("Product["+i+"]", ReturnOrderListAdapter.productName.get(i));
                    params.put("Condition["+i+"]", ReturnOrderListAdapter.productCondition.get(i));
                    params.put("Quantity["+i+"]", ReturnOrderListAdapter.productQuantity.get(i));
                }
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
