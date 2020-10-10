package com.example.appcentnasaproject.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.appcentnasaproject.data.response.NasaDataResponse
import com.example.appcentnasaproject.repository.NasaDataRepository
import com.example.appcentnasaproject.util.Coroutines
import kotlinx.coroutines.CoroutineScope

class NasaDataViewModel @ViewModelInject constructor(
    private val repository: NasaDataRepository
) : ViewModel(){

    suspend fun getNasaData(roverName : String, page : Int, cameraName : String) : NasaDataResponse{
        return repository.getNasaData(roverName, page, cameraName)
    }
    suspend fun getAllCamera(roverName : String, page : Int) : NasaDataResponse{
        return repository.getAllCamera(roverName, page)
    }
}