package com.example.project.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import org.w3c.dom.Text;

import java.net.IDN;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class EditPaymentFragment extends Fragment {

    TextView
            textCustomer,
            textPayableAmount,
            textPaidAmount,
            textTotalItem,
            textPaidBy,
            textReturn;

    EditText
            inputPaidToday;

    Button
            btnSubmit;

    String
            paymentCustomer,
            paymentItem,
            paymentPaidBy,
            paymentPaidAmount,
            paymentReturn,
            time;

    public static int ID;

    int
            vtStatus;

    public static double
            paidAmount,
            returnChange,
            paidToday,
            payable;

    ProgressBar
            progressBar;

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

    public EditPaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getContext()).setMyTitle("Make Payment");

        textCustomer = getView().findViewById(R.id.edit_customer);
        textPayableAmount = getView().findViewById(R.id.edit_payable);
        textTotalItem = getView().findViewById(R.id.edit_item);
        textPaidBy = getView().findViewById(R.id.edit_paid);
        textPaidAmount = getView().findViewById(R.id.edit_paid_amount);
        textReturn = getView().findViewById(R.id.edit_return);

        inputPaidToday = getView().findViewById(R.id.edit_paid_today);

        progressBar = getView().findViewById(R.id.progressBar);

        btnSubmit = getView().findViewById(R.id.btn_submit);

        ((MainActivity) getContext()).getMySqlByID("Data Debit", null, null, null, ID,
                null, null, null, null, null, null, textCustomer, textPayableAmount,
                textTotalItem, textPaidBy, textPaidAmount, textReturn, null, null, null, null,
                null);

        inputPaidToday.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!inputPaidToday.getText().toString().equals("")) {
                    paidToday = Double.valueOf(inputPaidToday.getText().toString());
                } else {
                    paidToday = 0;
                }
                returnChange = (paidToday + paidAmount) - payable;

                textReturn.setText(String.valueOf(returnChange));
            }
        });

        btnSubmit.setOnClickListener(updatePayment);
    }

    private View.OnClickListener updatePayment = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dataPayment();
        }
    };

    private void dataPayment() {
        time = dateFormat.format(currentDate);

        if (returnChange >= 0) {
            vtStatus = 1;
        }
        progressBar.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.GONE);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        stringRequest = new StringRequest(Request.Method.POST, ((MainActivity)getContext()).setURLUpdateData("Payment"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("Success")) {
                            progressBar.setVisibility(View.GONE);
                            btnSubmit.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Insert Succesful", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().popBackStack();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            btnSubmit.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Insert Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        btnSubmit.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", String.valueOf(ID));
                params.put("PaidAmount", String.valueOf(paidToday+paidAmount));
                params.put("Return", String.valueOf(returnChange));
                params.put("Time", time);
                params.put("VtStatus", String.valueOf(vtStatus));
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
