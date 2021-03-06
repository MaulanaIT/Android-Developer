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
import com.example.project.Adapter.EditInventoryListAdapter;
import com.example.project.R;

import java.net.IDN;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EditPasswordFragment extends Fragment {

    Button
            btnUpdate;

    EditText
            inputPassword,
            inputConfirmPassword;

    String
            password,
            confirmPassword,
            time;

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

    ProgressBar
            progressBar;

    public EditPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getContext()).setMyTitle("Edit Password");

        inputPassword = getView().findViewById(R.id.edit_password);
        inputConfirmPassword = getView().findViewById(R.id.edit_confirm_password);

        btnUpdate = getView().findViewById(R.id.btn_update);

        btnUpdate.setOnClickListener(updatePassword);
    }

    private View.OnClickListener updatePassword = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dataPassword();
        }
    };

    public void dataPassword() {
        password = inputPassword.getText().toString().trim();
        confirmPassword = inputConfirmPassword.getText().toString().trim();
        time = dateFormat.format(currentDate);

        if (password.equals(confirmPassword)) {
            stringRequest = new StringRequest(Request.Method.POST, ((MainActivity)getContext()).setURLUpdateData("Password"),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("Success")) {
                                progressBar.setVisibility(View.GONE);
                                btnUpdate.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), "Update Succesful", Toast.LENGTH_SHORT).show();
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
                    params.put("Password", password);
                    params.put("Time", time);
                    return params;
                }
            };

            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

            requestQueue.add(stringRequest);
        }
    }
}
