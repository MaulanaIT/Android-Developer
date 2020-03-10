package com.example.project.Fragment;


import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.EditText;

import com.example.project.Activity.MainActivity;
import com.example.project.Model.ListCustomer;
import com.example.project.Model.ListGiftCard;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class ListGiftCardFragment extends Fragment {

    EditText
            inputSearch;

    List<ListGiftCard>
            listGiftCards;

    RecyclerView
            recyclerView;

    RecyclerView.LayoutManager
            layoutManager;

    RecyclerView.Adapter
            adapter;

    int showAll = 1;

    public ListGiftCardFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_gift_card, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("List Gift Card");

        recyclerView = getView().findViewById(R.id.recycler_view);

        inputSearch = getView().findViewById(R.id.input_search);

        recyclerView = getView().findViewById(R.id.recycler_view);

        listGiftCards = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        if (showAll == 1) {
            ((MainActivity)getContext()).getMysql("Data Gift Card", adapter, recyclerView,
                    null, null, listGiftCards, null,null, null, null,null,
                    null, null, null, null, null, null, null, null,null);
        }

        inputSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showAll = 0;
                return false;
            }
        });

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (showAll == 0) {
                    ((MainActivity) getContext()).searchMySql("Search Gift Card", adapter, recyclerView,
                            null, null, listGiftCards,null, null, null, null,
                            null,null, null, inputSearch, null, null, null, null, null);
                }
            }
        });
    }
}
