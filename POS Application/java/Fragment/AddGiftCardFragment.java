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

public class AddGiftCardFragment extends Fragment {

    Button
            btnGenerate,
            btnAdd;

    private EditText
            inputCardNumber,
            inputValue,
            inputExpireDate;

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

    private String
            number,
            value,
            expired,
            time;

    public AddGiftCardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_gift_card, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("Add Gift Card");

        btnGenerate = getView().findViewById(R.id.btn_generate);
        btnAdd = getView().findViewById(R.id.btn_add);

        inputCardNumber = getView().findViewById(R.id.input_card_number);
        inputValue = getView().findViewById(R.id.input_value);
        inputExpireDate = getView().findViewById(R.id.input_exipre_date);

        progressBar = getView().findViewById(R.id.progressBar);

        btnGenerate.setOnClickListener(generateNumber);
        inputExpireDate.setOnClickListener(getDate);
        btnAdd.setOnClickListener(addGiftCard);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
    }

    private View.OnClickListener addGiftCard = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dataGiftCard();
        }
    };

    private void dataGiftCard() {
        number = inputCardNumber.getText().toString().trim();
        value = inputValue.getText().toString().trim();
        expired = inputExpireDate.getText().toString().trim();
        time = dateFormat.format(currentDate);

        progressBar.setVisibility(View.VISIBLE);
        btnAdd.setVisibility(View.GONE);

        stringRequest = new StringRequest(Request.Method.POST, ((MainActivity)getContext()).setURLAddData("Gift Card"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("Success")) {
                            progressBar.setVisibility(View.GONE);
                            btnAdd.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Insert Succesful", Toast.LENGTH_SHORT).show();

                            inputCardNumber.setText("");
                            inputValue.setText("");
                            inputExpireDate.setText("");
                        } else {
                            progressBar.setVisibility(View.GONE);
                            btnAdd.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Insert Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        btnAdd.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Number", number);
                params.put("Value", value);
                params.put("Expired", expired);
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

    private final View.OnClickListener generateNumber = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int random1 = new Random().nextInt(8999) + 1000;
            int random2 = new Random().nextInt(8999) + 1000;
            int random3 = new Random().nextInt(8999) + 1000;
            int random4 = new Random().nextInt(8999) + 1000;
            inputCardNumber.setText(random1 + " " + random2 + " " + random3 + " " + random4);
        }
    };
}
