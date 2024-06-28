package com.lucky7.parky.features.park.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.lucky7.parky.MyApp;
import com.lucky7.parky.R;
import com.lucky7.parky.core.adapter.ActivityListAdapter;
import com.lucky7.parky.core.callback.RepositoryCallback;
import com.lucky7.parky.core.di.AppComponent;
import com.lucky7.parky.core.entity.ParkStatus;
import com.lucky7.parky.core.entity.SearchableFragment;
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
    private ParkedUserFragment parkedUserFragment;
    private ParkingHistoryFragment parkingHistoryFragment;
    private SearchView searchView;
    private Button btnParked;
    private Button btnParkingHistory;

    private void initView() {
        ivArrowBack = findViewById(R.id.iv_back_to_home_admin);
        searchView = findViewById(R.id.search_user_activity);
        btnParked = findViewById(R.id.btn_parked);
        btnParkingHistory = findViewById(R.id.btn_parking_history);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApp myApp = (MyApp) getApplicationContext();
        AppComponent appComponent = myApp.getAppComponent();
        appComponent.inject(this);

        setContentView(R.layout.activity_history);

        initView();

        parkedUserFragment = new ParkedUserFragment();
        parkingHistoryFragment = new ParkingHistoryFragment();

        setupTabButtons();


        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if (currentFragment instanceof SearchableFragment) {
                    ((SearchableFragment) currentFragment).filterList(newText);
                }
                return false;
            }
        });

        ivArrowBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back_to_home_admin) {
            super.getOnBackPressedDispatcher().onBackPressed();
        } else if (v.getId() == R.id.btn_parked) {
            setActiveFragment(parkedUserFragment);
            btnParked.setSelected(true);
            btnParkingHistory.setSelected(false);
            btnParked.invalidate();
            btnParkingHistory.invalidate();
        } else if (v.getId() == R.id.btn_parking_history) {
            setActiveFragment(parkingHistoryFragment);
            btnParked.setSelected(false);
            btnParkingHistory.setSelected(true);
            btnParked.invalidate();
            btnParkingHistory.invalidate();
        }
    }

    private void setupTabButtons() {
        btnParked.setOnClickListener(this);
        btnParkingHistory.setOnClickListener(this);

        setActiveFragment(parkedUserFragment);
        btnParked.setSelected(true);
        btnParkingHistory.setSelected(false);
    }


    private void setActiveFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }



    private void filterList(String text) {
//        ArrayList<ParkHistoryModel> filteredList = new ArrayList<>();
//        for (ParkHistoryModel parkHistoryModel : parkHistoryModelList) {
//            if (parkHistoryModel.getUserModel().getStudentId().toLowerCase().contains(text.toLowerCase()) || parkHistoryModel.getUserModel().getPlate().toLowerCase().contains(text.toLowerCase())) {
//                filteredList.add(parkHistoryModel);
//            }
//        }
//
//        if (!filteredList.isEmpty()) {
//            activityListAdapter.setFilteredList(filteredList);
//        }
    }
}