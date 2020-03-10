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
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.example.project.Activity.MainActivity;
import com.example.project.Model.ListCustomer;
import com.example.project.Model.ListPurchaseOrder;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderFragment extends Fragment {

    LinearLayout
            btnAddPurchaseOrder;

    List<ListPurchaseOrder>
            listPurchaseOrders;

    RecyclerView
            recyclerView;

    RecyclerView.LayoutManager
            layoutManager;

    RecyclerView.Adapter
            adapter;

    public PurchaseOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_purchase_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("Purchase Order");

        btnAddPurchaseOrder = getView().findViewById(R.id.btn_add_purchase_order);

        recyclerView = getView().findViewById(R.id.recycler_view);

        listPurchaseOrders = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        btnAddPurchaseOrder.setOnClickListener(goToAddPurchaseOrder);

        ((MainActivity) getContext()).getMysql("Data Purchase Order", adapter, recyclerView, null, null,
                null, null, null, null, null, null, null, null,
                null, listPurchaseOrders, null, null, null, null, null);
    }

    private View.OnClickListener goToAddPurchaseOrder = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new AddPurchaseOrderFragment()).addToBackStack("Fragment").commit();
        }
    };
}
