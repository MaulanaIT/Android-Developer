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
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.Activity.MainActivity;
import com.example.project.Fragment.DebitFragment;
import com.example.project.Fragment.EditPasswordFragment;
import com.example.project.Fragment.EditPaymentFragment;
import com.example.project.Fragment.SalesFragment;
import com.example.project.Model.ListDebit;
import com.example.project.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DebitListAdapter extends RecyclerView.Adapter<DebitListAdapter.ListViewHolder> {

    List<ListDebit>
            listDebits;

    Context
            context;

    private RequestQueue
            requestQueue;

    StringRequest
            stringRequest;

    public DebitListAdapter(List<ListDebit> listDebits, Context context) {
        super();

        this.listDebits = listDebits;
        this.context = context;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        Button
                btnMakePayment;

        TextView
                debitDate,
                debitID,
                debitCustomer,
                debitOutlet,
                debitPayable,
                debitUnpaidAmount;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            btnMakePayment = itemView.findViewById(R.id.btn_make_payment);

            debitDate = itemView.findViewById(R.id.list_debit_date);
            debitID = itemView.findViewById(R.id.list_debit_id);
            debitCustomer = itemView.findViewById(R.id.list_debit_customer);
            debitOutlet = itemView.findViewById(R.id.list_debit_outlet);
            debitPayable = itemView.findViewById(R.id.list_debit_payable);
            debitUnpaidAmount = itemView.findViewById(R.id.list_debit_unpaid);
        }
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_debit, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        final ListDebit currentItem = listDebits.get(position);

        holder.debitDate.setText(currentItem.getDebitDate());
        holder.debitID.setText(String.valueOf(currentItem.getDebitID()));
        holder.debitCustomer.setText(currentItem.getDebitCustomer());
        holder.debitOutlet.setText(currentItem.getDebitOutlet());
        holder.debitPayable.setText(currentItem.getDebitPayable());
        holder.debitUnpaidAmount.setText(currentItem.getDebitUnpaid());

        holder.btnMakePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditPaymentFragment.ID = currentItem.getDebitID();
                EditPaymentFragment.paidAmount = Double.valueOf(currentItem.getDebitPayable()) - Double.valueOf(currentItem.getDebitUnpaid());
                EditPaymentFragment.returnChange = Double.valueOf(EditPaymentFragment.paidAmount) - Double.valueOf(currentItem.getDebitPayable());
                EditPaymentFragment.payable = Double.valueOf(currentItem.getDebitPayable());
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new EditPaymentFragment()).addToBackStack("Fragment").commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDebits.size();
    }

    private void refreshFragment(View view) {
        AppCompatActivity activity = (AppCompatActivity)view.getContext();
        activity.getSupportFragmentManager().popBackStack();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new DebitFragment()).addToBackStack("Fragment").commit();
    }
}
