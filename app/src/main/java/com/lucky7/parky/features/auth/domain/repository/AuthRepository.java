package com.lucky7.parky.features.auth.domain.repository;

import com.lucky7.parky.features.auth.data.model.AdminModel;
import com.lucky7.parky.features.auth.data.model.UserModel;

public interface AuthRepository {
     void loginWithEmailAndPasswordAdmin(AdminModel adminModel);
     void loginWithEmailAndPasswordUser(UserModel userModel);
     void logout();
}
