package com.example.project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.example.project.Model.ListProduct;
import com.example.project.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PosRightProductListAdapter extends RecyclerView.Adapter<PosRightProductListAdapter.ListViewHolder> {

    List<ListProduct> listProducts;
    Context context;

    private RequestQueue
            requestQueue;

    StringRequest
            stringRequest;

    public PosRightProductListAdapter(List<ListProduct> listProducts, Context context) {
        super();

        this.listProducts = listProducts;
        this.context = context;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        ImageView
                productImage;

        TextView
                productName;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.image_product);

            productName = itemView.findViewById(R.id.name_product);
        }
    }

    @NonNull
    @Override
    public PosRightProductListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pos_product, parent, false);
        PosRightProductListAdapter.ListViewHolder viewHolder = new PosRightProductListAdapter.ListViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PosRightProductListAdapter.ListViewHolder holder, int position) {
        final ListProduct currentItem = listProducts.get(position);

        holder.productName.setText(currentItem.getProductName());

        Picasso.get().load("https://fantastix.id/product_image/" + currentItem.getProductImage()).into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }
}