package com.lucky7.parky.core.di;

import com.lucky7.parky.MyApp;
import com.lucky7.parky.features.auth.data.data_source.local.AuthLocalDataSourceImpl;
import com.lucky7.parky.features.auth.data.data_source.remote.AuthRemoteDataSourceImpl;
import com.lucky7.parky.features.auth.data.data_source.remote.UserRemoteDataSourceImpl;
import com.lucky7.parky.features.auth.data.repository.AuthRepositoryImpl;
import com.lucky7.parky.features.auth.data.repository.UserRepositoryImpl;
import com.lucky7.parky.features.auth.presentation.AddUserActivity;
import com.lucky7.parky.features.auth.presentation.AdminHomeActivity;
import com.lucky7.parky.features.auth.presentation.SplashScreenActivity;
import com.lucky7.parky.features.park.presentation.HistoryActivity;
import com.lucky7.parky.features.auth.presentation.LoginActivity;
import com.lucky7.parky.features.park.data.data_source.remote.ParkHistoryRemoteDataSourceImpl;
import com.lucky7.parky.features.park.data.repository.ParkHistoryRepositoryImpl;
import com.lucky7.parky.features.park.presentation.ParkedUserFragment;
import com.lucky7.parky.features.park.presentation.ParkingHistoryFragment;
import com.lucky7.parky.features.park.presentation.QRCodeScannerActivity;
import com.lucky7.parky.features.auth.presentation.UserHomeActivity;
import com.lucky7.parky.features.auth.presentation.UserListActivity;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Provides;

@Singleton
@Component(modules = {
        FirebaseModule.class, AuthModule.class, UserModule.class,
        ParkHistoryModule.class, SharedPreferencesModule.class
})
public interface AppComponent {
    void inject(MyApp myApp);

    void inject(AuthLocalDataSourceImpl authLocalDataSourceImpl);
    void inject(AuthRemoteDataSourceImpl authRemoteDataResourceImpl);
    void inject(AuthRepositoryImpl authRepositoryImpl);
    void inject(UserRemoteDataSourceImpl userRemoteDataResourceImpl);
    void inject(UserRepositoryImpl userRepositoryImpl);
    void inject(ParkHistoryRemoteDataSourceImpl parkHistoryRemoteDataResourceImpl);
    void inject(ParkHistoryRepositoryImpl parkHistoryRepositoryImpl);
    void inject(SplashScreenActivity splashScreenActivity);
    void inject(AddUserActivity addUserActivity);
    void inject(HistoryActivity historyActivity);
    void inject(LoginActivity loginActivity);
    void inject(QRCodeScannerActivity qrCodeScannerActivity);
    void inject(UserHomeActivity userHomeActivity);
    void inject(AdminHomeActivity adminHomeActivity);
    void inject(UserListActivity userListActivity);
    void inject(ParkedUserFragment parkedUserFragment);
    void inject(ParkingHistoryFragment parkingHistoryFragment);
}