package com.example.project.Fragment;


import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.example.project.Activity.MainActivity;
import com.example.project.Model.ListReport;
import com.example.project.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class SoldByProductReportsFragment extends Fragment {

    LinearLayout
            layoutSearch;

    Button
            btnReports;

    EditText
            inputStartDate,
            inputEndDate,
            inputSearch;

    RequestQueue
            requestQueue;

    List<ListReport>
            listReports;

    RecyclerView
            recyclerView;

    RecyclerView.LayoutManager
            layoutManager;

    RecyclerView.Adapter
            adapter;

    private SimpleDateFormat
            dateExpiredFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private Calendar
            calendar = Calendar.getInstance();

    public SoldByProductReportsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sold_by_product_reports, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("Sold By Product Reports");

        btnReports = getView().findViewById(R.id.btn_get_report);

        inputStartDate = getView().findViewById(R.id.input_start_date);
        inputEndDate = getView().findViewById(R.id.input_end_date);

        recyclerView = getView().findViewById(R.id.recycler_view);

        inputSearch = getView().findViewById(R.id.input_search);

        layoutSearch = getView().findViewById(R.id.layout_search);

        listReports = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        btnReports.setOnClickListener(searchSoldByProductReport);
        inputStartDate.setOnClickListener(getStartDate);
        inputEndDate.setOnClickListener(getEndDate);

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                ((MainActivity) getContext()).searchMySql("Search Sold By Product", adapter, recyclerView,
                        null, null, null, null, null, null, null,
                        null, listReports, null, inputStartDate, inputEndDate, inputSearch, null, null, layoutSearch);
            }
        });
    }

    private View.OnClickListener searchSoldByProductReport = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (inputStartDate.getText().toString().isEmpty() && inputEndDate.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Please select date", Toast.LENGTH_LONG).show();
            } else {
                ((MainActivity) getContext()).searchMySql("Search Sold By Product", adapter, recyclerView,
                        null, null, null, null, null, null, null,
                        null, listReports, null, inputStartDate, inputEndDate, inputSearch, null, null, layoutSearch);
            }
        }
    };

    private View.OnClickListener getStartDate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getDate(inputStartDate);
        }
    };

    private View.OnClickListener getEndDate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getDate(inputEndDate);
        }
    };

    private void getDate(final EditText inputDate) {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH, i1);
                calendar.set(Calendar.DAY_OF_MONTH, i2);

                inputDate.setText(dateExpiredFormat.format(calendar.getTime()));
            }
        };

        new DatePickerDialog(getActivity(), date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
