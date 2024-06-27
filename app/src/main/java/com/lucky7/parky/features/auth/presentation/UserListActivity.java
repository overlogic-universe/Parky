package com.lucky7.parky.features.auth.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lucky7.parky.MyApp;
import com.lucky7.parky.R;
import com.lucky7.parky.core.adapter.UserListAdapter;
import com.lucky7.parky.core.callback.RepositoryCallback;
import com.lucky7.parky.core.di.AppComponent;
import com.lucky7.parky.features.auth.data.model.UserModel;
import com.lucky7.parky.features.auth.domain.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class UserListActivity extends AppCompatActivity implements View.OnClickListener {
    @Inject
    UserRepository userRepository;
    private ImageView ivBackToHomeAdmin;
    private RecyclerView rvUserList;
    private final ArrayList<UserModel> userModelList = new ArrayList<>();
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
        MyApp myApp = (MyApp) getApplicationContext();
        AppComponent appComponent = myApp.getAppComponent();
        appComponent.inject(this);

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

        userRepository.getAllUsers(new RepositoryCallback<List<UserModel>>() {
            @Override
            public void onSuccess(List<UserModel> result) {
                userModelList.clear();
                userModelList.addAll(result);
                showUserList();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(UserListActivity.this, "Failed to get users", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showUserList() {
        rvUserList.setLayoutManager(new LinearLayoutManager(this));
        userListAdapter = new UserListAdapter(userModelList);
        rvUserList.setAdapter(userListAdapter);

        userListAdapter.setOnItemClickListener(new UserListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(UserModel userModel) {
                deleteUser(userModel);
            }
        });

    }

    private void deleteUser(UserModel userModel) {
        TextView tvCancelDeleteUser = dialog.findViewById(R.id.tv_cancel_delete_user);
        TextView tvConfirmDeleteUser = dialog.findViewById(R.id.tv_confirm_delete_user);
        dialog.show();

        tvCancelDeleteUser.setOnClickListener(view -> {
            dialog.dismiss();
        });

        tvConfirmDeleteUser.setOnClickListener(v -> {
            userRepository.deleteUser(userModel, new RepositoryCallback<Void>() {
                @Override
                public void onSuccess(Void result) {
                    userModelList.remove(userModel);
                    showUserList();
                    Toast.makeText(UserListActivity.this, "Successfully deleted user", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(UserListActivity.this, "Failed to delete user", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

        });
    }

    private void filterList(String text) {
        ArrayList<UserModel> filteredList = new ArrayList<>();
        for (UserModel userModel : userModelList) {
            if (userModel.getStudentId().toLowerCase().contains(text.toLowerCase()) || userModel.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(userModel);
            }
        }
        if (!filteredList.isEmpty()) {
            userListAdapter.setFilteredList(filteredList);
        }
    }
}