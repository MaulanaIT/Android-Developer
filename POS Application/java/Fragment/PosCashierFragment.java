package com.example.project.Fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.Activity.MainActivity;
import com.example.project.Activity.PosCashierBarcodeActivity;
import com.example.project.Adapter.PosLeftProductListAdapter;
import com.example.project.Adapter.PosSelectedProductListAdapter;
import com.example.project.Model.ListPosProduct;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class PosCashierFragment extends Fragment {

    PosCashierBarcodeActivity
            posCashierBarcodeActivity;

    Button
            btnList,
            btnCancel,
            btnHold,
            btnPayment,
            btnClear,
            btnRefresh;

    EditText
            inputNameOrCode,
            inputDiscount;

    TextView
            inputTotal,
            inputTotalItem,
            inputTax,
            inputTotalPayable,
            textTax;

    public static EditText
            inputBarcode;

    public static int
            ID,
            totalItem;

    public static double
            totalPrice,
            totalCost,
            tax,
            discount,
            totalPayable;

    public static ArrayList<String>
            productName,
            productCode,
            productPrice,
            productCost,
            productQuantity,
            productPayable,
            productImage;

    String
            PREFS_NAME = "MyPrefsFile";

    SharedPreferences
            systemSetting;

    List<ListPosProduct>
            listPosProducts;

    RecyclerView
            recyclerView;

    RecyclerView.Adapter
            adapter;

    RecyclerView.LayoutManager
            layoutManager;

    public PosCashierFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pos_cashier, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getContext()).setMyTitle("Cashier");

        systemSetting = getActivity().getSharedPreferences(PREFS_NAME, 0);

        posCashierBarcodeActivity = new PosCashierBarcodeActivity();

        totalPrice = 0;
        tax = 0;
        totalPayable = 0;
        totalItem = 0;
        discount = 0;

        btnList = getView().findViewById(R.id.btn_list);
        btnCancel = getView().findViewById(R.id.btn_cancel);
        btnHold = getView().findViewById(R.id.btn_hold);
        btnPayment = getView().findViewById(R.id.btn_payment);
        btnRefresh = getView().findViewById(R.id.btn_refresh);

        inputBarcode = getView().findViewById(R.id.input_barcode);
        inputDiscount = getView().findViewById(R.id.input_discount);

        inputTotal = getView().findViewById(R.id.input_total);
        inputTotalItem = getView().findViewById(R.id.input_total_items);
        inputTax = getView().findViewById(R.id.input_tax);
        inputTotalPayable = getView().findViewById(R.id.input_total_payable);

        textTax = getView().findViewById(R.id.text_tax);

        recyclerView = getView().findViewById(R.id.recycler_view);

        listPosProducts = new ArrayList<>();
        productName = new ArrayList<>();
        productCode = new ArrayList<>();
        productPrice = new ArrayList<>();
        productCost = new ArrayList<>();
        productQuantity = new ArrayList<>();
        productPayable = new ArrayList<>();
        productImage = new ArrayList<>();

        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PosSelectedProductListAdapter(listPosProducts, getActivity());

        if (PosLeftProductListAdapter.productName.size() > 0) {
            int compareText = 0;
            if (productName.size() > 0) {
                for (int i = 0; i < PosLeftProductListAdapter.productName.size(); i++) {
                    if (productName.get(i).equals(PosLeftProductListAdapter.productName.get(i))) {
                        productQuantity.set(i, String.valueOf(Integer.valueOf(productQuantity.get(i)) + Integer.valueOf(PosLeftProductListAdapter.productQuantity.get(i))));
                        productPayable.set(i, String.valueOf(Double.valueOf(productPayable.get(i)) + Double.valueOf(PosLeftProductListAdapter.productPayable.get(i))));

                        compareText++;
                    }
                }
            }
            if (compareText == 0) {
                for (int i = 0; i < PosLeftProductListAdapter.productName.size(); i++) {
                    productName.add(PosLeftProductListAdapter.productName.get(i));
                    productCode.add(PosLeftProductListAdapter.productCode.get(i));
                    productPrice.add(PosLeftProductListAdapter.productPrice.get(i));
                    productCost.add(PosLeftProductListAdapter.productCost.get(i));
                    productQuantity.add(PosLeftProductListAdapter.productQuantity.get(i));
                    productPayable.add(PosLeftProductListAdapter.productPayable.get(i));
                    productImage.add(PosLeftProductListAdapter.productImage.get(i));
                }
            }
        }

        if (posCashierBarcodeActivity.setName().size() > 0) {
            int compareText = 0;
            if (productName.size() > 0) {
                for (int i = 0; i < posCashierBarcodeActivity.setName().size(); i++) {
                    if (productName.get(i).equals(posCashierBarcodeActivity.setName().get(i))) {
                        productQuantity.set(i, String.valueOf(Integer.valueOf(productQuantity.get(i)) + Integer.valueOf(posCashierBarcodeActivity.setQuantity().get(i))));
                        productPayable.set(i, String.valueOf(Double.valueOf(productPayable.get(i)) + Double.valueOf(posCashierBarcodeActivity.setPayable().get(i))));

                        compareText++;
                    }
                }
            }
            if (compareText == 0) {
                for (int i = 0; i < posCashierBarcodeActivity.setName().size(); i++) {
                    productName.add(posCashierBarcodeActivity.setName().get(i));
                    productCode.add(posCashierBarcodeActivity.setCode().get(i));
                    productPrice.add(posCashierBarcodeActivity.setPrice().get(i));
                    productCost.add(posCashierBarcodeActivity.setCost().get(i));
                    productQuantity.add(posCashierBarcodeActivity.setQuantity().get(i));
                    productPayable.add(posCashierBarcodeActivity.setPayable().get(i));
                    productImage.add(posCashierBarcodeActivity.setImage().get(i));
                }
            }
        }

        if (productName.size() > 0) {
            for (int i = 0; i < productName.size(); i++) {
                ListPosProduct selectedItem = new ListPosProduct();

                selectedItem.setProductName(productName.get(i));
                selectedItem.setProductCode(productCode.get(i));
                selectedItem.setProductPrice(productPrice.get(i));
                selectedItem.setProductCost(productCost.get(i));
                selectedItem.setProductQuantity(productQuantity.get(i));
                selectedItem.setProductPayable(productPayable.get(i));
                selectedItem.setProductImage(productImage.get(i));

                listPosProducts.add(selectedItem);

                totalPrice = totalPrice + (Double.valueOf(productPrice.get(i)) * Integer.valueOf(productQuantity.get(i)));
                totalCost = totalCost + (Double.valueOf(productCost.get(i)) * Integer.valueOf(productQuantity.get(i)));
                totalItem = totalItem + Integer.valueOf(productQuantity.get(i));
            }
        }

        tax = (totalPrice * Double.valueOf(systemSetting.getString("Tax", "0"))) / 100;
        totalPayable = totalPrice + tax;

        inputTotal.setText("Rp " + totalPrice + ",-");
        inputTax.setText("Rp " + tax + ",-");
        inputTotalItem.setText(String.valueOf(totalItem));
        inputTotalPayable.setText("Rp " + totalPayable + ",-");

        textTax.setText("Tax(" + Double.valueOf(systemSetting.getString("Tax", "0")) + "%)");

        recyclerView.setAdapter(adapter);

        btnList.setOnClickListener(goToAllProduct);
        btnCancel.setOnClickListener(cancelCashier);
        btnPayment.setOnClickListener(goToPayment);
        btnRefresh.setOnClickListener(refreshList);
        btnHold.setOnClickListener(goToHold);

        inputBarcode.setOnClickListener(camera);
    }

    private View.OnClickListener refreshList = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().getSupportFragmentManager().popBackStack();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new PosCashierFragment()).addToBackStack("Fragment").commit();
        }
    };

    private View.OnClickListener goToPayment = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AddPaymentFragment.paymentOutletID = ID;
            AddPaymentFragment.paymentTotal = totalPrice;
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new AddPaymentFragment()).addToBackStack("Fragment").commit();
        }
    };

    private View.OnClickListener goToHold = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new AddBillFragment()).addToBackStack("Fragment").commit();
        }
    };

    private View.OnClickListener cancelCashier = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            clear();
            getActivity().getSupportFragmentManager().popBackStack();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new PosCashierFragment()).addToBackStack("Fragment").commit();
        }
    };

    private View.OnClickListener goToAllProduct = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new PosListProductFragment()).addToBackStack("Fragment").commit();
        }
    };

    private View.OnClickListener camera = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getActivity(), PosCashierBarcodeActivity.class));
        }
    };

    public void clear() {
        listPosProducts.clear();
        PosLeftProductListAdapter.productName.clear();
        PosLeftProductListAdapter.productCode.clear();
        PosLeftProductListAdapter.productPrice.clear();
        PosLeftProductListAdapter.productCost.clear();
        PosCashierBarcodeActivity.productName.clear();
        PosCashierBarcodeActivity.productCode.clear();
        PosCashierBarcodeActivity.productPrice.clear();
        adapter.notifyItemRangeRemoved(0, listPosProducts.size());
    }
}