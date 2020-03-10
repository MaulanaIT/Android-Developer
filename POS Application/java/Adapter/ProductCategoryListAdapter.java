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
import com.example.project.Fragment.EditCustomerFragment;
import com.example.project.Fragment.EditProductCategoryFragment;
import com.example.project.Fragment.EditProductFragment;
import com.example.project.Fragment.ExpensesFragment;
import com.example.project.Fragment.ProductCategoryFragment;
import com.example.project.Model.ListExpensesCategory;
import com.example.project.Model.ListProductCategory;
import com.example.project.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductCategoryListAdapter extends RecyclerView.Adapter<ProductCategoryListAdapter.ListViewHolder> {

    List<ListProductCategory> listProductCategories;
    Context context;

    private RequestQueue
            requestQueue;

    StringRequest
            stringRequest;

    public ProductCategoryListAdapter(List<ListProductCategory> listProductCategories, Context context) {
        super();

        this.listProductCategories = listProductCategories;
        this.context = context;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView
                productCategoryName,
                productCategoryStatus;

        Button
                btnEdit,
                btnDelete;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            productCategoryName = itemView.findViewById(R.id.list_product_category_name);
            productCategoryStatus = itemView.findViewById(R.id.list_product_category_status);

            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }

    @NonNull
    @Override
    public ProductCategoryListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product_category, parent, false);
        ProductCategoryListAdapter.ListViewHolder viewHolder = new ProductCategoryListAdapter.ListViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCategoryListAdapter.ListViewHolder holder, int position) {
        final ListProductCategory currentItem = listProductCategories.get(position);

        holder.productCategoryName.setText(currentItem.getProductCategoryName());
        holder.productCategoryStatus.setText(currentItem.getProductCategoryStatus());
        if (holder.productCategoryStatus.getText().toString().equals("Active")) {
            holder.productCategoryStatus.setTextColor(ContextCompat.getColor(context, R.color.colorLime));
        } else {
            holder.productCategoryStatus.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        }

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditProductCategoryFragment.ID = currentItem.getProductCategoryID();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new EditProductCategoryFragment()).addToBackStack("Fragment").commit();
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                requestQueue = Volley.newRequestQueue(context.getApplicationContext());

                stringRequest = new StringRequest(Request.Method.POST, ((MainActivity)context).setURLDeleteData("Product Category"),
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
                        params.put("ID", String.valueOf(currentItem.getProductCategoryID()));
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProductCategories.size();
    }

    private void refreshFragment(View view) {
        AppCompatActivity activity = (AppCompatActivity)view.getContext();
        activity.getSupportFragmentManager().popBackStack();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProductCategoryFragment()).addToBackStack("Fragment").commit();
    }
}