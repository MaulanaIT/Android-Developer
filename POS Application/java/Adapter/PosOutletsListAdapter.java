package com.example.project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Fragment.PosCashierFragment;
import com.example.project.Model.ListPosOutlets;
import com.example.project.R;

import java.util.List;

public class PosOutletsListAdapter extends RecyclerView.Adapter<PosOutletsListAdapter.ListViewHolder> {

    List<ListPosOutlets>
            listPosOutlets;

    Context
            context;

    public PosOutletsListAdapter(List<ListPosOutlets> listPosOutlets, Context context) {
        super();

        this.listPosOutlets = listPosOutlets;
        this.context = context;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView
                outletName,
                outletAddress,
                outletTelephone;

        LinearLayout
                btnOutlet;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            outletName = itemView.findViewById(R.id.outlet_name);
            outletAddress = itemView.findViewById(R.id.outlet_address);
            outletTelephone = itemView.findViewById(R.id.outlet_telephone);

            btnOutlet = itemView.findViewById(R.id.btn_outlet);
        }
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pos_outlets, parent, false);
        PosOutletsListAdapter.ListViewHolder listViewHolder = new PosOutletsListAdapter.ListViewHolder(view);

        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        final ListPosOutlets currentItem = listPosOutlets.get(position);

        holder.outletName.setText(currentItem.getOutletsName());
        holder.outletAddress.setText(currentItem.getOutletsAddress());
        holder.outletTelephone.setText(currentItem.getOutletsTelephone());

        holder.btnOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PosCashierFragment.ID = currentItem.getOutletID();
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new PosCashierFragment()).addToBackStack("Fragment").commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPosOutlets.size();
    }
}
