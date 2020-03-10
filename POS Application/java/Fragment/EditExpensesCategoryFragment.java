package com.example.project.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.Activity.MainActivity;
import com.example.project.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EditExpensesCategoryFragment extends Fragment {

    public EditText
            inputExpensesCategoryname;

    Spinner
            spinnerStatus;

    Button
            btnUpdate;

    String
            expensesCategoryName,
            expensesCategoryStatus,
            time;

    private SimpleDateFormat
            dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa", Locale.getDefault());

    private Calendar
            calendar = Calendar.getInstance();

    private Date
            currentDate = calendar.getTime();

    private RequestQueue
            requestQueue;

    StringRequest
            stringRequest;

    ProgressBar
            progressBar;

    public static int
            id;

    public EditExpensesCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_expenses_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("Edit Expenses Category");

        inputExpensesCategoryname = getView().findViewById(R.id.edit_expenses_category_name);

        progressBar = getView().findViewById(R.id.progressBar);

        spinnerStatus = getView().findViewById(R.id.edit_status);

        btnUpdate = getView().findViewById(R.id.btn_update);

        ((MainActivity)getContext()).getMySqlByID("Data Expenses Category", null, null, null,
                id, inputExpensesCategoryname, null, null, null, null, null, null,
                null, null, null, null, null, spinnerStatus, null, null, null, null);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        btnUpdate.setOnClickListener(updateExpensesCategory);
    }

    private View.OnClickListener updateExpensesCategory = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dataExpensesCategory();
        }
    };

    private void dataExpensesCategory() {
        final int status;
        expensesCategoryName = inputExpensesCategoryname.getText().toString().trim();
        expensesCategoryStatus = spinnerStatus.getSelectedItem().toString().trim();

        if (expensesCategoryStatus.equals("Not Active")) {
            status = 0;
        } else {
            status = 1;
        }

        time = dateFormat.format(currentDate);

        progressBar.setVisibility(View.VISIBLE);
        btnUpdate.setVisibility(View.GONE);

        stringRequest = new StringRequest(Request.Method.POST, ((MainActivity) getContext()).setURLUpdateData("Expenses Category"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("Success")) {
                            progressBar.setVisibility(View.GONE);
                            btnUpdate.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Update Succesful", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().popBackStack();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            btnUpdate.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Update Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        btnUpdate.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", String.valueOf(id));
                params.put("Category", expensesCategoryName);
                params.put("Status", String.valueOf(status));
                params.put("Time", time);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
