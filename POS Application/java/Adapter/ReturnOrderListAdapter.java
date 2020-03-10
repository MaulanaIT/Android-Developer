package com.example.project.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.example.project.Fragment.AddPurchaseOrderFragment;
import com.example.project.Fragment.AddReturnOrderFragment;
import com.example.project.Model.ListProduct;
import com.example.project.Model.ListPurchaseOrderItem;
import com.example.project.Model.ListReturnOrder;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class ReturnOrderListAdapter extends RecyclerView.Adapter<ReturnOrderListAdapter.ListViewHolder> {

    List<ListReturnOrder> listReturnOrders;
    Context context;

    public static ArrayList<String>
            productID = new ArrayList<>(),
            productName = new ArrayList<>(),
            productCode = new ArrayList<>(),
            productQuantity = new ArrayList<>(),
            productCondition = new ArrayList<>();

    public ReturnOrderListAdapter(List<ListReturnOrder> listReturnOrders, Context context) {
        super();

        this.listReturnOrders = listReturnOrders;
        this.context = context;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {


        TextView
                productName,
                productCode;

        EditText
                productQuantity;

        Spinner
                productCondition;

        Button
                btnDelete;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.return_order_product_name);
            productCode = itemView.findViewById(R.id.return_order_product_code);

            productQuantity =itemView.findViewById(R.id.return_order_product_quantity);

            productCondition = itemView.findViewById(R.id.return_order_product_condition);

            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }

    @NonNull
    @Override
    public ReturnOrderListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_return_order, parent, false);
        ReturnOrderListAdapter.ListViewHolder viewHolder = new ReturnOrderListAdapter.ListViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ReturnOrderListAdapter.ListViewHolder holder, final int position) {
        final ListReturnOrder currentItem = listReturnOrders.get(position);

        if (productName.size() == 0) {
            addItem(currentItem, holder);
        } else {
            int compareText = 0;
            for (int i = 0; i < productName.size(); i++) {
                if (productName.get(i).equals(currentItem.getProductName())) {
                    compareText++;
                }
            }
            if (compareText == 0) {
                addItem(currentItem, holder);
            }
        }

        holder.productName.setText(currentItem.getProductName());
        holder.productCode.setText(currentItem.getProductCode());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listReturnOrders.remove(position);
                productID.remove(holder.getPosition());
                productName.remove(holder.getPosition());
                productCode.remove(holder.getPosition());
                productQuantity.remove(holder.getPosition());
                productCondition.remove(holder.getPosition());
                notifyDataSetChanged();
            }
        });

        holder.productQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (holder.productQuantity.getText().toString().equals("")) {
                    productQuantity.set(holder.getPosition(), "0");
                } else {
                    productQuantity.set(holder.getPosition(), holder.productQuantity.getText().toString());
                }
            }
        });

        holder.productCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                productCondition.set(position, String.valueOf(holder.productCondition.getSelectedItemPosition() + 1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    @Override
    public int getItemCount() {
        return listReturnOrders.size();
    }

    private void addItem(ListReturnOrder currentItem, ReturnOrderListAdapter.ListViewHolder holder) {
        productID.add(String.valueOf(currentItem.getProductID()));
        productName.add(currentItem.getProductName());
        productCode.add(currentItem.getProductCode());
        productQuantity.add("0");
        productCondition.add(String.valueOf(holder.productCondition.getSelectedItemPosition() + 1));
    }

}