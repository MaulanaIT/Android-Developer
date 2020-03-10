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
import com.example.project.Fragment.EditExpensesFragment;
import com.example.project.Fragment.ExpensesFragment;
import com.example.project.Model.ListExpenses;
import com.example.project.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpensesListAdapter extends RecyclerView.Adapter<ExpensesListAdapter.ListViewHolder> {

    List<ListExpenses> listExpenses;
    Context context;

    private RequestQueue
            requestQueue;

    StringRequest
            stringRequest;

    public ExpensesListAdapter(List<ListExpenses> listExpenses, Context context) {
        super();

        this.listExpenses = listExpenses;
        this.context = context;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView
                expensesNumber,
                expensesCategory,
                expensesOutlet,
                expensesDate,
                expensesAmount;

        Button
                btnEdit,
                btnDelete;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            expensesNumber = itemView.findViewById(R.id.list_expenses_number);
            expensesCategory = itemView.findViewById(R.id.list_expenses_category);
            expensesOutlet = itemView.findViewById(R.id.list_expenses_outlet);
            expensesDate = itemView.findViewById(R.id.list_expenses_date);
            expensesAmount = itemView.findViewById(R.id.list_expenses_amount);

            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }

    @NonNull
    @Override
    public ExpensesListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_expenses, parent, false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpensesListAdapter.ListViewHolder holder, int position) {
        final ListExpenses currentItem = listExpenses.get(position);

        holder.expensesNumber.setText(currentItem.getExpensesNumber());
        holder.expensesCategory.setText(currentItem.getExpensesCategory());
        holder.expensesOutlet.setText(String.valueOf(currentItem.getExpensesOutlet()));
        holder.expensesDate.setText(currentItem.getExpensesDate());
        holder.expensesAmount.setText(String.valueOf(currentItem.getExpensesAmount()));

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditExpensesFragment.id = currentItem.getExpensesID();
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new EditExpensesFragment()).addToBackStack("Fragment").commit();
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                requestQueue = Volley.newRequestQueue(context.getApplicationContext());

                stringRequest = new StringRequest(Request.Method.POST, ((MainActivity)context).setURLDeleteData("Expenses"),
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
                        params.put("ID", String.valueOf(currentItem.getExpensesID()));
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listExpenses.size();
    }

    private void refreshFragment(View view) {
        AppCompatActivity activity = (AppCompatActivity)view.getContext();
        activity.getSupportFragmentManager().popBackStack();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new ExpensesFragment()).addToBackStack("Fragment").commit();
    }
}