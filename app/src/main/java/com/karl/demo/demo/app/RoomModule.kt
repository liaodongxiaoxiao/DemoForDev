package com.karl.demo.demo.app

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.karl.demo.demo.model.AppDatabase
import com.karl.demo.demo.model.DivisionDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "weather.db"
        ).allowMainThreadQueries()
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .build()
    }

    @Singleton
    @Provides
    fun provideDivisionDAO(appDatabase: AppDatabase): DivisionDAO {
        return appDatabase.divisionDao()
    }
}