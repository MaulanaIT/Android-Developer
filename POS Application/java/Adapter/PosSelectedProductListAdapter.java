package com.example.project.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Activity.PosCashierBarcodeActivity;
import com.example.project.Fragment.PosCashierFragment;
import com.example.project.Model.ListPosProduct;
import com.example.project.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PosSelectedProductListAdapter extends RecyclerView.Adapter<PosSelectedProductListAdapter.ListViewHolder> {

    private List<ListPosProduct>
            listPosProducts;

    Context
            context;

    public PosSelectedProductListAdapter(List<ListPosProduct> listPosProducts, Context context) {
        super();

        this.listPosProducts = listPosProducts;
        this.context = context;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView
                productName,
                productCode,
                productPrice,
                productQuantity,
                productPayable;

        ImageView
                productImage;

        Button
                btnDelete;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.cashier_name_product);
            productCode = itemView.findViewById(R.id.cashier_code_product);
            productPrice = itemView.findViewById(R.id.cashier_price_product);
            productQuantity = itemView.findViewById(R.id.cashier_quantity_product);
            productPayable = itemView.findViewById(R.id.cashier_payable_product);

            productImage = itemView.findViewById(R.id.list_product_image);

            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }

    @NonNull
    @Override
    public PosSelectedProductListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pos_product_selected, parent, false);

        return new ListViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final PosSelectedProductListAdapter.ListViewHolder holder, int position) {
        final ListPosProduct currentItem = listPosProducts.get(position);

        holder.productName.setText(currentItem.getProductName());
        holder.productCode.setText(currentItem.getProductCode());
        holder.productPrice.setText("Rp " + currentItem.getProductPrice() + ",-");
        holder.productQuantity.setText(currentItem.getProductQuantity());
        holder.productPayable.setText("Rp " + currentItem.getProductPayable() + ",-");

        Picasso.get().load("https://fantastix.id/product_image/" + currentItem.getProductImage()).into(holder.productImage);

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listPosProducts.remove(holder.getPosition());
                if (PosLeftProductListAdapter.productName.size() > 0) {
                    PosLeftProductListAdapter.productName.remove(holder.getPosition());
                    PosLeftProductListAdapter.productCode.remove(holder.getPosition());
                    PosLeftProductListAdapter.productPrice.remove(holder.getPosition());
                    PosLeftProductListAdapter.productCost.remove(holder.getPosition());
                    PosLeftProductListAdapter.productQuantity.remove(holder.getPosition());
                    PosLeftProductListAdapter.productPayable.remove(holder.getPosition());
                    notifyDataSetChanged();
                }

                if (PosCashierBarcodeActivity.productName.size() > 0) {
                    PosCashierBarcodeActivity.productName.remove(holder.getPosition());
                    PosCashierBarcodeActivity.productCode.remove(holder.getPosition());
                    PosCashierBarcodeActivity.productPrice.remove(holder.getPosition());
                    PosCashierBarcodeActivity.productCost.remove(holder.getPosition());
                    PosCashierBarcodeActivity.productQuantity.remove(holder.getPosition());
                    PosCashierBarcodeActivity.productPayable.remove(holder.getPosition());
                    notifyDataSetChanged();
                }

                refreshFragment(view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPosProducts.size();
    }

    private void refreshFragment(View view) {
        AppCompatActivity activity = (AppCompatActivity)view.getContext();
        activity.getSupportFragmentManager().popBackStack();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new PosCashierFragment()).addToBackStack("Fragment").commit();
    }
}