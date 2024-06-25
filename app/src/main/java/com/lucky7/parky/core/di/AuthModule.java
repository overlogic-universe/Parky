package com.lucky7.parky.core.di;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lucky7.parky.features.auth.data.data_resource.remote.AuthRemoteDataResource;
import com.lucky7.parky.features.auth.data.data_resource.remote.AuthRemoteDataResourceImpl;
import com.lucky7.parky.features.auth.data.data_resource.remote.UserRemoteDataResource;
import com.lucky7.parky.features.auth.data.data_resource.remote.UserRemoteDataResourceImpl;
import com.lucky7.parky.features.auth.data.repository.AuthRepositoryImpl;
import com.lucky7.parky.features.auth.data.repository.UserRepositoryImpl;
import com.lucky7.parky.features.auth.domain.repository.AuthRepository;
import com.lucky7.parky.features.auth.domain.repository.UserRepository;

import javax.inject.Singleton;

import dagger.Provides;

public class AuthModule{
    @Provides
    @Singleton
    public AuthRemoteDataResource provideAuthRemoteDataSource(FirebaseFirestore firestore, FirebaseAuth auth) {
        return new AuthRemoteDataResourceImpl(firestore, auth);
    }

    @Provides
    @Singleton
    public AuthRepository provideAuthRepository(AuthRemoteDataResource authRemoteDataResource) {
        return new AuthRepositoryImpl(authRemoteDataResource);
    }
}
