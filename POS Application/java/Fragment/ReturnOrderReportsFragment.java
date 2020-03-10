package com.example.project.Fragment;


import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.project.Activity.MainActivity;
import com.example.project.Model.ListReport;
import com.example.project.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ReturnOrderReportsFragment extends Fragment {

    Button
            btnReport;

    EditText
            inputOutlets,
            inputRefundBy,
            inputDateFrom,
            inputDateTo;

    Spinner
            outletsSpinner,
            refundBySpinner;

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

    public ReturnOrderReportsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_return_order_reports, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("Return Order Reports");

        btnReport = getView().findViewById(R.id.btn_reports);

        inputOutlets = getView().findViewById(R.id.input_outlets);
        inputRefundBy = getView().findViewById(R.id.input_refund_by);
        inputDateFrom = getView().findViewById(R.id.input_date_from);
        inputDateTo = getView().findViewById(R.id.input_date_to);

        outletsSpinner = getView().findViewById(R.id.outlets_spinner);
        refundBySpinner = getView().findViewById(R.id.refund_by_spinner);

        recyclerView = getView().findViewById(R.id.recycler_view);

        listReports = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        ((MainActivity)getContext()).setSpinner(((MainActivity)getContext()).setURLReadData("Outlet"), "outlet", outletsSpinner);
        ((MainActivity)getContext()).setSpinner(((MainActivity)getContext()).setURLReadData("Payment Method"), "payment", refundBySpinner);

        btnReport.setOnClickListener(searchReturnOrderReport);
        inputDateFrom.setOnClickListener(getDateFrom);
        inputDateTo.setOnClickListener(getDateTo);
    }

    private View.OnClickListener searchReturnOrderReport = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (inputDateFrom.getText().toString().isEmpty() && inputDateTo.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Please select date", Toast.LENGTH_LONG).show();
            } else {
                ((MainActivity) getContext()).searchMySql("Search Return Order Report", adapter, recyclerView,
                        null, null, null, null, null, null, null,
                        null, listReports, null, inputDateFrom, inputDateTo, null, outletsSpinner, refundBySpinner, null);
            }
        }
    };

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
