package com.lucky7.parky.core.di;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lucky7.parky.features.auth.data.data_source.remote.AuthRemoteDataSource;
import com.lucky7.parky.features.auth.data.data_source.remote.AuthRemoteDataSourceImpl;
import com.lucky7.parky.features.auth.data.data_source.remote.UserRemoteDataSource;
import com.lucky7.parky.features.auth.data.repository.AuthRepositoryImpl;
import com.lucky7.parky.features.auth.domain.repository.AuthRepository;
import com.lucky7.parky.features.auth.domain.repository.UserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AuthModule{
    @Provides
    @Singleton
    public AuthRemoteDataSource provideAuthRemoteDataSource(FirebaseFirestore firestore, FirebaseAuth auth) {
        return new AuthRemoteDataSourceImpl(firestore, auth);
    }

    @Provides
    @Singleton
    public AuthRepository provideAuthRepository(AuthRemoteDataSource authRemoteDataSource, UserRepository userRepository) {
        return new AuthRepositoryImpl(authRemoteDataSource, userRepository);
    }
}
