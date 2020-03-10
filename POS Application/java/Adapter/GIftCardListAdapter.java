package com.example.project.Adapter;

import android.app.ProgressDialog;
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
import com.example.project.Fragment.EditExpensesCategoryFragment;
import com.example.project.Fragment.EditGiftCardFragment;
import com.example.project.Fragment.ListGiftCardFragment;
import com.example.project.Model.ListGiftCard;
import com.example.project.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GIftCardListAdapter extends RecyclerView.Adapter<GIftCardListAdapter.ListViewHolder> {

    List<ListGiftCard>
            listGiftCards;

    Context
            context;

    private RequestQueue
            requestQueue;

    StringRequest
            stringRequest;

    public GIftCardListAdapter(List<ListGiftCard> listGiftCards, Context context) {
        super();

        this.listGiftCards = listGiftCards;
        this.context = context;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
            TextView
                    giftCardNumber,
                    giftCardValue,
                    giftCardExpiryDate,
                    giftCardStatus;

            Button
                    btnEdit,
                    btnDelete;

        public ListViewHolder(@NonNull View itemView) {
                super(itemView);

                giftCardNumber = itemView.findViewById(R.id.list_gift_card_number);
                giftCardValue = itemView.findViewById(R.id.list_gift_card_value);
                giftCardExpiryDate = itemView.findViewById(R.id.list_gift_card_expiry_date);
                giftCardStatus = itemView.findViewById(R.id.list_gift_card_status);

                btnEdit = itemView.findViewById(R.id.btn_edit);
                btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_gift_card, parent, false);
        GIftCardListAdapter.ListViewHolder listViewHolder = new GIftCardListAdapter.ListViewHolder(view);

        return listViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        final ListGiftCard currentItem = listGiftCards.get(position);

        holder.giftCardNumber.setText(currentItem.getGiftCardNumber());
        holder.giftCardValue.setText(currentItem.getGiftCardValue());
        holder.giftCardExpiryDate.setText(String.valueOf(currentItem.getGiftCardExpiryDate()));
        holder.giftCardStatus.setText(currentItem.getGiftCardStatus());
        if (holder.giftCardStatus.getText().toString().equals("Active")) {
            holder.giftCardStatus.setTextColor(ContextCompat.getColor(context, R.color.colorLime));
        } else {
            holder.giftCardStatus.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        }
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditGiftCardFragment.ID = currentItem.getGiftCardID();
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new EditGiftCardFragment()).addToBackStack("Fragment").commit();
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                requestQueue = Volley.newRequestQueue(context.getApplicationContext());

                stringRequest = new StringRequest(Request.Method.POST, ((MainActivity)context).setURLDeleteData("Gift Card"),
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
                        params.put("ID", String.valueOf(currentItem.getGiftCardID()));
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listGiftCards.size();
    }

    private void refreshFragment(View view) {
        AppCompatActivity activity = (AppCompatActivity)view.getContext();
        activity.getSupportFragmentManager().popBackStack();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new ListGiftCardFragment()).addToBackStack("Fragment").commit();
    }
}
