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
import com.example.project.Model.ListEditInventory;
import com.example.project.R;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EditInventoryFragment extends Fragment {

    TextView
            inventoryOutletname,
            inventoryProductCode,
            inventoryProductName;

    EditText
            inputInvqntoryQuantity;

    Button
            btnUpdate;

    List<ListEditInventory>
            listEditInventories;

    RecyclerView
            recyclerView;

    RecyclerView.LayoutManager
            layoutManager;

    RecyclerView.Adapter
            adapter;

    public static String
            productCode,
            productName;

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

    String
            time;

    ProgressBar
            progressBar;

    public EditInventoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_inventory, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("Edit Inventory");

        inventoryOutletname = getView().findViewById(R.id.outlets_name);
        inventoryProductCode = getView().findViewById(R.id.inventory_product_code);
        inventoryProductName = getView().findViewById(R.id.inventory_product_name);

        inputInvqntoryQuantity = getView().findViewById(R.id.edit_quantity);

        recyclerView = getView().findViewById(R.id.recycler_view);

        btnUpdate = getView().findViewById(R.id.btn_update);

        progressBar = getView().findViewById(R.id.progressBar);

        inventoryProductCode.setText(productCode);
        inventoryProductName.setText(productName);

        listEditInventories = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        btnUpdate.setOnClickListener(updateInventory);

        ((MainActivity)getContext()).getMySqlByID("Data Inventory", adapter, recyclerView, listEditInventories,
                0, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null,null, btnUpdate, null);
    }

    private View.OnClickListener updateInventory = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dataInventory();
        }
    };

    public void dataInventory() {
        time = dateFormat.format(currentDate);

        progressBar.setVisibility(View.VISIBLE);
        btnUpdate.setVisibility(View.GONE);

        stringRequest = new StringRequest(Request.Method.POST, ((MainActivity)getContext()).setURLUpdateData("Inventory"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("Success")) {
                            progressBar.setVisibility(View.GONE);
                            btnUpdate.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Update Succesful", Toast.LENGTH_SHORT).show();
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
                params.put("Size", String.valueOf(EditInventoryListAdapter.productID.size()));
                params.put("Time", time);
                for (int i = 0; i < EditInventoryListAdapter.productID.size(); i++) {
                    params.put("ID["+i+"]", EditInventoryListAdapter.productID.get(i));
                    params.put("Quantity["+i+"]", EditInventoryListAdapter.productQuantity.get(i));
                }
                return params;
            }
        };

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        requestQueue.add(stringRequest);
    }
}
