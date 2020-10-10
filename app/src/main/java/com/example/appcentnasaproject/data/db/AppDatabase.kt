package com.example.appcentnasaproject.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appcentnasaproject.data.entities.Camera
import com.example.appcentnasaproject.data.entities.NasaData
import com.example.appcentnasaproject.data.entities.Rover

@Database(
    entities =[NasaData::class, Camera::class, Rover::class],
    exportSchema = false,
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun nasaDataDao() : NasaDataDao
}