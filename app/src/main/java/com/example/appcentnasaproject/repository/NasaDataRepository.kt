package com.example.appcentnasaproject.repository

import androidx.lifecycle.LiveData
import com.example.appcentnasaproject.data.api.NasaApi
import com.example.appcentnasaproject.data.response.NasaDataResponse
import javax.inject.Inject

class NasaDataRepository@Inject constructor(
    private val api : NasaApi
) : SafeApiRequest() {
    suspend fun getNasaData(roverName : String, page : Int, cameraName : String) : NasaDataResponse{
        return api.getRovers(roverName, page, cameraName)
    }
    suspend fun getAllCamera(roverName : String, page : Int) : NasaDataResponse{
        return api.getAllCamera(roverName, page)
    }
}