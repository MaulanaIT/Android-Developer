package com.example.project.Adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.project.Fragment.ListGiftCardFragment;
import com.example.project.Fragment.OpenedBillFragment;
import com.example.project.Model.ListOpenedBill;
import com.example.project.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenedBillListAdapter extends RecyclerView.Adapter<OpenedBillListAdapter.ListViewHolder> {

    List<ListOpenedBill>
            listOpenedBills;

    Context
            context;

    RequestQueue
            requestQueue;

    StringRequest
            stringRequest;

    public OpenedBillListAdapter(List<ListOpenedBill> listOpenedBills, Context context) {
        super();

        this.listOpenedBills = listOpenedBills;
        this.context = context;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        Button
                btnDelete;

        TextView
                billDate,
                billCustomer,
                billOutlet,
                billRefNumber,
                billItem,
                billTotal,
                billTax,
                billPayable;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            btnDelete = itemView.findViewById(R.id.btn_delete);

            billDate = itemView.findViewById(R.id.list_bill_date);
            billCustomer = itemView.findViewById(R.id.list_bill_customer);
            billOutlet = itemView.findViewById(R.id.list_bill_outlet);
            billRefNumber = itemView.findViewById(R.id.list_bill_refnumber);
            billItem = itemView.findViewById(R.id.list_bill_item);
            billTotal = itemView.findViewById(R.id.list_bill_total);
            billTax = itemView.findViewById(R.id.list_bill_tax);
            billPayable = itemView.findViewById(R.id.list_bill_payable);
        }
    }

    @NonNull
    @Override
    public OpenedBillListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_opened_bill, parent, false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OpenedBillListAdapter.ListViewHolder holder, int position) {
        final ListOpenedBill currentItem = listOpenedBills.get(position);

        holder.billDate.setText(currentItem.getBillDate());
        holder.billCustomer.setText(currentItem.getBillCustomer());
        holder.billOutlet.setText(currentItem.getBillOutlet());
        holder.billRefNumber.setText(currentItem.getBillRefNumber());
        holder.billItem.setText(currentItem.getBillItem());
        holder.billTotal.setText(currentItem.getBillTotal());
        holder.billTax.setText(currentItem.getBillTax());
        holder.billPayable.setText(currentItem.getBillPayable());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                requestQueue = Volley.newRequestQueue(context.getApplicationContext());

                stringRequest = new StringRequest(Request.Method.POST, ((MainActivity)context).setURLDeleteData("Bill"),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.contains("Success")) {
                                    Toast.makeText(context, "Delete Succesful", Toast.LENGTH_SHORT).show();
                                    refreshFragment(v);
                                } else {
                                    Toast.makeText(context, "Delete Failed", Toast.LENGTH_SHORT).show();
                                    refreshFragment(v);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                                refreshFragment(v);
                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("ID", String.valueOf(currentItem.getBillID()));
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOpenedBills.size();
    }

    private void refreshFragment(View view) {
        AppCompatActivity activity = (AppCompatActivity)view.getContext();
        activity.getSupportFragmentManager().popBackStack();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new OpenedBillFragment()).addToBackStack("Fragment").commit();
    }
}
