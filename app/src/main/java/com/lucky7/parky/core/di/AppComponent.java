package com.lucky7.parky.core.di;

import com.lucky7.parky.features.auth.data.data_resource.remote.AuthRemoteDataResourceImpl;
import com.lucky7.parky.features.auth.data.data_resource.remote.UserRemoteDataResourceImpl;
import com.lucky7.parky.features.auth.data.repository.AuthRepositoryImpl;
import com.lucky7.parky.features.auth.data.repository.UserRepositoryImpl;

import dagger.Component;

@Component(modules = {FirebaseModule.class, AuthModule.class, UserModule.class})
public interface AppComponent {
    void inject(AuthRemoteDataResourceImpl authRemoteDataResourceImpl);
    void inject(AuthRepositoryImpl authRepositoryImpl);
    void inject(UserRemoteDataResourceImpl userRemoteDataResourceImpl);
    void inject(UserRepositoryImpl userRepositoryImpl);
}