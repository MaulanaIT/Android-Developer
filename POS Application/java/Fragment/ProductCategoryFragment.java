package com.example.project.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.example.project.Activity.MainActivity;
import com.example.project.Model.ListProductCategory;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class ProductCategoryFragment extends Fragment {

    LinearLayout
            btnAddProductCategory;

    EditText
            inputSearch;

    List<ListProductCategory> listProductCategories;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager
            layoutManager;

    RecyclerView.Adapter adapter;

    int showAll;

    public ProductCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getContext()).setMyTitle("Product Category");

        btnAddProductCategory = getView().findViewById(R.id.btn_add_product_category);

        btnAddProductCategory.setOnClickListener(goToAddProductCategory);

        recyclerView = getView().findViewById(R.id.recycler_view);

        inputSearch = getView().findViewById(R.id.input_search);

        listProductCategories = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        showAll = 1;

        if (showAll == 1) {
            ((MainActivity) getContext()).getMysql("Data Product Category", adapter, recyclerView,
                    null, null, null, null, null, null, null,null,
                    listProductCategories, null, null, null, null, null, null, null,null);
        }
        inputSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showAll = 0;
                return false;
            }
        });

        inputSearch.addTextChangedListener(searchData);
    }

    private TextWatcher searchData = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (showAll == 0) {
                ((MainActivity) getContext()).searchMySql("Search Product Category", adapter, recyclerView,
                        null, null, null, null, null, null, null,
                        listProductCategories,null, null, inputSearch, null, null, null, null, null);
            }
        }
    };

    private View.OnClickListener goToAddProductCategory = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new AddProductCategoryFragment()).addToBackStack("Fragment").commit();
        }
    };
}
