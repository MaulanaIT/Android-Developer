package com.example.project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.Activity.MainActivity;
import com.example.project.Fragment.EditOutletFragment;
import com.example.project.Fragment.EditProductFragment;
import com.example.project.Fragment.OutletsSettingFragment;
import com.example.project.Fragment.ProductCategoryFragment;
import com.example.project.Model.ListCustomer;
import com.example.project.Model.ListOutlet;
import com.example.project.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutletListAdapter extends RecyclerView.Adapter<OutletListAdapter.ListViewHolder> {

    List<ListOutlet> listOutlets;
    Context context;

    private RequestQueue
            requestQueue;

    StringRequest
            stringRequest;

    public OutletListAdapter(List<ListOutlet> listOutlets, Context context) {
        super();

        this.listOutlets = listOutlets;
        this.context = context;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView
                outletName,
                outletAddress,
                outletContactNumber,
                outletStatus;

        Button
                btnEdit,
                btnDelete;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            outletName = itemView.findViewById(R.id.list_outlet_name);
            outletAddress = itemView.findViewById(R.id.list_outlet_address);
            outletContactNumber = itemView.findViewById(R.id.list_outlet_contact_number);
            outletStatus = itemView.findViewById(R.id.list_outlet_status);

            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }

    @NonNull
    @Override
    public OutletListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_outlet, parent, false);
        OutletListAdapter.ListViewHolder viewHolder = new OutletListAdapter.ListViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OutletListAdapter.ListViewHolder holder, int position) {
        final ListOutlet currentItem = listOutlets.get(position);

        holder.outletName.setText(currentItem.getOutletName());
        holder.outletAddress.setText(currentItem.getOutletAddress());
        holder.outletContactNumber.setText(String.valueOf(currentItem.getOutletContactNumber()));
        holder.outletStatus.setText(currentItem.getOutletStatus());
        if (holder.outletStatus.getText().toString().equals("Active")) {
            holder.outletStatus.setTextColor(ContextCompat.getColor(context, R.color.colorLime));
        } else {
            holder.outletStatus.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        }

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditOutletFragment.ID = currentItem.getOutletID();
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new EditOutletFragment()).addToBackStack("Fragment").commit();
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                requestQueue = Volley.newRequestQueue(context.getApplicationContext());

                stringRequest = new StringRequest(Request.Method.POST, ((MainActivity)context).setURLDeleteData("Outlet"),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.contains("Success")) {
                                    Toast.makeText(context, "Delete Succesful", Toast.LENGTH_SHORT).show();
                                    refreshFragment(view);
                                } else {
                                    Toast.makeText(context, "Delete Failed", Toast.LENGTH_SHORT).show();
                                    refreshFragment(view);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                                refreshFragment(view);
                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("ID", String.valueOf(currentItem.getOutletID()));
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOutlets.size();
    }

    private void refreshFragment(View view) {
        AppCompatActivity activity = (AppCompatActivity)view.getContext();
        activity.getSupportFragmentManager().popBackStack();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new OutletsSettingFragment()).addToBackStack("Fragment").commit();
    }
}