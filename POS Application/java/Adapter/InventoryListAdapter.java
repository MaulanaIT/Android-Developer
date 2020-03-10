package com.example.project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Fragment.EditCustomerFragment;
import com.example.project.Fragment.EditInventoryFragment;
import com.example.project.Model.ListEditInventory;
import com.example.project.Model.ListInventory;
import com.example.project.R;

import java.util.List;

public class InventoryListAdapter extends RecyclerView.Adapter<InventoryListAdapter.ListViewHolder> {

    List<ListInventory> listInventories;
    Context context;

    public InventoryListAdapter(List<ListInventory> listInventories, Context context) {
        super();

        this.listInventories = listInventories;
        this.context = context;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView
                inventoryProductCode,
                inventoryName,
                inventoryQuantity;

        Button
                inventoryEdit;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            inventoryProductCode = itemView.findViewById(R.id.list_inventory_product_code);
            inventoryName = itemView.findViewById(R.id.list_inventory_name);
            inventoryQuantity = itemView.findViewById(R.id.list_inventory_quantity);

            inventoryEdit = itemView.findViewById(R.id.btn_edit);
        }
    }

    @NonNull
    @Override
    public InventoryListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_inventory, parent, false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryListAdapter.ListViewHolder holder, final int position) {
        final ListInventory currentItem = listInventories.get(position);

        holder.inventoryProductCode.setText(currentItem.getInventoryProductCode());
        holder.inventoryName.setText(currentItem.getInventoryName());
        holder.inventoryQuantity.setText(String.valueOf(currentItem.getInventoryQuantity()));

        holder.inventoryEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditInventoryFragment.productCode = currentItem.getInventoryProductCode();
                EditInventoryFragment.productName = currentItem.getInventoryName();
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                EditInventoryListAdapter.productID.clear();
                EditInventoryListAdapter.productQuantity.clear();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new EditInventoryFragment()).addToBackStack("Fragment").commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listInventories.size();
    }
}