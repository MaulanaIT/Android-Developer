package com.example.project.Fragment;


import android.app.ProgressDialog;
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

import com.example.project.Activity.MainActivity;
import com.example.project.Model.ListCustomer;
import com.example.project.Model.ListInventory;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class InventoryFragment extends Fragment {

    Button
            btnSearch,
            btnExport;

    EditText
            inputProductCode,
            inputProductName;

    ProgressBar
            progressBar;

    List<ListInventory>
            listInventories;

    RecyclerView
            recyclerView;

    RecyclerView.LayoutManager
            layoutManager;

    RecyclerView.Adapter
            adapter;

    public InventoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inventory, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("Inventory");

        btnExport = getView().findViewById(R.id.btn_export);
        btnSearch = getView().findViewById(R.id.btn_search);

        inputProductCode = getView().findViewById(R.id.input_product_code);
        inputProductName = getView().findViewById(R.id.input_product_name);

        recyclerView = getView().findViewById(R.id.recycler_view);

        progressBar = getView().findViewById(R.id.progressBar);

        listInventories = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        listAllInventory();

        btnSearch.setOnClickListener(searchInventory);
    }

    private View.OnClickListener searchInventory = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (inputProductCode.getText().toString().isEmpty() && inputProductName.getText().toString().isEmpty()) {
                listAllInventory();
            } else {
                searchDataInventory();
            }
        }
    };

    private View.OnClickListener editInventory = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new EditInventoryFragment()).addToBackStack("Fragment").commit();
        }
    };

    private void searchDataInventory() {
        ((MainActivity) getContext()).searchMySql("Search Inventory", adapter, recyclerView,
                null, null, null, null, listInventories, null, null,
                null,null, null, inputProductCode, inputProductName, null, null, null, null);
    }

    private void listAllInventory() {
        ((MainActivity)getContext()).getMysql("Data Inventory", adapter, recyclerView, null, null,
                null, null, null, listInventories, null, null,null, null,
                null, null, null,  null,null, null,null);
    }
}
