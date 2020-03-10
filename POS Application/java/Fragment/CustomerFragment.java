package com.example.project.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.project.Activity.MainActivity;
import com.example.project.Adapter.CustomerListAdapter;
import com.example.project.Model.ListCustomer;
import com.example.project.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerFragment extends Fragment {

    LinearLayout
            btnAddCustomer;

    Button
            btnSearch;

    EditText
            inputName,
            inputEmail,
            inputMobile;

    ProgressBar
            progressBar;

    List<ListCustomer> listCustomers;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager
            layoutManager;

    RecyclerView.Adapter adapter;

    RequestQueue
            requestQueue;

    StringRequest
            stringRequest;

    public CustomerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getContext()).setMyTitle("Customer");

        btnAddCustomer = getView().findViewById(R.id.btn_add_customer);
        btnSearch = getView().findViewById(R.id.btn_search);

        inputName = getView().findViewById(R.id.input_name);
        inputEmail = getView().findViewById(R.id.input_email);
        inputMobile = getView().findViewById(R.id.input_mobile);

        recyclerView = getView().findViewById(R.id.recycler_view);

        progressBar = getView().findViewById(R.id.progressBar);

        btnAddCustomer.setOnClickListener(goToAddCustomer);

        listCustomers = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        listAllCustomer();

        btnSearch.setOnClickListener(searchCustomer);
    }

    private View.OnClickListener goToAddCustomer = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new AddCustomerFragment()).addToBackStack("Fragment").commit();
        }
    };

    private View.OnClickListener searchCustomer = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (inputName.getText().toString().equals("") && inputEmail.getText().toString().equals("") &&
                    inputMobile.getText().toString().equals("")) {
                listAllCustomer();
            } else {
                searchDataCustomer();
            }
        }
    };

    private void searchDataCustomer() {
        ((MainActivity) getContext()).searchMySql("Search Customer", adapter, recyclerView,
                listCustomers, null, null, null, null, null, null,
                null,null, null, inputName, inputEmail, inputMobile, null, null, null);
    }

    private void listAllCustomer() {
        ((MainActivity) getContext()).getMysql("Data Customer", adapter, recyclerView, listCustomers, null,
                null, null, null, null, null,null, null, null,
                null, null, null, null, null, null,null);
    }
}
