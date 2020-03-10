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
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.example.project.Activity.MainActivity;
import com.example.project.Model.ListCustomer;
import com.example.project.Model.ListUser;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class UsersSettingFragment extends Fragment {

    LinearLayout
            btnAddUsers;

    List<ListUser> listUsers;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager
            layoutManager;

    RecyclerView.Adapter adapter;

    public UsersSettingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getContext()).setMyTitle("Users Setting");

        btnAddUsers = getView().findViewById(R.id.btn_add_users);

        btnAddUsers.setOnClickListener(goToAddUser);

        recyclerView = getView().findViewById(R.id.recycler_view);

        listUsers = new ArrayList<>();
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        ((MainActivity)getContext()).getMysql("Data User", adapter, recyclerView,
                null, null, null, null, null, null, null,null,
                null, null, null,null, null, null, listUsers, null,null);
    }

    private View.OnClickListener goToAddUser = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new AddUserFragment()).addToBackStack("Fragment").commit();
        }
    };
}
