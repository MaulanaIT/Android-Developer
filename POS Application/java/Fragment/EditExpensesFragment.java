package com.example.project.Fragment;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class EditExpensesFragment extends Fragment {

    public static EditText
            inputNumber,
            inputDate,
            inputReason,
            inputAmount,
            inputFile;

    Spinner
            spinnerOutlet,
            spinnerCategory;

    Button
            btnUpdate,
            btnBrowse;

    private String
            fileUri,
            fileName,
            number,
            outlets,
            date,
            reason,
            amount,
            category,
            time;

    Uri
            uri;

    Bitmap
            bitmap;

    ImageView
            imageView;

    public static int
            id;

    private SimpleDateFormat
            dateFormatexpenses = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()),
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

    public EditExpensesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_expenses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("Edit Expenses");

        inputNumber = getView().findViewById(R.id.edit_expenses_number);
        inputDate = getView().findViewById(R.id.edit_date);
        inputReason = getView().findViewById(R.id.edit_reason);
        inputAmount = getView().findViewById(R.id.edit_amount);
        inputFile = getView().findViewById(R.id.edit_file);

        imageView = getView().findViewById(R.id.image_view);

        spinnerCategory = getView().findViewById(R.id.edit_expenses_category);
        spinnerOutlet = getView().findViewById(R.id.edit_outlets);

        progressBar = getView().findViewById(R.id.progressBar);

        btnUpdate = getView().findViewById(R.id.btn_update);
        btnBrowse = getView().findViewById(R.id.btn_browse);

        ((MainActivity) getContext()).getMySqlByID("Data Expenses", null, null, null,
                id, inputNumber, inputDate, inputReason, inputAmount, null, null, null, null, null,
                null, null, null,null, null, null, null, imageView);

        ((MainActivity)getContext()).setSpinner(((MainActivity)getContext()).setURLReadData("Expenses Category"), "category", spinnerCategory);
        ((MainActivity)getContext()).setSpinner(((MainActivity)getContext()).setURLReadData("Outlet"), "outlet", spinnerOutlet);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        btnUpdate.setOnClickListener(updateExpenses);
        inputDate.setOnClickListener(getDate);
        btnBrowse.setOnClickListener(filePicker);
    }

    private View.OnClickListener updateExpenses = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dataExpenses();
        }
    };

    private void dataExpenses() {
        number = inputNumber.getText().toString().trim();
        outlets = spinnerOutlet.getSelectedItem().toString().trim();
        date = inputDate.getText().toString().trim();
        reason = inputReason.getText().toString().trim();
        amount = inputAmount.getText().toString().trim();
        category = spinnerCategory.getSelectedItem().toString().trim();

        time = dateFormat.format(currentDate);

        progressBar.setVisibility(View.VISIBLE);
        btnUpdate.setVisibility(View.GONE);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        final String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

        stringRequest = new StringRequest(Request.Method.POST, ((MainActivity) getContext()).setURLUpdateData("Expenses"),
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
                params.put("ID", String.valueOf(id));
                params.put("Number", number);
                params.put("Outlets", outlets);
                params.put("Date", date);
                params.put("Reason", reason);
                params.put("Amount", amount);
                params.put("Category", category);
                params.put("Image", fileName);
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

                    inputDate.setText(dateFormatexpenses.format(calendar.getTime()));
                }
            };

            new DatePickerDialog(getActivity(), date, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    };

    private View.OnClickListener filePicker = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setType("file/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 1);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fileUri = data.getData().getPath();
            uri = data.getData();
            fileName = fileUri.substring(fileUri.lastIndexOf("/") + 1);

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
            }

            inputFile.setText(fileName);
        }
    }
}
