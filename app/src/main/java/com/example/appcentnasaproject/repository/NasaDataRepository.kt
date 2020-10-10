package com.example.appcentnasaproject.repository

import androidx.lifecycle.LiveData
import com.example.appcentnasaproject.data.api.NasaApi
import com.example.appcentnasaproject.data.db.NasaDataDao
import com.example.appcentnasaproject.data.entities.NasaData
import com.example.appcentnasaproject.data.response.NasaDataResponse
import javax.inject.Inject

class NasaDataRepository@Inject constructor(
    private val api : NasaApi,
    private val nasaDataDao: NasaDataDao
) : SafeApiRequest() {
    suspend fun getNasaData(roverName : String, page : Int, cameraName : String) : NasaDataResponse{
        return api.getRovers(roverName, page, cameraName)
    }
    suspend fun getAllCamera(roverName : String, page : Int) : NasaDataResponse{
        return api.getAllCamera(roverName, page)
    }

    //Local data operations
    suspend fun saveAllData(nasaData : List<NasaData>) = nasaDataDao.saveAllData(nasaData)
    suspend fun getLocalAllData(roverName: String) : List<NasaData> = nasaDataDao.getAllData(roverName)
    suspend fun getLocalSelectedCamera(cameraName: String, roverName: String) : List<NasaData> = nasaDataDao.getSelectedCamera(cameraName, roverName)
    suspend fun deleteAllNasaData() = nasaDataDao.deleteAllNasaData()
    suspend fun deleteAllCamera() = nasaDataDao.deleteAllCamera()
    suspend fun deleteAllRovers() = nasaDataDao.deleteAllRovers()
}