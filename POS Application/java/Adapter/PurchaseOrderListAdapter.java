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
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.Activity.MainActivity;
import com.example.project.Fragment.AddPurchaseOrderFragment;
import com.example.project.Fragment.PurchaseOrderFragment;
import com.example.project.Model.ListPurchaseOrder;
import com.example.project.Model.ListPurchaseOrderItem;
import com.example.project.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseOrderListAdapter extends RecyclerView.Adapter<PurchaseOrderListAdapter.ListViewHolder> {

    List<ListPurchaseOrder> listPurchaseOrders;
    Context context;

    private RequestQueue
            requestQueue;

    StringRequest
            stringRequest;

    public PurchaseOrderListAdapter(List<ListPurchaseOrder> listPurchaseOrders, Context context) {
        super();

        this.listPurchaseOrders = listPurchaseOrders;
        this.context = context;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {


        TextView
                purchaseOrderNumber,
                purchaseOderOutlet,
                purchaseOrderSupplier,
                purchaseOrderDate,
                purchaseOrderStatus;

        Button
                btnView,
                btnEdit,
                btnReceive,
                btnDelete;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            purchaseOrderNumber = itemView.findViewById(R.id.list_purchase_order_number);
            purchaseOderOutlet = itemView.findViewById(R.id.list_purchase_order_outlet);
            purchaseOrderSupplier = itemView.findViewById(R.id.list_purchase_order_supplier);
            purchaseOrderDate = itemView.findViewById(R.id.list_purchase_order_date);
            purchaseOrderStatus = itemView.findViewById(R.id.list_purchase_order_status);

            btnReceive = itemView.findViewById(R.id.btn_receive);
            btnView = itemView.findViewById(R.id.btn_view);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }

    @NonNull
    @Override
    public PurchaseOrderListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_purchase_order, parent, false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseOrderListAdapter.ListViewHolder holder, int position) {
        final ListPurchaseOrder currentItem = listPurchaseOrders.get(position);

        if (currentItem.getPurchaseOrderStatusCode() == 1) {
            holder.btnReceive.setVisibility(View.GONE);
            holder.btnView.setVisibility(View.GONE);
            holder.btnEdit.setVisibility(View.VISIBLE);
            holder.btnDelete.setVisibility(View.VISIBLE);
        } else if (currentItem.getPurchaseOrderStatusCode() == 2) {
            holder.btnReceive.setVisibility(View.VISIBLE);
            holder.btnView.setVisibility(View.VISIBLE);
            holder.btnEdit.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.VISIBLE);
        } else {
            holder.btnReceive.setVisibility(View.GONE);
            holder.btnView.setVisibility(View.VISIBLE);
            holder.btnEdit.setVisibility(View.GONE);
            holder.btnDelete.setVisibility(View.GONE);
        }

        holder.purchaseOrderNumber.setText(currentItem.getPurchaseOrderNumber());
        holder.purchaseOderOutlet.setText(currentItem.getPurchaseOrderOutlet());
        holder.purchaseOrderSupplier.setText(currentItem.getPurchaseOrderSupplier());
        holder.purchaseOrderDate.setText(currentItem.getPurchaseOrderDate());
        holder.purchaseOrderStatus.setText(currentItem.getPurchaseOrderStatus());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                requestQueue = Volley.newRequestQueue(context.getApplicationContext());

                stringRequest = new StringRequest(Request.Method.POST, ((MainActivity)context).setURLDeleteData("Purchase Order"),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.contains("Success")) {
                                    Toast.makeText(context, "Delete Successful", Toast.LENGTH_SHORT).show();
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
                        params.put("ID", String.valueOf(currentItem.getPurchaseOrderID()));
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPurchaseOrders.size();
    }

    private void refreshFragment(View view) {
        AppCompatActivity activity = (AppCompatActivity)view.getContext();
        activity.getSupportFragmentManager().popBackStack();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new PurchaseOrderFragment()).addToBackStack("Fragment").commit();
    }
}