package com.example.project.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.project.Activity.MainActivity;
import com.example.project.Model.ListOpenedBill;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class OpenedBillFragment extends Fragment {

    Button
            btnPrevious,
            btnNext;

    EditText
            inputEntries,
            inputSearch;

    int showAll = 1;

    List<ListOpenedBill>
            listOpenedBills;

    RecyclerView
            recyclerView;

    RecyclerView.LayoutManager
            layoutManager;

    RecyclerView.Adapter
            adapter;

    public OpenedBillFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_opened_bill, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("Opened Bill");

        btnPrevious = getView().findViewById(R.id.btn_previous);
        btnNext = getView().findViewById(R.id.btn_next);

        inputEntries = getView().findViewById(R.id.input_entries);
        inputSearch = getView().findViewById(R.id.input_search);

        recyclerView = getView().findViewById(R.id.recycler_view);

        listOpenedBills = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        if (showAll == 1) {
            ((MainActivity) getContext()).getMysql("Data Opened Bill", adapter, recyclerView, null, null, null,
                    null, null, null, listOpenedBills, null, null, null,
                    null, null, null, null, null, null, null);
        }

        inputSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showAll = 0;
                return false;
            }
        });

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (showAll == 0) {
                    ((MainActivity) getContext()).searchMySql("Search Opened Bill", adapter, recyclerView, null,
                            null, null, null, null, listOpenedBills, null, null,
                            null, null, inputSearch, null, null, null, null, null);
                }
            }
        });
    }
}
