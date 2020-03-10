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
import android.widget.LinearLayout;

import com.example.project.Activity.MainActivity;
import com.example.project.Model.ListExpenses;
import com.example.project.Model.ListExpensesCategory;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class ExpensesCategoryFragment extends Fragment {

    LinearLayout
            btnAddExpensesCategory;

    List<ListExpensesCategory> listExpensesCategories;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager
            layoutManager;

    RecyclerView.Adapter adapter;

    public ExpensesCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expenses_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("Expenses Category");

        btnAddExpensesCategory = getView().findViewById(R.id.btn_add_expanses);

        btnAddExpensesCategory.setOnClickListener(goToAddExpansesCategory);

        recyclerView = getView().findViewById(R.id.recycler_view);

        listExpensesCategories = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        ((MainActivity)getContext()).getMysql("Data Expenses Category", adapter, recyclerView,
                null, null, null, null, listExpensesCategories, null, null,null,
                null, null, null, null, null, null, null, null,null);
    }

    private View.OnClickListener goToAddExpansesCategory = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new AddExpensesCategoryFragment()).addToBackStack("Fragment").commit();
        }
    };
}
