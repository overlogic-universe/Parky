package com.lucky7.parky.features.auth.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lucky7.parky.R;
import com.lucky7.parky.core.adapter.ActivityListAdapter;
import com.lucky7.parky.features.auth.data.model.UserModel;

import java.util.ArrayList;
import java.util.Objects;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivArrowBack;
    private RecyclerView rvActivityList;
    private final ArrayList<UserModel> userModelList = new ArrayList<>();
    private SearchView searchView;
    private ActivityListAdapter activityListAdapter;

    private void initView() {
        ivArrowBack = findViewById(R.id.iv_back_to_home_admin);
        rvActivityList = findViewById(R.id.rv_activity_list);
        searchView = findViewById(R.id.search_user_activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initView();

        rvActivityList.setHasFixedSize(true);

        getActivityList();

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

        ivArrowBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back_to_home_admin) {
            super.getOnBackPressedDispatcher().onBackPressed();
        }
    }

    private void getActivityList() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userModelList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserModel userModel = dataSnapshot.getValue(UserModel.class);
                    if (userModel != null && Objects.equals(userModel.getParkStatus().toLowerCase(), "parked")) {
                        userModelList.add(userModel);
                    }
                }
                showActivityList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showActivityList() {
        rvActivityList.setLayoutManager(new LinearLayoutManager(this));
        activityListAdapter = new ActivityListAdapter(userModelList);
        rvActivityList.setAdapter(activityListAdapter);
    }

    private void filterList(String text) {
        ArrayList<UserModel> filteredList = new ArrayList<>();
        for (UserModel userModel : userModelList) {
            if (userModel.getStudentId().toLowerCase().contains(text.toLowerCase()) || userModel.getPlate().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(userModel);
            }
        }

        if (!filteredList.isEmpty()) {
            activityListAdapter.setFilteredList(filteredList);
        }
    }
}