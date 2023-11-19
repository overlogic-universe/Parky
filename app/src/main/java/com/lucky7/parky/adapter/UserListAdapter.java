package com.lucky7.parky.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lucky7.parky.R;
import com.lucky7.parky.model.User;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ListViewHolder> {
    private final ArrayList<User> userList;

    public UserListAdapter(ArrayList<User> list) {
        this.userList = list;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView licensePlate;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.tv_username_list);
            licensePlate = itemView.findViewById(R.id.tv_license_plate_list);
        }
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_list, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        User user = userList.get(position);
        holder.username.setText(user.getName());
        holder.licensePlate.setText(user.getPlate());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
