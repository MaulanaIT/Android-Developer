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
import android.widget.TextView;

import com.example.project.Activity.MainActivity;
import com.example.project.Model.ListExpenses;
import com.example.project.Model.ListOutlet;
import com.example.project.Model.ListPosOutlets;
import com.example.project.Adapter.PosOutletsListAdapter;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class PosListOutletsFragment extends Fragment {

    List<ListPosOutlets>
            listPosOutlets;

    RecyclerView
            recyclerView;

    RecyclerView.LayoutManager
            layoutManager;

    RecyclerView.Adapter
            adapter;

    private TextView
            outletsName,
            outletsAddress,
            outletsTelephone;

    public PosListOutletsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pos_list_outlets, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("POS");

        outletsName = getView().findViewById(R.id.outlets_name);
        outletsAddress = getView().findViewById(R.id.outlet_address);
        outletsTelephone = getView().findViewById(R.id.outlet_telephone);

        recyclerView = getView().findViewById(R.id.recycler_view);

        listPosOutlets = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        ((MainActivity)getContext()).getMysql("Data POS Outlet", adapter, recyclerView,
                null, null, null, null, null, null, null,
                listPosOutlets, null, null, null, null, null, null, null, null,null);
    }
}
