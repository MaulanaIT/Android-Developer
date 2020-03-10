package com.example.project.Fragment;


import android.app.ProgressDialog;
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
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.example.project.Activity.MainActivity;
import com.example.project.Model.ListExpenses;
import com.example.project.Model.ListOutlet;
import com.example.project.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class OutletsSettingFragment extends Fragment {

    LinearLayout
            btnAddOutlets;

    Button
            btnEdit;

    EditText
            inputSearch;

    List<ListOutlet> listOutlets;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager
            layoutManager;

    RecyclerView.Adapter adapter;

    int showAll = 1;

    public OutletsSettingFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_outlets_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getContext()).setMyTitle("Outlets Setting");

        btnAddOutlets = getView().findViewById(R.id.btn_add_outlet);
        btnEdit = getView().findViewById(R.id.btn_edit_row1);

        btnAddOutlets.setOnClickListener(goToAddOutlets);

        recyclerView = getView().findViewById(R.id.recycler_view);

        inputSearch = getView().findViewById(R.id.input_search);

        listOutlets = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        if (showAll == 1) {
            ((MainActivity) getContext()).getMysql("Data Outlet", adapter, recyclerView,
                    null, null, null, null, null, null, null,null,
                    null, null, null, null, null, listOutlets, null, null,null);
        }

        inputSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showAll = 0;
                return false;
            }
        });

        inputSearch.addTextChangedListener(searchData);
    }

    private TextWatcher searchData = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (showAll == 0) {
                ((MainActivity) getContext()).searchMySql("Search Outlet", adapter, recyclerView,
                        null, null, null, null, null, null, null,
                        null,null, listOutlets, inputSearch, null, null, null, null, null);
            }
        }
    };

    private View.OnClickListener goToAddOutlets = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new AddOutletsFragment()).addToBackStack("Fragment").commit();
        }
    };
}
