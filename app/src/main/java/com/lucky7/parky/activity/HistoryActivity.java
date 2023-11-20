package com.lucky7.parky.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.lucky7.parky.adapter.ActivityListAdapter;
import com.lucky7.parky.model.User;

import java.util.ArrayList;
import java.util.Objects;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivArrowBack;
    private RecyclerView rvActivityList;
    private final ArrayList<User> userList = new ArrayList<>();

    private void initView() {
        ivArrowBack = findViewById(R.id.iv_back_to_home_admin);
        rvActivityList = findViewById(R.id.rv_activity_list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initView();

        rvActivityList.setHasFixedSize(true);

        getActivityList();

        ivArrowBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back_to_home_admin) {
            super.getOnBackPressedDispatcher().onBackPressed();
        }
    }

    private void getActivityList(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    if(user!= null && Objects.equals(user.getParkStatus().toLowerCase(), "parked")){
                        userList.add(user);
                    }
                }
                showActivityList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showActivityList(){
        rvActivityList.setLayoutManager(new LinearLayoutManager(this));
        ActivityListAdapter activityListAdapter = new ActivityListAdapter(userList);
        rvActivityList.setAdapter(activityListAdapter);
    }
}