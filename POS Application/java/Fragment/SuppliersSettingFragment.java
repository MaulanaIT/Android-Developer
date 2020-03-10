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
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.example.project.Activity.MainActivity;
import com.example.project.Model.ListExpenses;
import com.example.project.Model.ListSupplier;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class SuppliersSettingFragment extends Fragment {

    LinearLayout
            btnAddSupplier;

    Button
            btnEdit;

    List<ListSupplier> listSuppliers;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager
            layoutManager;

    RecyclerView.Adapter adapter;

    public SuppliersSettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_suppliers_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("Suppliers Setting");

        btnAddSupplier = getView().findViewById(R.id.btn_add_suppliers);
        btnEdit = getView().findViewById(R.id.btn_edit_row1);

        btnAddSupplier.setOnClickListener(goToAddSupplier);

        recyclerView = getView().findViewById(R.id.recycler_view);

        listSuppliers = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        ((MainActivity)getContext()).getMysql("Data Supplier", adapter, recyclerView,
                null, null, null, null, null, null, null,null,
                null, null, null,  null, null,null, null, listSuppliers, null);
    }

    private View.OnClickListener goToAddSupplier = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new AddSupplierFragment()).addToBackStack("Fragment").commit();
        }
    };
}
