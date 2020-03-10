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

import com.example.project.Activity.MainActivity;
import com.example.project.Model.ListProduct;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class PosListProductFragment extends Fragment {

    List<ListProduct>
            listProductsLeft,
            listProductsMid,
            listProductsRight;

    RecyclerView
            recyclerViewLeft,
            recyclerViewMid,
            recyclerViewRight;

    RecyclerView.Adapter
            adapterLeft,
            adapterMid,
            adapterRight;

    RecyclerView.LayoutManager
            layoutManagerLeft,
            layoutManagerMid,
            layoutManagerRight;

    public PosListProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pos_list_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getContext()).setMyTitle("POS List Product");

        recyclerViewLeft = getView().findViewById(R.id.recycler_view_left);
        recyclerViewMid = getView().findViewById(R.id.recycler_view_minddle);
        recyclerViewRight = getView().findViewById(R.id.recycler_view_right);

        listProductsLeft = new ArrayList<>();
        listProductsMid = new ArrayList<>();
        listProductsRight = new ArrayList<>();

        layoutManagerLeft = new LinearLayoutManager(getActivity());
        layoutManagerMid = new LinearLayoutManager(getActivity());
        layoutManagerRight = new LinearLayoutManager(getActivity());

        recyclerViewLeft.setHasFixedSize(true);
        recyclerViewMid.setHasFixedSize(true);
        recyclerViewRight.setHasFixedSize(true);

        recyclerViewLeft.setLayoutManager(layoutManagerLeft);
        recyclerViewMid.setLayoutManager(layoutManagerMid);
        recyclerViewRight.setLayoutManager(layoutManagerRight);

        ((MainActivity) getContext()).getMySqlPosProduct(listProductsLeft, listProductsMid, listProductsRight, adapterLeft,
                adapterMid, adapterRight, recyclerViewLeft, recyclerViewMid, recyclerViewRight);
    }
}
