package com.example.project.Fragment;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.provider.MediaStore;
import android.util.Base64;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class AddProductFragment extends Fragment {

    Button
            btnBrowse,
            btnAdd;

    private EditText
            inputCode,
            inputName,
            inputCost,
            inputPrice,
            inputImageName;

    Spinner
            spinnerProductCategory;

    ProgressBar
            progressBar;

    private ImageView
            inputImage;

    Uri
            uri;

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
            fileName,
            fileUri,
            file,
            code,
            name,
            category,
            cost,
            price,
            imagename,
            time;

    Bitmap
            bitmap;

    public AddProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("Add Product");

        btnBrowse = getView().findViewById(R.id.btn_browse);
        btnAdd = getView().findViewById(R.id.btn_add);

        inputCode = getView().findViewById(R.id.input_product_code);
        inputName = getView().findViewById(R.id.input_product_name);
        inputCost = getView().findViewById(R.id.input_product_price);
        inputPrice = getView().findViewById(R.id.input_retail_price);
        inputImageName = getView().findViewById(R.id.input_image_name);

        spinnerProductCategory = getView().findViewById(R.id.product_category_spinner);

        progressBar = getView().findViewById(R.id.progressBar);

        inputImage = getView().findViewById(R.id.input_image);

        SharedPreferences user = getActivity().getSharedPreferences("MyPrefs", 0);

        Toast.makeText(getActivity(), user.getString("image", ""), Toast.LENGTH_LONG).show();

        if (user.getString("image", "").equals("")) {
            inputImage.setBackground(getResources().getDrawable(R.drawable.bg_login));
        } else {
//            convert image to string
            byte [] encodeByte=Base64.decode(user.getString("image", ""),Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            inputImage.setImageBitmap(bitmap);
        }

        btnBrowse.setOnClickListener(filePicker);
        btnAdd.setOnClickListener(addProduct);

        requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());

        requestMultiplePermissions();

        ((MainActivity)getContext()).setSpinner(((MainActivity)getContext()).setURLReadData("Product Category"), "category", spinnerProductCategory);
    }

    private View.OnClickListener addProduct = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            dataProduct();
//            ByteArrayOutputStream baos=new  ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
//            byte [] arr=baos.toByteArray();
//            String imageString = Base64.encodeToString(arr, Base64.DEFAULT);
//
//            SharedPreferences user = getActivity().getSharedPreferences("MyPrefs", 0);
//            SharedPreferences.Editor editor = user.edit();
//            editor.putString("image", imageString);
//            editor.commit();
        }
    };

    private void dataProduct() {
        code = inputCode.getText().toString().trim();
        name = inputName.getText().toString().trim();
        category = spinnerProductCategory.getSelectedItem().toString().trim();
        cost = inputCost.getText().toString().trim();
        price = inputPrice.getText().toString().trim();
        imagename = inputImageName.getText().toString().trim();
        time = dateFormat.format(currentDate);

        progressBar.setVisibility(View.VISIBLE);
        btnAdd.setVisibility(View.GONE);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        file = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

        stringRequest = new StringRequest(Request.Method.POST, ((MainActivity)getContext()).setURLAddData("Product"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Success")) {
                            progressBar.setVisibility(View.GONE);
                            btnAdd.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Insert Succesful", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().popBackStack();
                        } else if (response.equals("Failed")) {
                            progressBar.setVisibility(View.GONE);
                            btnAdd.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Insert Image Failed", Toast.LENGTH_SHORT).show();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            btnAdd.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Insert Data Failed", Toast.LENGTH_SHORT).show();
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
                params.put("Code", code);
                params.put("Name", name);
                params.put("Category", category);
                params.put("Cost", cost);
                params.put("Price", price);
                params.put("Image", imagename);
                params.put("File", file);
                params.put("Time", time);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private View.OnClickListener filePicker = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 1);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data!=null && data.getData()!=null) {
            uri = data.getData();
            fileUri = data.getData().getPath();
            fileName = fileUri.substring(fileUri.lastIndexOf("/") + 1);

            inputImageName.setText(fileName);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                inputImage.setBackground(null);
                inputImage.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void  requestMultiplePermissions(){
        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                        }
                        if (report.isAnyPermissionPermanentlyDenied()) { }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getActivity().getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }
}
