package com.example.project.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class AddOutletsFragment extends Fragment {

    Button
            btnAdd;

    private EditText
            inputName,
            inputNumber,
            inputAddress,
            inputReceiptFooter,
            inputReceiptHeader;

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

    private String
            name,
            number,
            address,
            receiptfooter,
            receiptheader,
            time;

    public AddOutletsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_outlets, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("Add Outlets");

        btnAdd = getView().findViewById(R.id.btn_add);

        inputName = getView().findViewById(R.id.input_outlets_name);
        inputNumber = getView().findViewById(R.id.input_contact_number);
        inputAddress = getView().findViewById(R.id.input_address);
        inputReceiptFooter = getView().findViewById(R.id.input_receipt_footer);
        inputReceiptHeader = getView().findViewById(R.id.input_receipt_header);

        progressBar = getView().findViewById(R.id.progressBar);

        btnAdd.setOnClickListener(addOutlets);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
    }

    private View.OnClickListener addOutlets = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dataOutlets();
        }
    };

    private void dataOutlets() {
        name = inputName.getText().toString().trim();
        number = inputNumber.getText().toString().trim();
        address = inputAddress.getText().toString().trim();
        receiptfooter = inputReceiptFooter.getText().toString().trim();
        receiptheader = inputReceiptHeader.getText().toString().trim();
        time = dateFormat.format(currentDate);

        progressBar.setVisibility(View.VISIBLE);
        btnAdd.setVisibility(View.GONE);

        stringRequest = new StringRequest(Request.Method.POST, ((MainActivity)getContext()).setURLAddData("Outlet"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("Success")) {
                            progressBar.setVisibility(View.GONE);
                            btnAdd.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Insert Succesful", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().popBackStack();
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
                params.put("Name", name);
                params.put("Number", number);
                params.put("Address", address);
                params.put("ReceiptHeader", receiptheader);
                params.put("ReceiptFooter", receiptfooter);
                params.put("Time", time);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
