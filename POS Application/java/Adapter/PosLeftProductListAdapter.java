package com.example.project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.example.project.Model.ListProduct;
import com.example.project.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PosLeftProductListAdapter extends RecyclerView.Adapter<PosLeftProductListAdapter.ListViewHolder> {

    public static ArrayList<String>
            productName = new ArrayList<>(),
            productCode = new ArrayList<>(),
            productPrice = new ArrayList<>(),
            productCost = new ArrayList<>(),
            productQuantity = new ArrayList<>(),
            productPayable = new ArrayList<>(),
            productImage = new ArrayList<>();

    int
            quantityProduct;

    List<ListProduct>
            listProducts;

    Context
            context;

    private RequestQueue
            requestQueue;

    StringRequest
            stringRequest;

    public PosLeftProductListAdapter(List<ListProduct> listProducts, Context context) {
        super();

        this.listProducts = listProducts;
        this.context = context;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        ImageView
                productImage;

        TextView
                productName;

        LinearLayout
                btnAdd;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.image_product);

            productName = itemView.findViewById(R.id.name_product);

            btnAdd = itemView.findViewById(R.id.btn_add);
        }
    }

    @NonNull
    @Override
    public PosLeftProductListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pos_product, parent, false);
        PosLeftProductListAdapter.ListViewHolder viewHolder = new PosLeftProductListAdapter.ListViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PosLeftProductListAdapter.ListViewHolder holder, int position) {
        final ListProduct currentItem = listProducts.get(position);

        holder.productName.setText(currentItem.getProductName());

        Picasso.get().load("https://fantastix.id/product_image/" + currentItem.getProductImage()).into(holder.productImage);

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productName.size() == 0) {
                    addItem(currentItem);
                } else {
                    int compareText = 0;
                    for (int i = 0; i < productName.size(); i++) {
                        if (productName.get(i).equals(currentItem.getProductName())) {
                            quantityProduct = Integer.valueOf(productQuantity.get(i));

                            quantityProduct = quantityProduct + 1;

                            productQuantity.set(i, String.valueOf(quantityProduct));
                            productPayable.set(i, String.valueOf(quantityProduct * Double.valueOf(currentItem.getProductPrice())));

                            compareText++;
                        }
                    }
                    if (compareText == 0) {
                        addItem(currentItem);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }

    public void addItem(ListProduct currentItem) {
        productName.add(currentItem.getProductName());
        productCode.add(currentItem.getProductCode());
        productPrice.add(currentItem.getProductPrice());
        productCost.add(currentItem.getProductCost());
        productQuantity.add("1");
        productPayable.add(currentItem.getProductPrice());
        productImage.add(currentItem.getProductImage());
    }
}