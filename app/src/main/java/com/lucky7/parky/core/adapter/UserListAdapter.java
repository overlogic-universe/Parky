package com.lucky7.parky.core.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lucky7.parky.R;
import com.lucky7.parky.features.auth.data.model.UserModel;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ListViewHolder> {
    private ArrayList<UserModel> userModelList;
    private OnItemClickListener itemClickListener;

    public UserListAdapter(ArrayList<UserModel> list) {
        this.userModelList = list;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView username;
        private TextView studentId;
        private TextView licensePlate;
        private ImageButton deleteUser;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.tv_username_list);
            studentId = itemView.findViewById(R.id.tv_student_id_list);
            licensePlate = itemView.findViewById(R.id.tv_license_plate_list);
            deleteUser = itemView.findViewById(R.id.ib_delete_user);
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
        UserModel userModel = userModelList.get(position);
        holder.username.setText(userModel.getName());
        holder.studentId.setText(userModel.getId());
        holder.licensePlate.setText(userModel.getPlate());
        holder.deleteUser.setOnClickListener(view -> {
            itemClickListener.onItemClick(userModelList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(UserModel userModel);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setFilteredList(ArrayList<UserModel> filteredList) {
        this.userModelList = filteredList;
        notifyDataSetChanged();
    }
}
