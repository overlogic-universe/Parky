package com.lucky7.parky.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.lucky7.parky.R;
import com.lucky7.parky.adapter.UserListAdapter;
import com.lucky7.parky.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserListActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBackToHomeAdmin;
    private RecyclerView rvUserList;
    private final ArrayList<User> userList = new ArrayList<>();
    private SearchView searchView;
    private UserListAdapter userListAdapter;
    Dialog dialog;

    private void initView() {
        ivBackToHomeAdmin = findViewById(R.id.iv_back_to_home_admin);
        rvUserList = findViewById(R.id.rv_user_list);
        searchView = findViewById(R.id.search_user_list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        initView();

        rvUserList.setHasFixedSize(true);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_confirm_delete_user);
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.border_radius_15));
        dialog.setCancelable(false);

        getUserList();

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });

        ivBackToHomeAdmin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back_to_home_admin) {
            super.getOnBackPressedDispatcher().onBackPressed();
        }
    }

    private void getUserList() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);

                    userList.add(user);
                }
                showUserList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void showUserList() {
        rvUserList.setLayoutManager(new LinearLayoutManager(this));
        userListAdapter = new UserListAdapter(userList);
        rvUserList.setAdapter(userListAdapter);

        userListAdapter.setOnItemClickListener(new UserListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User user) {
                deleteUser(user);
            }
        });

    }

    private void deleteUser(User user) {
        TextView tvCancelDeleteUser = dialog.findViewById(R.id.tv_cancel_delete_user); // Inisialisasi ulang TextView dari dialog
        TextView tvConfirmDeleteUser = dialog.findViewById(R.id.tv_confirm_delete_user);
        dialog.show();

        tvCancelDeleteUser.setOnClickListener(view -> {
            dialog.dismiss();
        });

        tvConfirmDeleteUser.setOnClickListener(v -> {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
            DatabaseReference dataRef = reference.child(user.getStudentId());

            dataRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(UserListActivity.this, "Successfully deleted user", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        });
    }

    private void filterList(String text) {
        ArrayList<User> filteredList = new ArrayList<>();
        for (User user : userList) {
            if (user.getStudentId().toLowerCase().contains(text.toLowerCase()) || user.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(user);
            }
        }
        if (!filteredList.isEmpty()) {
            userListAdapter.setFilteredList(filteredList);
        }
    }
}