package com.lucky7.parky.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lucky7.parky.R;
import com.lucky7.parky.model.User;

import org.w3c.dom.Text;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ListViewHolder> {
    private ArrayList<User> userList;
    private OnItemClickListener itemClickListener;

    public UserListAdapter(ArrayList<User> list) {
        this.userList = list;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView studentId;
        TextView licensePlate;
        ImageButton deleteUser;

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
        User user = userList.get(position);
        holder.username.setText(user.getName());
        holder.studentId.setText(user.getStudentId());
        holder.licensePlate.setText(user.getPlate());
        holder.deleteUser.setOnClickListener(view -> {
            itemClickListener.onItemClick(userList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(User user);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setFilteredList(ArrayList<User> filteredList) {
        this.userList = filteredList;
        notifyDataSetChanged();
    }
}
