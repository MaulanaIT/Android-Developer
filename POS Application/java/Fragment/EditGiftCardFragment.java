package com.example.project.Fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Random;

public class EditGiftCardFragment extends Fragment {

    EditText
            inputNumber,
            inputValue,
            inputExpireDate;

    Spinner
            spinnerStatus;

    Button
            btnUpdate;

    String
            number,
            value,
            expireDate,
            time;

    int
            status;

    public static int ID;

    ProgressBar
            progressBar;

    private SimpleDateFormat
            dateExpiredFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()),
            dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa", Locale.getDefault());

    private Calendar
            calendar = Calendar.getInstance();

    private Date
            currentDate = calendar.getTime();

    private RequestQueue
            requestQueue;

    StringRequest
            stringRequest;

    public EditGiftCardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_gift_card, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getContext()).setMyTitle("Edit Gift Card");

        inputNumber = getView().findViewById(R.id.edit_number);
        inputValue = getView().findViewById(R.id.edit_value);
        inputExpireDate = getView().findViewById(R.id.edit_expire_date);

        spinnerStatus = getView().findViewById(R.id.edit_status);

        btnUpdate = getView().findViewById(R.id.btn_update);

        progressBar = getView().findViewById(R.id.progressBar);

        ((MainActivity) getContext()).getMySqlByID("Data Gift Card", null, null, null,
                ID, inputNumber, inputValue, inputExpireDate, null, null, null, null, null,
                null, null, null, null, spinnerStatus, null, null, null, null);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        btnUpdate.setOnClickListener(updateGiftCard);
        inputExpireDate.setOnClickListener(getDate);
    }

    private View.OnClickListener updateGiftCard = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dataGiftCard();
        }
    };

    private void dataGiftCard() {
        value = inputValue.getText().toString().trim();
        expireDate = inputExpireDate.getText().toString().trim();
        status = spinnerStatus.getSelectedItemPosition();

        time = dateFormat.format(currentDate);

        progressBar.setVisibility(View.VISIBLE);
        btnUpdate.setVisibility(View.GONE);

        stringRequest = new StringRequest(Request.Method.POST, ((MainActivity) getContext()).setURLUpdateData("Gift Card"),
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
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", String.valueOf(ID));
                params.put("Value", value);
                params.put("Expired", expireDate);
                params.put("Status", String.valueOf(status));
                params.put("Time", time);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private View.OnClickListener getDate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    calendar.set(Calendar.YEAR, i);
                    calendar.set(Calendar.MONTH, i1);
                    calendar.set(Calendar.DAY_OF_MONTH, i2);

                    inputExpireDate.setText(dateExpiredFormat.format(calendar.getTime()));
                }
            };

            new DatePickerDialog(getActivity(), date, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    };
}
