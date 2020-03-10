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
import android.widget.ImageView;
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

public class EditProductFragment extends Fragment {

    EditText
            inputCode,
            inputName,
            inputPurchase,
            inputRetail,
            inputFile;

    Spinner
            spinnerCategory,
            spinnerStatus;

    ImageView
            productImage;

    Button
            btnBrowse,
            btnUpdate;

    String
            code,
            name,
            purchase,
            retail,
            category,
            file,
            time;

    SimpleDateFormat
            dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa", Locale.getDefault());

    Calendar
            calendar = Calendar.getInstance();

    Date
            currentDate = calendar.getTime();

    ProgressBar
            progressBar;

    public static int ID, status;

    private RequestQueue
            requestQueue;

    StringRequest
            stringRequest;

    public EditProductFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("Edit Product");

        inputCode = getView().findViewById(R.id.edit_product_code);
        inputName = getView().findViewById(R.id.edit_product_name);
        inputPurchase = getView().findViewById(R.id.edit_product_purchase);
        inputRetail = getView().findViewById(R.id.edit_product_retail);
        inputFile = getView().findViewById(R.id.edit_file);

        spinnerCategory = getView().findViewById(R.id.edit_product_category);
        spinnerStatus = getView().findViewById(R.id.edit_product_status);

        productImage = getView().findViewById(R.id.product_image);

        progressBar = getView().findViewById(R.id.progressBar);

        btnBrowse = getView().findViewById(R.id.btn_browse);
        btnUpdate = getView().findViewById(R.id.btn_update);

        ((MainActivity)getContext()).setSpinner(((MainActivity)getContext()).setURLReadData("Product Category"), "category", spinnerCategory);

        ((MainActivity)getContext()).getMySqlByID("Data Product", null,null,null,
                ID, inputCode, inputName, inputPurchase, inputRetail, null, null, null, null,
                null, null, null, null, spinnerCategory, spinnerStatus, null, null, null);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        btnUpdate.setOnClickListener(updateProduct);
    }

    private View.OnClickListener updateProduct = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dataProduct();
        }
    };

    private void dataProduct() {
        code = inputCode.getText().toString().trim();
        name = inputName.getText().toString().trim();
        purchase = inputPurchase.getText().toString().trim();
        retail = inputRetail.getText().toString().trim();
        category = spinnerCategory.getSelectedItem().toString().trim();
        status = spinnerStatus.getSelectedItemPosition();

        time = dateFormat.format(currentDate);

        progressBar.setVisibility(View.VISIBLE);
        btnUpdate.setVisibility(View.GONE);

        stringRequest = new StringRequest(Request.Method.POST, ((MainActivity) getContext()).setURLUpdateData("Product"),
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
                params.put("Code", code);
                params.put("Name", name);
                params.put("Category", category);
                params.put("Purchase", purchase);
                params.put("Retail", retail);
                params.put("Status", String.valueOf(status));
                params.put("Time", time);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
