package com.lucky7.parky.core.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lucky7.parky.R;
import com.lucky7.parky.features.auth.data.model.UserModel;

import java.util.ArrayList;

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ListViewHolder> {
    private ArrayList<UserModel> userModelList;

    public ActivityListAdapter(ArrayList<UserModel> userModelList) {
        this.userModelList = userModelList;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        TextView licensePlate;
        TextView studentId;
        TextView status;
        TextView parkingDateTime;


        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            licensePlate = itemView.findViewById(R.id.tv_license_plate_activity);
            studentId = itemView.findViewById(R.id.tv_student_id_activity);
            status = itemView.findViewById(R.id.tv_status_activity);
            parkingDateTime = itemView.findViewById(R.id.tv_date_activity);
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
        UserModel userModel = userModelList.get(position);
        holder.licensePlate.setText(userModel.getPlate());
        holder.studentId.setText(userModel.getStudentId());
        holder.status.setText(userModel.getParkStatus());
        holder.parkingDateTime.setText(userModel.getLastActivity());
    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public void setFilteredList(ArrayList<UserModel> filteredList) {
        this.userModelList = filteredList;
        notifyDataSetChanged();
    }
}
