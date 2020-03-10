package com.example.project.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Model.ListMonthlyProfitAndLoss;

import java.util.ArrayList;
import java.util.List;

public class MonthlyProfitAndLossStatisticAdapter extends RecyclerView.Adapter<MonthlyProfitAndLossStatisticAdapter.ListViewHolder> {

    public static ArrayList<String>
            statisticID = new ArrayList<>(),
            statisticOutlet = new ArrayList<>(),
            statisticDay = new ArrayList<>(),
            statisticProfit = new ArrayList<>();

    List<ListMonthlyProfitAndLoss>
            listMonthlyProfitAndLosses;

    Context
            context;

    public MonthlyProfitAndLossStatisticAdapter(List<ListMonthlyProfitAndLoss> listMonthlyProfitAndLosses, Context context) {
        super();

        this.listMonthlyProfitAndLosses = listMonthlyProfitAndLosses;
        this.context = context;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        final ListMonthlyProfitAndLoss currentItem = listMonthlyProfitAndLosses.get(position);

        statisticID.add(String.valueOf(currentItem.getStatisticID()));
        statisticOutlet.add(currentItem.getStatisticOutlet());
        statisticDay.add(currentItem.getStatisticDay());
        statisticProfit.add(currentItem.getStatisticProfit());
    }

    @Override
    public int getItemCount() {
        return listMonthlyProfitAndLosses.size();
    }
}
