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

public class EditCustomerFragment extends Fragment {

    public static EditText
            inputName,
            inputEmail,
            inputMobile;

    Button
            btnUpdate,
            btnDelete;

    String
            name,
            email,
            mobile,
            time;

    public static int
            ID;

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

    public EditCustomerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_customer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("Edit Customer");

        inputName = getView().findViewById(R.id.edit_customer_name);
        inputEmail = getView().findViewById(R.id.edit_customer_email);
        inputMobile = getView().findViewById(R.id.edit_customer_mobile);

        progressBar = getView().findViewById(R.id.progressBar);

        btnUpdate = getView().findViewById(R.id.btn_update);
        btnDelete = getView().findViewById(R.id.btn_delete);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        btnUpdate.setOnClickListener(updateCustomer);
        btnDelete.setOnClickListener(deleteCustomer);

        ((MainActivity) getContext()).getMySqlByID("Data Customer", null, null, null, ID,
                inputName, inputEmail, inputMobile, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null);
    }

    private View.OnClickListener updateCustomer = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dataCustomer("Update");
        }
    };

    private View.OnClickListener deleteCustomer = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dataCustomer("Delete");
        }
    };

    private void dataCustomer(String condition) {

        if (condition.equals("Update")) {
            name = inputName.getText().toString().trim();
            email = inputEmail.getText().toString().trim();
            mobile = inputMobile.getText().toString().trim();
            time = dateFormat.format(currentDate);

            final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            if (email.matches(emailPattern)){
                progressBar.setVisibility(View.VISIBLE);
                btnUpdate.setVisibility(View.GONE);

                stringRequest = new StringRequest(Request.Method.POST, ((MainActivity) getContext()).setURLUpdateData("Customer"),
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
                        params.put("ID", String.valueOf(ID));
                        params.put("fullname", name);
                        params.put("email", email);
                        params.put("mobile", mobile);
                        params.put("time", time);
                        return params;
                    }
                };

                requestQueue.add(stringRequest);
            } else {
                Toast.makeText(getActivity(), "Email is not valid", Toast.LENGTH_LONG).show();
            }
        } else if (condition.equals("Delete")) {
            progressBar.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);

            stringRequest = new StringRequest(Request.Method.POST, ((MainActivity) getContext()).setURLDeleteData("Customer"),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("Success")) {
                                progressBar.setVisibility(View.GONE);
                                btnUpdate.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), "Delete Succesful", Toast.LENGTH_SHORT).show();
                                getActivity().getSupportFragmentManager().popBackStack();
                            } else {
                                progressBar.setVisibility(View.GONE);
                                btnUpdate.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), "Delete Failed", Toast.LENGTH_SHORT).show();
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
                    params.put("ID", String.valueOf(ID));
                    return params;
                }
            };

            requestQueue.add(stringRequest);
        }
    }
}