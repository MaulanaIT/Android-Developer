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
import com.android.volley.DefaultRetryPolicy;
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


public class AddCustomerFragment extends Fragment {

    Button
            btnAdd;

    private EditText
            inputName,
            inputEmail,
            inputMobile;

    private RequestQueue
            requestQueue;

    StringRequest
            stringRequest;

    ProgressBar
            progressBar;

    SimpleDateFormat
            dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa", Locale.getDefault());

    Calendar
            calendar = Calendar.getInstance();

    Date
            currentDate = calendar.getTime();

    public AddCustomerFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add_customer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("Add Customer");

        btnAdd = getView().findViewById(R.id.btn_add);

        inputName = getView().findViewById(R.id.input_name);
        inputEmail = getView().findViewById(R.id.input_email);
        inputMobile = getView().findViewById(R.id.input_mobile);

        progressBar = getView().findViewById(R.id.progressBar);

        btnAdd.setOnClickListener(addCustomer);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
    }

    private View.OnClickListener addCustomer = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dataCustomer();
        }
    };

    private void dataCustomer() {
        final String
                name = inputName.getText().toString().trim(),
                email = inputEmail.getText().toString().trim(),
                mobile = inputMobile.getText().toString().trim(),
                time = dateFormat.format(currentDate);

        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.matches(emailPattern)){
            progressBar.setVisibility(View.VISIBLE);
            btnAdd.setVisibility(View.GONE);

            stringRequest = new StringRequest(Request.Method.POST, ((MainActivity)getContext()).setURLAddData("Customer"),
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
                    params.put("Email", email);
                    params.put("Mobile", mobile);
                    params.put("Time", time);
                    return params;
                }
            };

            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(getActivity(), "Email is not valid", Toast.LENGTH_LONG).show();
        }

    }
}
