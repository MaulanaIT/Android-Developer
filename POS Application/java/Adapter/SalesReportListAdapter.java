package com.example.project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Model.ListReport;
import com.example.project.R;

import java.util.List;

public class SalesReportListAdapter extends RecyclerView.Adapter<SalesReportListAdapter.ListViewHolder> {

    List<ListReport>
            listReports;

    Context
            context;

    public SalesReportListAdapter(List<ListReport> listReports, Context context) {
        super();

        this.listReports = listReports;
        this.context = context;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{

        TextView
                salesDate,
                salesID,
                salesOutlet,
                salesPaymentMethod,
                salesTotal,
                salesTax,
                salesPayable;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            salesDate = itemView.findViewById(R.id.list_report_date);
            salesID = itemView.findViewById(R.id.list_report_id);
            salesOutlet = itemView.findViewById(R.id.list_report_outlet);
            salesPaymentMethod = itemView.findViewById(R.id.list_report_method);
            salesTotal = itemView.findViewById(R.id.list_report_total);
            salesTax = itemView.findViewById(R.id.list_report_tax);
            salesPayable = itemView.findViewById(R.id.list_report_payable);
        }
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_sales_report, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        final ListReport currentItem = listReports.get(position);

        holder.salesDate.setText(currentItem.getReportDate());
        holder.salesID.setText(String.valueOf(currentItem.getReportID()));
        holder.salesOutlet.setText(currentItem.getReportOutlet());
        holder.salesPaymentMethod.setText(currentItem.getReportPaymentMethod());
        holder.salesTotal.setText(currentItem.getReportTotal());
        holder.salesTax.setText(currentItem.getReportTax());
        holder.salesPayable.setText(currentItem.getReportPayable());
    }

    @Override
    public int getItemCount() {
        return listReports.size();
    }
}