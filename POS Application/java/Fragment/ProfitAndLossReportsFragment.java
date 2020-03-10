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

import com.android.volley.RequestQueue;
import com.example.project.Activity.MainActivity;
import com.example.project.Model.ListReport;
import com.example.project.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ProfitAndLossReportsFragment extends Fragment {

    LinearLayout
            layoutSearch;

    Button
            btnReports;

    EditText
            inputStartDate,
            inputEndDate,
            inputSearch;

    Spinner
            spinnerOutlet;

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

    public ProfitAndLossReportsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profit_and_loss_reports, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("Profit & Loss Retports");

        btnReports = getView().findViewById(R.id.btn_get_report);

        inputStartDate = getView().findViewById(R.id.input_date_from);
        inputEndDate = getView().findViewById(R.id.input_date_to);

        spinnerOutlet = getView().findViewById(R.id.outlets_spinner);

        recyclerView = getView().findViewById(R.id.recycler_view);

        inputSearch = getView().findViewById(R.id.input_search);

        layoutSearch = getView().findViewById(R.id.layout_search);

        listReports = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        ((MainActivity)getContext()).setSpinner(((MainActivity)getContext()).setURLReadData("Outlet"), "outlet", spinnerOutlet);

        btnReports.setOnClickListener(searchProfitAndLossReport);
        inputStartDate.setOnClickListener(getDateFrom);
        inputEndDate.setOnClickListener(getDateTo);

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                ((MainActivity) getContext()).searchMySql("Search Profit And Loss Report", adapter, recyclerView,
                        null, null, null, null, null, null, null,
                        null, listReports, null, inputStartDate, inputEndDate, inputSearch, spinnerOutlet, null, layoutSearch);
            }
        });
    }

    private View.OnClickListener getDateFrom = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setDate(inputStartDate);
        }
    };

    private View.OnClickListener searchProfitAndLossReport = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (inputStartDate.getText().toString().isEmpty() && inputEndDate.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Please select date", Toast.LENGTH_LONG).show();
            } else {
                ((MainActivity) getContext()).searchMySql("Search Profit And Loss Report", adapter, recyclerView,
                        null, null, null, null, null, null, null,
                        null, listReports, null, inputStartDate, inputEndDate, inputSearch, spinnerOutlet, null, layoutSearch);
            }
        }
    };

    private View.OnClickListener getDateTo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setDate(inputEndDate);
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
