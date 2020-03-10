package com.example.project.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.project.Model.ListPurchaseOrderItem;
import com.example.project.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AddPurchaseOrderFragment extends Fragment {

    List<ListPurchaseOrderItem>
            listPurchaseOrderItems;

    Button
            btnAdd,
            btnSubmit;

    private EditText
            inputNumber,
            inputNote,
            inputQuantity;

    private Spinner
            spinnerOutlet,
            spinnerSupplier, spinnerSearch;

    ProgressBar
            progressBar;

    private RequestQueue
            requestQueue;

    StringRequest
            stringRequest;

    RecyclerView
            recyclerView;

    RecyclerView.LayoutManager
            layoutManager;

    RecyclerView.Adapter
            adapter;

    private SimpleDateFormat
            dateSimple = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()),
            dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa", Locale.getDefault());

    private Calendar
            calendar = Calendar.getInstance();

    private Date
            currentDate = calendar.getTime();

    private String
            number,
            outlet,
            suppliers,
            note,
            code,
            product,
            quantity,
            date,
            time;

    int
            cost;

    ArrayList<String> arrayList = new ArrayList<>();

    public AddPurchaseOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_purchase_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getContext()).setMyTitle("Add Purchase Order");

        btnAdd = getView().findViewById(R.id.btn_add_to_list);
        btnSubmit = getView().findViewById(R.id.btn_submit);

        inputNumber = getView().findViewById(R.id.input_order_number);
        inputNote = getView().findViewById(R.id.input_note);
        inputQuantity = getView().findViewById(R.id.input_quantity_product);

        spinnerSearch = getView().findViewById(R.id.spinner_search_product);
        spinnerOutlet = getView().findViewById(R.id.spinner_outlet);
        spinnerSupplier = getView().findViewById(R.id.spinner_supplier);

        recyclerView = getView().findViewById(R.id.recycler_view);

        progressBar = getView().findViewById(R.id.progressBar);

        inputNumber.setText(number);

        listPurchaseOrderItems = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        btnSubmit.setOnClickListener(addPurchaseOrder);
        btnAdd.setOnClickListener(addOrderList);



        ((MainActivity) getContext()).setSpinner(((MainActivity) getContext()).setURLReadData("Outlet"), "outlet", spinnerOutlet);
        ((MainActivity) getContext()).setSpinner(((MainActivity) getContext()).setURLReadData("Supplier"), "supplier", spinnerSupplier);
        ((MainActivity) getContext()).setSpinner(((MainActivity) getContext()).setURLReadData("Product"), "product", spinnerSearch);

        if (listPurchaseOrderItems.size() > 0) {
            ((MainActivity) getContext()).getMySqlOrderItem(listPurchaseOrderItems, adapter, recyclerView, inputQuantity, spinnerSearch);
        }
    }

    private View.OnClickListener addPurchaseOrder = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                dataPurchaseOrder();
                TimeUnit.SECONDS.sleep(1);
                dataOrder();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private View.OnClickListener addOrderList = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((MainActivity) getContext()).getMySqlOrderItem(listPurchaseOrderItems, adapter, recyclerView, inputQuantity, spinnerSearch);
        }
    };

    private void dataPurchaseOrder() {
        number = inputNumber.getText().toString().trim();
        outlet = spinnerOutlet.getSelectedItem().toString().trim();
        suppliers = spinnerSupplier.getSelectedItem().toString().trim();
        note = inputNote.getText().toString().trim();
        date = dateSimple.format(currentDate);
        time = dateFormat.format(currentDate);

        progressBar.setVisibility(View.VISIBLE);
        btnAdd.setVisibility(View.GONE);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        stringRequest = new StringRequest(Request.Method.POST, ((MainActivity) getContext()).setURLAddData("Purchase Order"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("Success")) {
                            progressBar.setVisibility(View.GONE);
                            btnAdd.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Insert Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            btnAdd.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Insert Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        btnAdd.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Number", number);
                params.put("Outlet", outlet);
                params.put("Supplier", suppliers);
                params.put("Note", note);
                params.put("Date", date);
                params.put("Time", time);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void dataOrder() {
        final int size = listPurchaseOrderItems.size();

        progressBar.setVisibility(View.VISIBLE);
        btnAdd.setVisibility(View.GONE);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        stringRequest = new StringRequest(Request.Method.POST, ((MainActivity) getContext()).setURLAddData("Purchase Order Item"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("Success")) {
                            progressBar.setVisibility(View.GONE);
                            btnAdd.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Insert Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            btnAdd.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Insert Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        btnAdd.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Number", inputNumber.getText().toString().trim());
                params.put("Time", time);
                params.put("Size", String.valueOf(size));
                for (int i = 0; i < listPurchaseOrderItems.size(); i++) {
                    params.put("Code["+i+"]", listPurchaseOrderItems.get(i).getProductCode());
                    params.put("Product["+i+"]", listPurchaseOrderItems.get(i).getProductName());
                    params.put("Quantity["+i+"]", String.valueOf(listPurchaseOrderItems.get(i).getProductQuantity()));
                    params.put("Cost["+i+"]", String.valueOf(listPurchaseOrderItems.get(i).getProductCost()));
                }
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
