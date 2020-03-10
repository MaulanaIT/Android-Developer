package com.example.project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Fragment.EditPasswordFragment;
import com.example.project.Fragment.EditUserFragment;
import com.example.project.Model.ListUser;
import com.example.project.R;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ListViewHolder> {

    List<ListUser> listUsers;
    Context context;

    public UserListAdapter(List<ListUser> listUsers, Context context) {
        super();

        this.listUsers = listUsers;
        this.context = context;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView
                userName,
                userEmail,
                userRole,
                userOutlet,
                userStatus;

        Button
                btnEdit,
                btnChangePassword;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.list_user_name);
            userEmail = itemView.findViewById(R.id.list_user_email);
            userRole = itemView.findViewById(R.id.list_user_role);
            userOutlet = itemView.findViewById(R.id.list_user_outlet);
            userStatus = itemView.findViewById(R.id.list_user_status);

            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnChangePassword = itemView.findViewById(R.id.btn_change_password);
        }
    }

    @NonNull
    @Override
    public UserListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user, parent, false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.ListViewHolder holder, int position) {
        final ListUser currentItem = listUsers.get(position);

        holder.userName.setText(currentItem.getUserName());
        holder.userEmail.setText(currentItem.getUserEmail());
        holder.userRole.setText(String.valueOf(currentItem.getUserRole()));
        holder.userOutlet.setText(currentItem.getUserOutlet());
        holder.userStatus.setText(currentItem.getUserStatus());
        if (holder.userStatus.getText().toString().equals("Active")) {
            holder.userStatus.setTextColor(ContextCompat.getColor(context, R.color.colorLime));
        } else {
            holder.userStatus.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        }

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditUserFragment.ID = currentItem.getUserID();
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new EditUserFragment()).addToBackStack("Fragment").commit();
            }
        });

        holder.btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditUserFragment.ID = currentItem.getUserID();
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, new EditPasswordFragment()).addToBackStack("Fragment").commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }
}
