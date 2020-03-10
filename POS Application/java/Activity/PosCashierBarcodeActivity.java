package com.example.project.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.Adapter.PosLeftProductListAdapter;
import com.example.project.Fragment.PosCashierFragment;
import com.example.project.Fragment.PosListProductFragment;
import com.example.project.Fragment.SuppliersSettingFragment;
import com.example.project.Model.ListPosProduct;
import com.example.project.Model.ListProduct;
import com.example.project.R;
import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class PosCashierBarcodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;

    MainActivity mainActivity;

    List<ListProduct>
            listProducts;

    RequestQueue
            requestQueue;

    JsonArrayRequest
            jsonArrayRequest;

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

    ArrayList<String>
            string;

    String
            name = "",
            code = "",
            price = "",
            cost = "",
            image = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos_cashier_barcode);

        mainActivity = new MainActivity();
        listProducts = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);

        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
    }

    public void handleResult(final Result result) {
        jsonArrayRequest = new JsonArrayRequest(mainActivity.setURLReadData("Product"),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject jsonObject = response.getJSONObject(i);

                                name = jsonObject.getString("product");
                                code = jsonObject.getString("code");
                                price = jsonObject.getString("retail");
                                cost = jsonObject.getString("purchase");
                                image = jsonObject.getString("image");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            if (result.toString().equals(code)) {
                                if (productName.size() == 0) {
                                    productName.add(name);
                                    productCode.add(code);
                                    productPrice.add(price);
                                    productCost.add(cost);
                                    productQuantity.add("1");
                                    productPayable.add(price);
                                    productImage.add(image);
                                } else {
                                    int compareText = 0;
                                    for (int j = 0; j < productName.size(); j++) {
                                        if (productName.get(j).equals(name)) {
                                            quantityProduct = Integer.valueOf(productQuantity.get(j));

                                            quantityProduct = quantityProduct + 1;

                                            productQuantity.set(j, String.valueOf(quantityProduct));
                                            productPayable.set(j, String.valueOf(quantityProduct * Double.valueOf(price)));

                                            compareText++;
                                        }
                                    }
                                    if (compareText == 0) {
                                        productName.add(name);
                                        productCode.add(code);
                                        productPrice.add(price);
                                        productCost.add(cost);
                                        productQuantity.add("1");
                                        productPayable.add(price);
                                        productImage.add(image);
                                    }
                                }
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PosCashierBarcodeActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        requestQueue.add(jsonArrayRequest);
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();

        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    public ArrayList<String> setName() {
        string = productName;
        return string;
    }

    public ArrayList<String> setCode() {
        string = productCode;
        return string;
    }

    public ArrayList<String> setPrice() {
        string = productPrice;
        return string;
    }

    public ArrayList<String> setCost() {
        string = productCost;
        return string;
    }

    public ArrayList<String> setQuantity() {
        string = productQuantity;
        return string;
    }

    public ArrayList<String> setPayable() {
        string = productPayable;
        return string;
    }

    public ArrayList<String> setImage() {
        string = productImage;
        return string;
    }
}
