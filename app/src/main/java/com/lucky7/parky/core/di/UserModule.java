package com.lucky7.parky.core.di;

import com.google.firebase.firestore.FirebaseFirestore;
import com.lucky7.parky.features.auth.data.data_resource.remote.UserRemoteDataResource;
import com.lucky7.parky.features.auth.data.data_resource.remote.UserRemoteDataResourceImpl;
import com.lucky7.parky.features.auth.data.repository.UserRepositoryImpl;
import com.lucky7.parky.features.auth.domain.repository.UserRepository;

import javax.inject.Singleton;

import dagger.Provides;

public class UserModule{
    @Provides
    @Singleton
    public UserRemoteDataResource provideUserRemoteDataSource(FirebaseFirestore firestore) {
        return new UserRemoteDataResourceImpl(firestore);
    }

    @Provides
    @Singleton
    public UserRepository provideUserRepository(UserRemoteDataResource userRemoteDataResource) {
        return new UserRepositoryImpl(userRemoteDataResource);
    }
}