package com.flightsearch.di

import android.content.Context
import com.flightsearch.db.AppDatabase
import com.flightsearch.db.FlightDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {
    @Provides
    @Singleton
    fun provideDB(@ApplicationContext app: Context) =
        AppDatabase.getDatabase(app)
    /*Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "app_db"
    )
        .createFromAsset("database/flight_search.db")
        .fallbackToDestructiveMigration()
        .build()*/

    @Provides
    @Singleton
    fun provideDBDao(db: AppDatabase): FlightDao {
        return db.flightDao()
    }
}