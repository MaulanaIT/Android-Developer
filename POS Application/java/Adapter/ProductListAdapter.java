package com.example.project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.project.Fragment.EditInventoryFragment;
import com.example.project.Fragment.EditProductFragment;
import com.example.project.Fragment.ExpensesFragment;
import com.example.project.Fragment.ListProductFragment;
import com.example.project.Model.ListCustomer;
import com.example.project.Model.ListProduct;
import com.example.project.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ListViewHolder> {

    List<ListProduct> listProducts;
    Context context;

    private RequestQueue
            requestQueue;

    StringRequest
            stringRequest;

    public ProductListAdapter(List<ListProduct> listProducts, Context context) {
        super();

        this.listProducts = listProducts;
        this.context = context;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        ImageView
                productImage;

        TextView
                productCode,
                productName,
                productCategory,
                productCost,
                productPrice,
                productStatus;

        Button
                btnEdit,
                btnDelete;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.list_product_image);

            productCode = itemView.findViewById(R.id.list_product_code);
            productName = itemView.findViewById(R.id.list_product_name);
            productCategory = itemView.findViewById(R.id.list_product_category);
            productCost = itemView.findViewById(R.id.list_product_cost);
            productPrice = itemView.findViewById(R.id.list_product_price);
            productStatus = itemView.findViewById(R.id.list_product_status);

            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }

    @NonNull
    @Override
    public ProductListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product, parent, false);
        ProductListAdapter.ListViewHolder viewHolder = new ProductListAdapter.ListViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ListViewHolder holder, int position) {
        final ListProduct currentItem = listProducts.get(position);

        holder.productCode.setText(currentItem.getProductCode());
        holder.productName.setText(currentItem.getProductName());
        holder.productCategory.setText(String.valueOf(currentItem.getProductCategory()));
        holder.productCost.setText(currentItem.getProductCost());
        holder.productPrice.setText(currentItem.getProductPrice());
        holder.productStatus.setText(currentItem.getProductStatus());
        if (holder.productStatus.getText().toString().equals("Active")) {
            holder.productStatus.setTextColor(ContextCompat.getColor(context, R.color.colorLime));
        } else {
            holder.productStatus.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        }

        Picasso.get().load("https://fantastix.id/product_image/" + currentItem.getProductImage()).into(holder.productImage);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditProductFragment.ID = currentItem.getProductID();
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new EditProductFragment()).addToBackStack("Fragment").commit();
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                requestQueue = Volley.newRequestQueue(context.getApplicationContext());

                stringRequest = new StringRequest(Request.Method.POST, ((MainActivity)context).setURLDeleteData("Product"),
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
                        params.put("ID", String.valueOf(currentItem.getProductID()));
                        params.put("Image", currentItem.getProductImage());
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }

    private void refreshFragment(View view) {
        AppCompatActivity activity = (AppCompatActivity)view.getContext();
        activity.getSupportFragmentManager().popBackStack();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new ListProductFragment()).addToBackStack("Fragment").commit();
    }
}