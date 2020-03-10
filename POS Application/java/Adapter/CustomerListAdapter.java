package com.example.project.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.project.Activity.MainActivity;
import com.example.project.Fragment.AddCustomerFragment;
import com.example.project.Fragment.CustomerFragment;
import com.example.project.Fragment.EditCustomerFragment;
import com.example.project.Model.ListCustomer;
import com.example.project.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.ListViewHolder> {

    List<ListCustomer> listCustomers;
    Context context;

    public CustomerListAdapter(List<ListCustomer> listCustomers, Context context) {
        super();

        this.listCustomers = listCustomers;
        this.context = context;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView
                customerName,
                customerEmail,
                customerMobile,
                customerId;

        Button
                customerEdit;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            customerName = itemView.findViewById(R.id.list_customer_name);
            customerEmail = itemView.findViewById(R.id.list_customer_email);
            customerMobile = itemView.findViewById(R.id.list_customer_mobile);
            customerId = itemView.findViewById(R.id.list_customer_id);

            customerEdit = itemView.findViewById(R.id.btn_edit);
        }
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_customer, parent, false);
        CustomerListAdapter.ListViewHolder viewHolder = new CustomerListAdapter.ListViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        final ListCustomer currentItem = listCustomers.get(position);

        holder.customerId.setText(String.valueOf(currentItem.getCustomerId()));
        holder.customerName.setText(currentItem.getCustomerName());
        holder.customerEmail.setText(currentItem.getCustomerEmail());
        holder.customerMobile.setText(String.valueOf(currentItem.getCustomerMobile()));

        holder.customerEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditCustomerFragment.ID = currentItem.getCustomerId();
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new EditCustomerFragment()).addToBackStack("Fragment").commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCustomers.size();
    }
}
