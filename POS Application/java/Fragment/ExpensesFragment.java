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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.project.Activity.MainActivity;
import com.example.project.Model.ListExpenses;
import com.example.project.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExpensesFragment extends Fragment {

    LinearLayout
            btnAddExpenses;

    Button
            btnSearch,
            btnExport;

    EditText
            inputExpensesNumber,
            inputDateFrom,
            inputDateTo;

    Spinner
            categorySpinner,
            outletsSpinner;

    ProgressBar
            progressBar;

    private SimpleDateFormat
            dateExpiredFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private Calendar
            calendar = Calendar.getInstance();

    List<ListExpenses>
            listExpenses;

    RecyclerView
            recyclerView;

    RecyclerView.LayoutManager
            layoutManager;

    RecyclerView.Adapter
            adapter;

    public ExpensesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expenses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("Expenses");

        btnAddExpenses = getView().findViewById(R.id.btn_add_expanses);
        btnSearch = getView().findViewById(R.id.btn_search);
        btnExport = getView().findViewById(R.id.btn_export);

        inputExpensesNumber = getView().findViewById(R.id.input_number);
        inputDateFrom = getView().findViewById(R.id.input_date_from);
        inputDateTo = getView().findViewById(R.id.input_date_to);

        categorySpinner = getView().findViewById(R.id.category_spinner);
        outletsSpinner = getView().findViewById(R.id.outlets_spinner);

        progressBar = getView().findViewById(R.id.progressBar);

        recyclerView = getView().findViewById(R.id.recycler_view);

        listExpenses = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        ((MainActivity)getContext()).getMysql("Data Expenses", adapter, recyclerView,
                null, null, null, listExpenses, null, null, null,null,
                null, null, null, null, null, null, null, null,null);

        ((MainActivity)getContext()).setSpinner(((MainActivity)getContext()).setURLReadData("Expenses Category"), "category", categorySpinner);
        ((MainActivity)getContext()).setSpinner(((MainActivity)getContext()).setURLReadData("Outlet"), "outlet", outletsSpinner);

        inputDateFrom.setOnClickListener(getDateFrom);
        inputDateTo.setOnClickListener(getDateTo);

        btnAddExpenses.setOnClickListener(goToAddExpenses);
        btnSearch.setOnClickListener(searchExpenses);
    }

    private View.OnClickListener goToAddExpenses = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new AddExpensesFragment()).addToBackStack("Fragment").commit();
        }
    };

    private View.OnClickListener searchExpenses = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (inputDateFrom.getText().toString().isEmpty() && inputDateTo.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Please select date", Toast.LENGTH_LONG).show();
            } else {
                searchDataExpenses();
            }
        }
    };

    private void searchDataExpenses() {
        ((MainActivity) getContext()).searchMySql("Search Expenses", adapter, recyclerView,
                null, null, null, listExpenses, null, null, null,
                null,null, null, inputExpensesNumber, inputDateFrom, inputDateTo, categorySpinner, outletsSpinner, null);
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