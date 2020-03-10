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

public class EditUserFragment extends Fragment {

    EditText
            inputName,
            inputEmail;

    Spinner
            spinnerRole,
            spinnerOutlet,
            spinnerStatus;

    Button
            btnUpdate,
            btnDelete;

    ProgressBar
            progressBar;

    public static int ID;

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

    String
            name,
            email,
            role,
            outlet,
            time;

    int
            status;

    public EditUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getContext()).setMyTitle("Edit User");

        inputName = getView().findViewById(R.id.edit_full_name);
        inputEmail = getView().findViewById(R.id.edit_email);

        spinnerRole = getView().findViewById(R.id.edit_role);
        spinnerOutlet = getView().findViewById(R.id.edit_outlets);
        spinnerStatus = getView().findViewById(R.id.edit_status);

        btnUpdate = getView().findViewById(R.id.btn_update);
        btnDelete = getView().findViewById(R.id.btn_delete);

        progressBar = getView().findViewById(R.id.progressBar);

        ((MainActivity)getContext()).setSpinner(((MainActivity)getContext()).setURLReadData("Role"), "role", spinnerRole);
        ((MainActivity)getContext()).setSpinner(((MainActivity)getContext()).setURLReadData("Outlet"), "outlet", spinnerOutlet);

        ((MainActivity) getContext()).getMySqlByID("Data User", null, null, null,
                ID, inputName, inputEmail, null, null, null, null, null, null,
                null, null, null, null, spinnerRole, spinnerOutlet, spinnerStatus, null, null);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        btnUpdate.setOnClickListener(updateUser);
        btnDelete.setOnClickListener(deleteUser);
    }

    private View.OnClickListener deleteUser = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dataUser("Delete");
        }
    };

    private View.OnClickListener updateUser = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dataUser("Update");
        }
    };

    private void dataUser(String condition) {

        if (condition.equals("Update")) {
            name = inputName.getText().toString().trim();
            email = inputEmail.getText().toString().trim();
            role = spinnerRole.getSelectedItem().toString().trim();
            outlet = spinnerOutlet.getSelectedItem().toString().trim();
            status = spinnerStatus.getSelectedItemPosition();

            time = dateFormat.format(currentDate);

            progressBar.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);

            stringRequest = new StringRequest(Request.Method.POST, ((MainActivity) getContext()).setURLUpdateData("User"),
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
                    params.put("Name", name);
                    params.put("Email", email);
                    params.put("Role", role);
                    params.put("Outlet", outlet);
                    params.put("Status", String.valueOf(status));
                    params.put("Time", time);
                    return params;
                }
            };
        } else if (condition.equals("Delete")) {
            stringRequest = new StringRequest(Request.Method.POST, ((MainActivity) getContext()).setURLDeleteData("User"),
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
                    return params;
                }
            };
        }

        requestQueue.add(stringRequest);
    }
}
