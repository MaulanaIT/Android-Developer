package com.example.project.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.Activity.MainActivity;
import com.example.project.Activity.PosCashierBarcodeActivity;
import com.example.project.Adapter.PosLeftProductListAdapter;
import com.example.project.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AddPaymentFragment extends Fragment {

    LinearLayout
            layoutPaid,
            layoutCard,
            layoutCheque,
            layoutGiftCard;

    EditText
            inputPaid,
            inputCardNumber,
            inputChequeNumber,
            inputGiftCard;

    Spinner
            spinnerCustomer,
            spinnerPaidBy;

    TextView
            textPayableAmount,
            textTotalItem,
            textReturn;

    Button
            btnSubmit;

    String
            paymentCustomer,
            paymentItem,
            paymentPaidBy,
            paymentPaidAmount,
            paymentCardNumber,
            paymentChequeNumber,
            paymentGiftCard,
            paymentReturn,
            time;

    public static double
            paymentPayable,
            paymentTotal,
            totalPayable,
            totalPaid,
            totalReturn,
            tax;

    public static int
            paymentOutletID,
            vtStatus,
            refundStatus;

    ProgressBar
            progressBar;

    private SimpleDateFormat
            dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa", Locale.getDefault());

    private Calendar
            calendar = Calendar.getInstance();

    private Date
            currentDate = calendar.getTime();

    private RequestQueue
            requestQueue;

    StringRequest
            stringRequest;

    public AddPaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getContext()).setMyTitle("Payment");

        totalPayable = 0;
        totalPaid = 0;
        totalReturn = 0;

        layoutPaid = getView().findViewById(R.id.layout_paid);
        layoutCard = getView().findViewById(R.id.layout_card);
        layoutCheque = getView().findViewById(R.id.layout_cheque);
        layoutGiftCard = getView().findViewById(R.id.layout_gift_card);

        inputPaid = getView().findViewById(R.id.input_paid_amount);
        inputCardNumber = getView().findViewById(R.id.input_card_number);
        inputChequeNumber = getView().findViewById(R.id.input_cheque_number);
        inputGiftCard = getView().findViewById(R.id.input_gift_card);

        spinnerCustomer = getView().findViewById(R.id.spinner_customer);
        spinnerPaidBy = getView().findViewById(R.id.spinner_paid);

        textPayableAmount = getView().findViewById(R.id.input_payable);
        textTotalItem = getView().findViewById(R.id.input_item);
        textReturn = getView().findViewById(R.id.input_return);

        progressBar = getView().findViewById(R.id.progressBar);

        btnSubmit = getView().findViewById(R.id.btn_submit);

        ((MainActivity) getContext()).setSpinner(((MainActivity) getContext()).setURLReadData("Customer"), "customer", spinnerCustomer);
        ((MainActivity) getContext()).setSpinner(((MainActivity) getContext()).setURLReadData("Payment Method"), "payment", spinnerPaidBy);

        spinnerPaidBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (spinnerPaidBy.getSelectedItem().toString()) {
                    case "Cash":
                        layoutCard.setVisibility(View.GONE);
                        layoutCheque.setVisibility(View.GONE);
                        layoutGiftCard.setVisibility(View.GONE);
                        break;
                    case "Nett":
                        layoutCard.setVisibility(View.GONE);
                        layoutCheque.setVisibility(View.GONE);
                        layoutGiftCard.setVisibility(View.GONE);
                        break;
                    case "Visa":
                        layoutCard.setVisibility(View.VISIBLE);
                        layoutCheque.setVisibility(View.GONE);
                        layoutGiftCard.setVisibility(View.GONE);
                        break;
                    case "Master Card":
                        layoutCard.setVisibility(View.VISIBLE);
                        layoutCheque.setVisibility(View.GONE);
                        layoutGiftCard.setVisibility(View.GONE);
                        break;
                    case "Cheque":
                        layoutCard.setVisibility(View.GONE);
                        layoutCheque.setVisibility(View.VISIBLE);
                        layoutGiftCard.setVisibility(View.GONE);
                        break;
                    case "Debit":
                        layoutCard.setVisibility(View.GONE);
                        layoutCheque.setVisibility(View.GONE);
                        layoutGiftCard.setVisibility(View.GONE);
                        break;
                    case "Gift Card":
                        layoutCard.setVisibility(View.GONE);
                        layoutCheque.setVisibility(View.GONE);
                        layoutGiftCard.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        totalPayable = PosCashierFragment.totalPayable;

        inputPaid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @SuppressLint("DefaultLocale")
            @Override
            public void afterTextChanged(Editable s) {
                if (inputPaid.getText().toString().equals("")) {
                    totalPaid = 0;
                } else {
                    totalPaid = Double.valueOf(inputPaid.getText().toString());
                }
                totalReturn = totalPaid - totalPayable;
                textReturn.setText(String.format("%.2f", (Double)totalReturn));
            }
        });

        textPayableAmount.setText(String.valueOf(PosCashierFragment.totalPayable));
        textTotalItem.setText(String.valueOf(PosCashierFragment.totalItem));

        btnSubmit.setOnClickListener(addPayment);
    }

    private View.OnClickListener addPayment = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                dataPayment();
                TimeUnit.SECONDS.sleep(1);
                dataItem();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void dataPayment() {
        paymentCustomer = spinnerCustomer.getSelectedItem().toString().trim();
        paymentPayable = Double.valueOf(textPayableAmount.getText().toString().trim());
        paymentItem = textTotalItem.getText().toString().trim();
        paymentPaidBy = spinnerPaidBy.getSelectedItem().toString().trim();
        paymentPaidAmount = inputPaid.getText().toString().trim();
        paymentCardNumber = inputCardNumber.getText().toString().trim();
        paymentChequeNumber = inputChequeNumber.getText().toString().trim();
        paymentGiftCard = inputGiftCard.getText().toString().trim();
        paymentReturn = textReturn.getText().toString().trim();
        tax = paymentPayable-paymentTotal;
        time = dateFormat.format(currentDate);

        if (Double.valueOf(textReturn.getText().toString()) >= 0) {
            refundStatus = 1;
        }

        if (paymentPaidBy.equals("Debit")) {
            vtStatus = 0;
        }

        switch (paymentPaidBy) {
            case "Visa":
            case "Master Card":
                layoutCard.setVisibility(View.VISIBLE);
                layoutCheque.setVisibility(View.GONE);
                layoutGiftCard.setVisibility(View.GONE);
                paymentChequeNumber = "";
                paymentGiftCard = "";
                break;
            case "Cheque":
                layoutCard.setVisibility(View.GONE);
                layoutCheque.setVisibility(View.VISIBLE);
                layoutGiftCard.setVisibility(View.GONE);
                paymentCardNumber = "";
                paymentGiftCard = "";
                break;
            case "Gift Card":
                layoutCard.setVisibility(View.GONE);
                layoutCheque.setVisibility(View.GONE);
                layoutGiftCard.setVisibility(View.VISIBLE);
                paymentChequeNumber = "";
                paymentCardNumber = "";
                break;
            default:
                layoutCard.setVisibility(View.GONE);
                layoutCheque.setVisibility(View.GONE);
                layoutGiftCard.setVisibility(View.GONE);
                paymentCardNumber = "";
                paymentChequeNumber = "";
                paymentGiftCard = "";
                break;
        }

        progressBar.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.GONE);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        stringRequest = new StringRequest(Request.Method.POST, ((MainActivity)getContext()).setURLAddData("Payment"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("Success")) {
                            progressBar.setVisibility(View.GONE);
                            btnSubmit.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Insert Succesful", Toast.LENGTH_SHORT).show();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            btnSubmit.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Insert Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        btnSubmit.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Customer", paymentCustomer);
                params.put("Payable", String.valueOf(paymentPayable));
                params.put("Item", paymentItem);
                params.put("OutletID", String.valueOf(paymentOutletID));
                params.put("PaidBy", paymentPaidBy);
                params.put("PaidAmount", paymentPaidAmount);
                params.put("CardNumber", paymentCardNumber);
                params.put("ChequeNumber", paymentChequeNumber);
                params.put("GiftCard", paymentGiftCard);
                params.put("Return", paymentReturn);
                params.put("Time", time);
                params.put("VtStatus", String.valueOf(vtStatus));
                params.put("RefundStatus", String.valueOf(refundStatus));
                params.put("TotalPrice", String.valueOf(paymentTotal));
                params.put("Tax", String.valueOf(tax));
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    public void clearArray() {
        if (PosLeftProductListAdapter.productName.size() > 0) {
            PosLeftProductListAdapter.productName.clear();
            PosLeftProductListAdapter.productCode.clear();
            PosLeftProductListAdapter.productPrice.clear();
            PosLeftProductListAdapter.productCost.clear();
            PosLeftProductListAdapter.productQuantity.clear();
            PosLeftProductListAdapter.productPayable.clear();
        }

        if (PosCashierBarcodeActivity.productName.size() > 0) {
            PosCashierBarcodeActivity.productName.clear();
            PosCashierBarcodeActivity.productCode.clear();
            PosCashierBarcodeActivity.productPrice.clear();
            PosCashierBarcodeActivity.productCost.clear();
            PosCashierBarcodeActivity.productQuantity.clear();
            PosCashierBarcodeActivity.productPayable.clear();
        }
    }

    private void dataItem() {
        final int size = PosCashierFragment.productName.size();

        progressBar.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.GONE);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        stringRequest = new StringRequest(Request.Method.POST, ((MainActivity) getContext()).setURLAddData("Payment Item"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("Success")) {
                            progressBar.setVisibility(View.GONE);
                            btnSubmit.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Insert Successful", Toast.LENGTH_SHORT).show();
                            clearArray();
                            getActivity().getSupportFragmentManager().popBackStack();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            btnSubmit.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "Insert Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        btnSubmit.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Time", time);
                params.put("Size", String.valueOf(size));
                for (int i = 0; i < size; i++) {
                    params.put("Code["+i+"]", PosCashierFragment.productCode.get(i));
                    params.put("Product["+i+"]", PosCashierFragment.productName.get(i));
                    params.put("Quantity["+i+"]", PosCashierFragment.productQuantity.get(i));
                    params.put("Cost["+i+"]", PosCashierFragment.productCost.get(i));
                    params.put("Price["+i+"]", PosCashierFragment.productPrice.get(i));
                }
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
