package com.lucky7.parky.core.di;

import com.google.firebase.firestore.FirebaseFirestore;
import com.lucky7.parky.features.park.data.data_source.remote.ParkHistoryRemoteDataSource;
import com.lucky7.parky.features.park.data.data_source.remote.ParkHistoryRemoteDataSourceImpl;
import com.lucky7.parky.features.park.data.repository.ParkHistoryRepositoryImpl;
import com.lucky7.parky.features.park.domain.repository.ParkHistoryRepository;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ParkHistoryModule{
    @Provides
    @Singleton
    public ParkHistoryRemoteDataSource provideParkHistoryRemoteDataSource(FirebaseFirestore firestore) {
        return new ParkHistoryRemoteDataSourceImpl(firestore);
    }

    @Provides
    @Singleton
    public ParkHistoryRepository provideParkHistoryRepository(ParkHistoryRemoteDataSource authRemoteDataSource) {
        return new ParkHistoryRepositoryImpl(authRemoteDataSource);
    }
}
