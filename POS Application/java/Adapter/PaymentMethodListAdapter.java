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
import com.example.project.Fragment.EditInventoryFragment;
import com.example.project.Fragment.EditPaymentMethodFragment;
import com.example.project.Fragment.PaymentMethodFragment;
import com.example.project.Fragment.SuppliersSettingFragment;
import com.example.project.Model.ListExpenses;
import com.example.project.Model.ListPaymentMethod;
import com.example.project.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentMethodListAdapter extends RecyclerView.Adapter<PaymentMethodListAdapter.ListViewHolder> {

    List<ListPaymentMethod> listPaymentMethods;
    Context context;

    private RequestQueue
            requestQueue;

    StringRequest
            stringRequest;

    public PaymentMethodListAdapter(List<ListPaymentMethod> listPaymentMethods, Context context) {
        super();

        this.listPaymentMethods = listPaymentMethods;
        this.context = context;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView
                paymentMethodName,
                paymentMethodStatus;

        Button
                btnEdit,
                btnDelete;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            paymentMethodName = itemView.findViewById(R.id.list_payment_method_name);
            paymentMethodStatus = itemView.findViewById(R.id.list_payment_method_status);

            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }

    @NonNull
    @Override
    public PaymentMethodListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_payment_method, parent, false);
        PaymentMethodListAdapter.ListViewHolder viewHolder = new PaymentMethodListAdapter.ListViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentMethodListAdapter.ListViewHolder holder, int position) {
        final ListPaymentMethod currentItem = listPaymentMethods.get(position);

        holder.paymentMethodName.setText(currentItem.getPaymentMethodName());
        holder.paymentMethodStatus.setText(currentItem.getPaymentMethodStatus());
        if (holder.paymentMethodStatus.getText().toString().equals("Active")) {
            holder.paymentMethodStatus.setTextColor(ContextCompat.getColor(context, R.color.colorLime));
        } else {
            holder.paymentMethodStatus.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        }

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditPaymentMethodFragment.ID = currentItem.getPaymentMethodID();
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new EditPaymentMethodFragment()).addToBackStack("Fragment").commit();
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                requestQueue = Volley.newRequestQueue(context.getApplicationContext());

                stringRequest = new StringRequest(Request.Method.POST, ((MainActivity)context).setURLDeleteData("Payment Method"),
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
                        params.put("ID", String.valueOf(currentItem.getPaymentMethodID()));
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPaymentMethods.size();
    }

    private void refreshFragment(View view) {
        AppCompatActivity activity = (AppCompatActivity)view.getContext();
        activity.getSupportFragmentManager().popBackStack();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new PaymentMethodFragment()).addToBackStack("Fragment").commit();
    }
}