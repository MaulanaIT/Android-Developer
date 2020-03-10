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
import com.example.project.Model.ListPurchaseOrderItem;
import com.example.project.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseOrderItemListAdapter extends RecyclerView.Adapter<PurchaseOrderItemListAdapter.ListViewHolder> {

    List<ListPurchaseOrderItem> listPurchaseOrderItems;
    Context context;

    private RequestQueue
            requestQueue;

    StringRequest
            stringRequest;

    public PurchaseOrderItemListAdapter(List<ListPurchaseOrderItem> listPurchaseOrderItems, Context context) {
        super();

        this.listPurchaseOrderItems = listPurchaseOrderItems;
        this.context = context;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {


        TextView
                productName,
                productCode,
                productQuantity,
                productCost;

        Button
                btnDelete;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.purchase_order_product_name);
            productCode = itemView.findViewById(R.id.purchase_order_product_code);
            productQuantity = itemView.findViewById(R.id.purchase_order_product_quantity);
            productCost = itemView.findViewById(R.id.purchase_order_product_cost);

            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }

    @NonNull
    @Override
    public PurchaseOrderItemListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_purchase_order_item, parent, false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseOrderItemListAdapter.ListViewHolder holder, final int position) {
        final ListPurchaseOrderItem currentItem = listPurchaseOrderItems.get(position);

        holder.productName.setText(currentItem.getProductName());
        holder.productCode.setText(currentItem.getProductCode());
        holder.productQuantity.setText(String.valueOf(currentItem.getProductQuantity()));
        holder.productCost.setText(String.valueOf(currentItem.getProductCost()));

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listPurchaseOrderItems.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPurchaseOrderItems.size();
    }
}