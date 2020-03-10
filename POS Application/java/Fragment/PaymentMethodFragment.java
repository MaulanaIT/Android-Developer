package com.example.project.Fragment;


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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.project.Activity.MainActivity;
import com.example.project.Model.ListExpenses;
import com.example.project.Model.ListPaymentMethod;
import com.example.project.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PaymentMethodFragment extends Fragment {

    LinearLayout
            btnAddPaymentMethod;

    ProgressBar
            progressBar;

    private SimpleDateFormat
            dateExpiredFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private Calendar
            calendar = Calendar.getInstance();

    List<ListPaymentMethod>
            listPaymentMethods;

    RecyclerView
            recyclerView;

    RecyclerView.LayoutManager
            layoutManager;

    RecyclerView.Adapter
            adapter;

    public PaymentMethodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_method, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("Payment Method");

        btnAddPaymentMethod = getView().findViewById(R.id.btn_add_payment_method);

        progressBar = getView().findViewById(R.id.progressBar);

        recyclerView = getView().findViewById(R.id.recycler_view);

        listPaymentMethods = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        ((MainActivity)getContext()).getMysql("Data Payment Method", adapter, recyclerView,
                null, null, null, null, null, null, null,null,
                null, null, null, null, null, null, null, null, listPaymentMethods);

        btnAddPaymentMethod.setOnClickListener(goToAdPaymentMethod);
    }

    private View.OnClickListener goToAdPaymentMethod = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new AddPaymentMethodFragment()).addToBackStack("Fragment").commit();
        }
    };
}
