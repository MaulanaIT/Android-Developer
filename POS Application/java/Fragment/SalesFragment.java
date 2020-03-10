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
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.example.project.Activity.MainActivity;
import com.example.project.Model.ListSales;
import com.example.project.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SalesFragment extends Fragment {

    List<ListSales>
            listSales;

    Button
            btnExport;

    StringRequest
            stringRequest;

    RecyclerView
            recyclerView;

    RecyclerView.LayoutManager
            layoutManager;

    RecyclerView.Adapter
            adapter;

    public SalesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sales, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("Today's Sales");

        btnExport = getView().findViewById(R.id.btn_export);

        recyclerView = getView().findViewById(R.id.recycler_view);

        listSales = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        ((MainActivity) getContext()).getMysql("Data Sales", adapter, recyclerView, null, null, null, null,
                null, null, null, null, null, null, null,
                null, listSales, null, null, null, null);
    }
}
