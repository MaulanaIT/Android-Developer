package com.example.project.Fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.example.project.Activity.MainActivity;
import com.example.project.Model.ListDebit;
import com.example.project.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DebitFragment extends Fragment {

    List<ListDebit>
            listDebits;

    Button
            btnSearch,
            btnExport;

    EditText
            inputCustomerName,
            inputDateFrom,
            inputDateTo;

    private SimpleDateFormat
            dateExpiredFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private Calendar
            calendar = Calendar.getInstance();

    StringRequest
            stringRequest;

    RecyclerView
            recyclerView;

    RecyclerView.LayoutManager
            layoutManager;

    RecyclerView.Adapter
            adapter;

    public DebitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_debit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("Debit");

        btnSearch = getView().findViewById(R.id.btn_search);
        btnExport = getView().findViewById(R.id.btn_export);

        inputCustomerName = getView().findViewById(R.id.input_name);
        inputDateFrom = getView().findViewById(R.id.input_date_from);
        inputDateTo = getView().findViewById(R.id.input_date_to);

        recyclerView = getView().findViewById(R.id.recycler_view);

        listDebits = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        ((MainActivity) getContext()).getMysql("Data Debit", adapter, recyclerView, null, listDebits, null,
                null, null, null, null, null, null, null,
                null, null, null, null, null, null, null);

        inputDateFrom.setOnClickListener(getDateFrom);
        inputDateTo.setOnClickListener(getDateTo);
        btnSearch.setOnClickListener(searchDebit);
    }

    private View.OnClickListener searchDebit = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (inputDateFrom.getText().toString().isEmpty() && inputDateTo.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Please select date", Toast.LENGTH_LONG).show();
            } else {
                searchDataDebit();
            }
        }
    };

    private void searchDataDebit() {
        ((MainActivity) getContext()).searchMySql("Search Debit", adapter, recyclerView,
                null, listDebits, null, null, null, null, null,
                null,null, null, inputCustomerName, inputDateFrom, inputDateTo, null, null, null);
    }

    private View.OnClickListener getDateFrom = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setDate(inputDateFrom);
        }
    };

    private View.OnClickListener getDateTo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setDate(inputDateTo);
        }
    };

    private void setDate(final EditText inputDate) {
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
