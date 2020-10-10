package com.example.appcentnasaproject.di

import android.content.Context
import androidx.room.Room
import com.example.appcentnasaproject.data.api.NasaApi
import com.example.appcentnasaproject.data.db.AppDatabase
import com.example.appcentnasaproject.util.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.currentCoroutineContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext app : Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "nasaData_db"
    ).fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideNasaDataDao(db : AppDatabase) = db.nasaDataDao()

    @Singleton
    @Provides
    fun provideNasaApi(networkConnectionInterceptor : NetworkConnectionInterceptor) = NasaApi(networkConnectionInterceptor)

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context) = context
}