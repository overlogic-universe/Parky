package com.lucky7.parky.features.auth.domain.repository;

import com.lucky7.parky.features.auth.data.model.UserModel;

public interface UserRepository {
    void addUser(UserModel userModel);
    void deleteUser(UserModel userModel);
    void updatePassword(UserModel userModel);
    void updateParkStatus(UserModel userModel);
}
