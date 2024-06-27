package com.lucky7.parky.features.park.presentation;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lucky7.parky.MyApp;
import com.lucky7.parky.R;
import com.lucky7.parky.core.adapter.ParkHistoryListAdapter;
import com.lucky7.parky.core.callback.RepositoryCallback;
import com.lucky7.parky.features.park.data.model.ParkHistoryModel;
import com.lucky7.parky.features.park.domain.repository.ParkHistoryRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ParkingHistoryFragment extends Fragment {
    @Inject
    ParkHistoryRepository parkHistoryRepository;
    private RecyclerView rvHistoryList;
    private TextView tvEmptyData;
    private ParkHistoryListAdapter parkHistoryListAdapter;
    private ArrayList<ParkHistoryModel> historyList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApp)  requireActivity().getApplication()).getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parking_history, container, false);
        rvHistoryList = view.findViewById(R.id.rv_activity_list);
        tvEmptyData = view.findViewById(R.id.tv_empty_data_history);
        setupRecyclerView();
        getParkHistoryList();
        return view;
    }

    private void setupRecyclerView() {
        rvHistoryList.setHasFixedSize(true);
        rvHistoryList.setLayoutManager(new LinearLayoutManager(getContext()));
        parkHistoryListAdapter = new ParkHistoryListAdapter(historyList);
        rvHistoryList.setAdapter(parkHistoryListAdapter);
    }

    private void getParkHistoryList() {
        parkHistoryRepository.getAllHistories(new RepositoryCallback<List<ParkHistoryModel>>() {
            @Override
            public void onSuccess(List<ParkHistoryModel> result) {
                historyList.clear();
                historyList.addAll(result);
                Log.d("WOWOWO", "onSuccess: " + result);
                showHistoryList();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getContext(), "Failed to get users", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showHistoryList() {
        rvHistoryList.setLayoutManager(new LinearLayoutManager(getContext()));
        parkHistoryListAdapter = new ParkHistoryListAdapter(historyList);
        rvHistoryList.setAdapter(parkHistoryListAdapter);
        if(historyList.isEmpty()){
            tvEmptyData.setText(R.string.empty_data);
        }
    }

    public void setHistoryList(ArrayList<ParkHistoryModel> historyList) {
        this.historyList = historyList;
        if (parkHistoryListAdapter != null) {
            parkHistoryListAdapter.notifyDataSetChanged();
        }
    }
}
