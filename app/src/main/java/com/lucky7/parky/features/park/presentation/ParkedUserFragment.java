package com.lucky7.parky.features.park.presentation;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lucky7.parky.MyApp;
import com.lucky7.parky.R;
import com.lucky7.parky.core.adapter.ActivityListAdapter;
import com.lucky7.parky.core.callback.RepositoryCallback;
import com.lucky7.parky.core.entity.ParkStatus;
import com.lucky7.parky.features.auth.data.model.UserModel;
import com.lucky7.parky.features.auth.domain.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class ParkedUserFragment extends Fragment {
    @Inject
    UserRepository userRepository;
    private RecyclerView rvUserList;
    private TextView tvEmptyData;
    private ActivityListAdapter activityListAdapter;
    private ArrayList<UserModel> userModelList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApp) requireActivity().getApplication()).getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parked_user, container, false);
        rvUserList = view.findViewById(R.id.rv_activity_list);
        tvEmptyData = view.findViewById(R.id.tv_empty_data_parked);
        setupRecyclerView();
        getUserList();
        return view;
    }

    private void setupRecyclerView() {
        rvUserList.setHasFixedSize(true);
        rvUserList.setLayoutManager(new LinearLayoutManager(getContext()));
        activityListAdapter = new ActivityListAdapter(userModelList);
        rvUserList.setAdapter(activityListAdapter);
    }

    public void setUserList(ArrayList<UserModel> userList) {
        this.userModelList = userList;
        if (activityListAdapter != null) {
            activityListAdapter.notifyDataSetChanged();
        }
    }

    private void getUserList() {
        userRepository.getAllUsers(new RepositoryCallback<List<UserModel>>() {
            @Override
            public void onSuccess(List<UserModel> result) {
                userModelList.clear();
                for (UserModel userModel : result) {
                    if (Objects.equals(userModel.getParkStatus(), ParkStatus.PARKED.toString())) {
                        userModelList.add(userModel);
                    }
                }
                showUserList();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getContext(), "Failed to get users", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showUserList() {
        rvUserList.setLayoutManager(new LinearLayoutManager(getContext()));
        activityListAdapter = new ActivityListAdapter(userModelList);
        rvUserList.setAdapter(activityListAdapter);
        if(userModelList.isEmpty()){
            tvEmptyData.setText(R.string.empty_data);
        }
    }
}

