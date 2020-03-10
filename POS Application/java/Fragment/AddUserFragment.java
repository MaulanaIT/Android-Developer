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

public class AddUserFragment extends Fragment {

    Button
            btnAdd;

    private EditText
            inputName,
            inputEmail,
            inputPassword,
            inputConfirmPassword;

    private Spinner
            inputRoleSpinner,
            inputOutletsSpinner;

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
            fullname,
            email,
            password,
            role,
            outlet,
            time;

    public AddUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("Add User");

        btnAdd = getView().findViewById(R.id.btn_add);

        inputName = getView().findViewById(R.id.input_full_name);
        inputEmail = getView().findViewById(R.id.input_email);
        inputPassword = getView().findViewById(R.id.input_password);
        inputConfirmPassword = getView().findViewById(R.id.input_confirm_password);
        inputRoleSpinner = getView().findViewById(R.id.input_role_spinner);
        inputOutletsSpinner = getView().findViewById(R.id.input_outlets_spinner);

        progressBar = getView().findViewById(R.id.progressBar);

        btnAdd.setOnClickListener(addUsers);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        ((MainActivity)getContext()).setSpinner(((MainActivity)getContext()).setURLReadData("Role"), "role", inputRoleSpinner);
        ((MainActivity)getContext()).setSpinner(((MainActivity)getContext()).setURLReadData("Outlet"), "outlet", inputOutletsSpinner);
    }

    private View.OnClickListener addUsers = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dataUser();
        }
    };

    private void dataUser() {
        fullname = inputName.getText().toString().trim();
        email = inputEmail.getText().toString().trim();
        password = inputPassword.getText().toString().trim();
        role = inputRoleSpinner.getSelectedItem().toString().trim();
        outlet = inputOutletsSpinner.getSelectedItem().toString().trim();
        time = dateFormat.format(currentDate);

        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.matches(emailPattern)){
            progressBar.setVisibility(View.VISIBLE);
            btnAdd.setVisibility(View.GONE);

            stringRequest = new StringRequest(Request.Method.POST, ((MainActivity)getContext()).setURLAddData("User"),
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
                            Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Fullname", fullname);
                    params.put("Email", email);
                    params.put("Password", password);
                    params.put("Role", role);
                    params.put("Outlet", outlet);
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
