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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.project.Activity.MainActivity;
import com.example.project.Model.ListProduct;
import com.example.project.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListProductFragment extends Fragment {

    LinearLayout
            btnAddProduct;

    Button
            btnSearch;

    EditText
            inputProductCode,
            inputProductName;

    Spinner
            categorySpinner;

    ProgressBar
            progressBar;

    List<ListProduct>
            listProducts;

    RecyclerView
            recyclerView;

    RecyclerView.LayoutManager
            layoutManager;

    RecyclerView.Adapter
            adapter;

    public ListProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("List Product");

        btnAddProduct = getView().findViewById(R.id.btn_add_product);
        btnSearch = getView().findViewById(R.id.btn_search);

        inputProductCode = getView().findViewById(R.id.input_product_code);
        inputProductName = getView().findViewById(R.id.input_product_name);

        categorySpinner = getView().findViewById(R.id.product_category_spinner);

        progressBar = getView().findViewById(R.id.progressBar);

        btnAddProduct.setOnClickListener(goToAddProduct);

        recyclerView = getView().findViewById(R.id.recycler_view);

        listProducts = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        btnSearch.setOnClickListener(searchProduct);

        ((MainActivity)getContext()).getMysql("Data Product", adapter, recyclerView,
                null, null, null, null, null, null, null,null,
                null, null, listProducts, null, null, null, null, null,null);

        ((MainActivity)getContext()).setSpinner(((MainActivity)getContext()).setURLReadData("Product Category"), "category", categorySpinner);
    }

    private View.OnClickListener goToAddProduct = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new AddProductFragment()).addToBackStack("Fragment").commit();
        }
    };

    private View.OnClickListener searchProduct = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            searchDataProduct();
        }
    };

    private void searchDataProduct() {
        ((MainActivity) getContext()).searchMySql("Search Product", adapter, recyclerView,
                null, null, null, null, null, null, listProducts,
                null,null, null, inputProductCode, inputProductName, null, categorySpinner, null, null);
    }
}
