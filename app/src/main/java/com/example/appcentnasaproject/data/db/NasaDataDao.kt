package com.example.appcentnasaproject.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appcentnasaproject.data.entities.NasaData

@Dao
interface NasaDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllData(nasaData : List<NasaData>)

    @Query("Select * FROM nasadata where roverName= :roverName ")
    suspend fun getAllData(roverName : String) : List<NasaData>

    @Query("Select * FROM nasadata where cameraName= :cameraName And roverName= :roverName ")
    suspend fun getSelectedCamera(cameraName : String, roverName : String) : List<NasaData>

    @Query("DELETE FROM nasadata")
    fun deleteAllNasaData()
    @Query("DELETE FROM nasacamera")
    fun deleteAllCamera()
    @Query("DELETE FROM rover")
    fun deleteAllRovers()
}