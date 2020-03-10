package com.example.project.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.example.project.Fragment.EditInventoryFragment;
import com.example.project.Model.ListEditInventory;
import com.example.project.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EditInventoryListAdapter extends RecyclerView.Adapter<EditInventoryListAdapter.ListViewHolder> {

    public static List<ListEditInventory> listEditInventories;
    Context context;

    public static ArrayList<String>
            productID = new ArrayList<>(),
            productQuantity = new ArrayList<>();

    public EditInventoryListAdapter(List<ListEditInventory> listEditInventories, Context context) {
        super();

        this.listEditInventories = listEditInventories;
        this.context = context;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView
                inventoryOutletName;

        EditText
                inputQuantity;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            inventoryOutletName = itemView.findViewById(R.id.outlets_name);

            inputQuantity = itemView.findViewById(R.id.edit_quantity);
        }
    }

    @NonNull
    @Override
    public EditInventoryListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_edit_inventory, parent, false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EditInventoryListAdapter.ListViewHolder holder, final int position) {
        final ListEditInventory currentItem = listEditInventories.get(position);

        productID.add(String.valueOf(currentItem.getInventoryID()));
        productQuantity.add(String.valueOf(currentItem.getInventoryQuantity()));

        holder.inventoryOutletName.setText(currentItem.getOutletName());
        holder.inputQuantity.setText(String.valueOf(currentItem.getInventoryQuantity()));

        holder.inputQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                productID.set(position, String.valueOf(currentItem.getInventoryID()));
                productQuantity.set(position, holder.inputQuantity.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listEditInventories.size();
    }
}