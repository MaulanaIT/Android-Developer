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
import com.example.project.Fragment.EditSupplierFragment;
import com.example.project.Fragment.SuppliersSettingFragment;
import com.example.project.Model.ListSupplier;
import com.example.project.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierListAdapter extends RecyclerView.Adapter<SupplierListAdapter.ListViewHolder> {

    List<ListSupplier> listSuppliers;
    Context context;

    private RequestQueue
            requestQueue;

    StringRequest
            stringRequest;

    public SupplierListAdapter(List<ListSupplier> listSuppliers, Context context) {
        super();

        this.listSuppliers = listSuppliers;
        this.context = context;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView
                supplierName,
                supplierEmail,
                supplierTelephone,
                supplierFax,
                supplierStatus;

        Button
                btnEdit,
                btnDelete;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            supplierName = itemView.findViewById(R.id.list_supplier_name);
            supplierEmail = itemView.findViewById(R.id.list_supplier_email);
            supplierTelephone = itemView.findViewById(R.id.list_supplier_telephone);
            supplierFax = itemView.findViewById(R.id.list_supplier_fax);
            supplierStatus = itemView.findViewById(R.id.list_supplier_status);

            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }

    @NonNull
    @Override
    public SupplierListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_supplier, parent, false);
        SupplierListAdapter.ListViewHolder viewHolder = new SupplierListAdapter.ListViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SupplierListAdapter.ListViewHolder holder, int position) {
        final ListSupplier currentItem = listSuppliers.get(position);

        holder.supplierName.setText(currentItem.getSupplierName());
        holder.supplierEmail.setText(currentItem.getSupplierEmail());
        holder.supplierTelephone.setText(String.valueOf(currentItem.getSupplierTelephone()));
        holder.supplierFax.setText(currentItem.getSupplierFax());
        holder.supplierStatus.setText(currentItem.getSupplierStatus());
        if (holder.supplierStatus.getText().toString().equals("Active")) {
            holder.supplierStatus.setTextColor(ContextCompat.getColor(context, R.color.colorLime));
        } else {
            holder.supplierStatus.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        }

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditSupplierFragment.ID = currentItem.getSupplierID();
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new EditSupplierFragment()).addToBackStack("Fragment").commit();
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                requestQueue = Volley.newRequestQueue(context.getApplicationContext());

                stringRequest = new StringRequest(Request.Method.POST, ((MainActivity)context).setURLDeleteData("Supplier"),
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
                        params.put("ID", String.valueOf(currentItem.getSupplierID()));
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSuppliers.size();
    }

    private void refreshFragment(View view) {
        AppCompatActivity activity = (AppCompatActivity)view.getContext();
        activity.getSupportFragmentManager().popBackStack();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new SuppliersSettingFragment()).addToBackStack("Fragment").commit();
    }
}