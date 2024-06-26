package com.lucky7.parky.features.park.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lucky7.parky.R;
import com.lucky7.parky.core.adapter.ActivityListAdapter;
import com.lucky7.parky.core.callback.RepositoryCallback;
import com.lucky7.parky.core.entity.ParkStatus;
import com.lucky7.parky.core.util.firestore.CollectionReferenceUtil;
import com.lucky7.parky.features.auth.data.model.UserModel;
import com.lucky7.parky.features.park.data.model.ParkHistoryModel;
import com.lucky7.parky.features.park.domain.repository.ParkHistoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {
    @Inject
    ParkHistoryRepository parkHistoryRepository;
    private ImageView ivArrowBack;
    private RecyclerView rvActivityList;
    private final ArrayList<ParkHistoryModel> parkHistoryModelList = new ArrayList<>();
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
        parkHistoryRepository.getAllHistories(new RepositoryCallback<List<ParkHistoryModel>>() {
            @Override
            public void onSuccess(List<ParkHistoryModel> parkHistories) {
                parkHistoryModelList.clear();
                parkHistoryModelList.addAll(parkHistories);
                showActivityList();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(HistoryActivity.this, "Failed to fetch park histories", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void showActivityList() {
        rvActivityList.setLayoutManager(new LinearLayoutManager(this));
        activityListAdapter = new ActivityListAdapter(parkHistoryModelList);
        rvActivityList.setAdapter(activityListAdapter);
    }

    private void filterList(String text) {
        ArrayList<ParkHistoryModel> filteredList = new ArrayList<>();
        for (ParkHistoryModel parkHistoryModel : parkHistoryModelList) {
            if (parkHistoryModel.getUserModel().getStudentId().toLowerCase().contains(text.toLowerCase()) || parkHistoryModel.getUserModel().getPlate().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(parkHistoryModel);
            }
        }

        if (!filteredList.isEmpty()) {
            activityListAdapter.setFilteredList(filteredList);
        }
    }
}