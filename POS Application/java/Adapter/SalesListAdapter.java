package com.example.project.Adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.Activity.MainActivity;
import com.example.project.Fragment.ListGiftCardFragment;
import com.example.project.Fragment.SalesFragment;
import com.example.project.Model.ListSales;
import com.example.project.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesListAdapter extends RecyclerView.Adapter<SalesListAdapter.ListViewHolder> {

    List<ListSales>
            listSales;

    Context
            context;

    private RequestQueue
            requestQueue;

    StringRequest
            stringRequest;

    public SalesListAdapter(List<ListSales> listSales, Context context) {
        super();

        this.listSales = listSales;
        this.context = context;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView
                salesID,
                salesDate,
                saledCustomer,
                salesOutlet,
                salesMethod,
                salesItem,
                salesTotal,
                salesTax,
                salesPayable;

        Button
                btnDelete;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            salesID = itemView.findViewById(R.id.list_sales_id);
            salesDate = itemView.findViewById(R.id.list_sales_date);
            saledCustomer = itemView.findViewById(R.id.list_sales_customer);
            salesOutlet = itemView.findViewById(R.id.list_sales_outlet);
            salesMethod = itemView.findViewById(R.id.list_sales_method);
            salesItem = itemView.findViewById(R.id.list_sales_item);
            salesTotal = itemView.findViewById(R.id.list_sales_total);
            salesTax = itemView.findViewById(R.id.list_sales_tax);
            salesPayable = itemView.findViewById(R.id.list_sales_payable);

            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }

    @NonNull
    @Override
    public SalesListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_sales, parent, false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalesListAdapter.ListViewHolder holder, int position) {
        final ListSales currentItem = listSales.get(position);

        holder.salesDate.setText(currentItem.getSalesDate());
        holder.salesID.setText(String.valueOf(currentItem.getSalesID()));
        holder.saledCustomer.setText(currentItem.getSaledCustomer());
        holder.salesOutlet.setText(currentItem.getSalesOutlet());
        holder.salesMethod.setText(currentItem.getSalesMethod());
        holder.salesItem.setText(currentItem.getSalesItem());
        holder.salesTotal.setText(currentItem.getSalesTotal());
        holder.salesTax.setText(currentItem.getSalesTax());
        holder.salesPayable.setText(currentItem.getSalesPayable());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                requestQueue = Volley.newRequestQueue(context.getApplicationContext());

                stringRequest = new StringRequest(Request.Method.POST, ((MainActivity)context).setURLDeleteData("Sales"),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.contains("Success")) {
                                    Toast.makeText(context, "Delete Succesful", Toast.LENGTH_SHORT).show();
                                    refreshFragment(v);
                                } else {
                                    Toast.makeText(context, "Delete Failed", Toast.LENGTH_SHORT).show();
                                    refreshFragment(v);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                                refreshFragment(v);
                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("ID", String.valueOf(currentItem.getSalesID()));
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSales.size();
    }

    private void refreshFragment(View view) {
        AppCompatActivity activity = (AppCompatActivity)view.getContext();
        activity.getSupportFragmentManager().popBackStack();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new SalesFragment()).addToBackStack("Fragment").commit();
    }
}
