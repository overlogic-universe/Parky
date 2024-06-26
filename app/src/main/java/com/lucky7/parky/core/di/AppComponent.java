package com.lucky7.parky.core.di;

import com.lucky7.parky.MyApp;
import com.lucky7.parky.features.auth.data.data_source.remote.AuthRemoteDataSourceImpl;
import com.lucky7.parky.features.auth.data.data_source.remote.UserRemoteDataSourceImpl;
import com.lucky7.parky.features.auth.data.repository.AuthRepositoryImpl;
import com.lucky7.parky.features.auth.data.repository.UserRepositoryImpl;
import com.lucky7.parky.features.auth.presentation.AddUserActivity;
import com.lucky7.parky.features.park.presentation.HistoryActivity;
import com.lucky7.parky.features.auth.presentation.LoginActivity;
import com.lucky7.parky.features.park.data.data_source.remote.ParkHistoryRemoteDataSourceImpl;
import com.lucky7.parky.features.park.data.repository.ParkHistoryRepositoryImpl;
import com.lucky7.parky.features.park.presentation.QRCodeScannerActivity;
import com.lucky7.parky.features.auth.presentation.UserHomeActivity;
import com.lucky7.parky.features.auth.presentation.UserListActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {FirebaseModule.class, AuthModule.class, UserModule.class, ParkHistoryModule.class})
public interface AppComponent {
    void inject(MyApp myApp);

    void inject(AuthRemoteDataSourceImpl authRemoteDataResourceImpl);
    void inject(AuthRepositoryImpl authRepositoryImpl);
    void inject(UserRemoteDataSourceImpl userRemoteDataResourceImpl);
    void inject(UserRepositoryImpl userRepositoryImpl);
    void inject(ParkHistoryRemoteDataSourceImpl parkHistoryRemoteDataResourceImpl);
    void inject(ParkHistoryRepositoryImpl parkHistoryRepositoryImpl);
    void inject(AddUserActivity addUserActivity);
    void inject(HistoryActivity historyActivity);
    void inject(LoginActivity loginActivity);
    void inject(QRCodeScannerActivity qrCodeScannerActivity);
    void inject(UserHomeActivity userHomeActivity);
    void inject(UserListActivity userListActivity);
}