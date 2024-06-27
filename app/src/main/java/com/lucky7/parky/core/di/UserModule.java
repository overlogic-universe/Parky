package com.lucky7.parky.core.di;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lucky7.parky.features.auth.data.data_source.remote.UserRemoteDataSource;
import com.lucky7.parky.features.auth.data.data_source.remote.UserRemoteDataSourceImpl;
import com.lucky7.parky.features.auth.data.repository.UserRepositoryImpl;
import com.lucky7.parky.features.auth.domain.repository.UserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UserModule{
    @Provides
    @Singleton
    public UserRemoteDataSource provideUserRemoteDataSource(FirebaseFirestore firestore, FirebaseAuth auth) {
        return new UserRemoteDataSourceImpl(firestore, auth);
    }

    @Provides
    @Singleton
    public UserRepository provideUserRepository(UserRemoteDataSource userRemoteDataSource) {
        return new UserRepositoryImpl(userRemoteDataSource);
    }
}