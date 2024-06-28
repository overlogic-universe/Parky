package com.lucky7.parky.core.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lucky7.parky.R;
import com.lucky7.parky.core.entity.HistoryStatus;
import com.lucky7.parky.features.park.data.model.ParkHistoryModel;

import java.util.ArrayList;

public class ParkHistoryListAdapter extends RecyclerView.Adapter<ParkHistoryListAdapter.ListViewHolder> {
    private ArrayList<ParkHistoryModel> parkHistoryList;

    public ParkHistoryListAdapter(ArrayList<ParkHistoryModel> parkHistoryList) {
        this.parkHistoryList = parkHistoryList;
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
            parkingDateTime = itemView.findViewById(R.id.tv_date_activity);
            status = itemView.findViewById(R.id.tv_status_activity);
        }
    }

    @NonNull
    @Override
    public ParkHistoryListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_park_history, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParkHistoryListAdapter.ListViewHolder holder, int position) {
        ParkHistoryModel parkHistory = parkHistoryList.get(position);
        holder.licensePlate.setText(parkHistory.getUserModel().getPlate());
        holder.studentId.setText(parkHistory.getUserModel().getStudentId());
        holder.parkingDateTime.setText(parkHistory.getParkDateTime());
        holder.status.setText(parkHistory.getHistoryStatus());

        if (HistoryStatus.IN.toString().equals(parkHistory.getHistoryStatus())) {
            holder.status.setBackgroundResource(R.drawable.border_in);
        } else if (HistoryStatus.OUT.toString().equals(parkHistory.getHistoryStatus())) {
            holder.status.setBackgroundResource(R.drawable.border_out);
        }
    }

    @Override
    public int getItemCount() {
        return parkHistoryList.size();
    }

    public void setFilteredList(ArrayList<ParkHistoryModel> filteredList) {
        this.parkHistoryList = filteredList;
        notifyDataSetChanged();
    }
}
