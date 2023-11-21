package com.lucky7.parky.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lucky7.parky.R;
import com.lucky7.parky.model.User;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ListViewHolder> {
    private ArrayList<User> userList;

    public ActivityListAdapter(ArrayList<User> userList) {
        this.userList = userList;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        TextView licensePlate;
        TextView studentId;
        TextView status;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            licensePlate = itemView.findViewById(R.id.tv_license_plate_activity);
            studentId = itemView.findViewById(R.id.tv_student_id_activity);
            status = itemView.findViewById(R.id.tv_status_activity);
        }
    }

    @NonNull
    @Override
    public ActivityListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_activity, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityListAdapter.ListViewHolder holder, int position) {
        User user = userList.get(position);
        holder.licensePlate.setText(user.getPlate());
        holder.studentId.setText(user.getStudentId());
        holder.status.setText(user.getParkStatus());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setFilteredList(ArrayList<User> filteredList) {
        this.userList = filteredList;
        notifyDataSetChanged();
    }
}
