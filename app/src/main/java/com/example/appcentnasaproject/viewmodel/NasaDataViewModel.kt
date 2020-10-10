package com.example.appcentnasaproject.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcentnasaproject.data.entities.NasaData
import com.example.appcentnasaproject.data.response.NasaDataResponse
import com.example.appcentnasaproject.repository.NasaDataRepository
import kotlinx.coroutines.launch

class NasaDataViewModel @ViewModelInject constructor(
    private val repository: NasaDataRepository
) : ViewModel(){

    suspend fun getNasaData(roverName : String, page : Int, cameraName : String) : NasaDataResponse{
        return repository.getNasaData(roverName, page, cameraName)
    }
    suspend fun getAllCamera(roverName : String, page : Int) : NasaDataResponse{
        return repository.getAllCamera(roverName, page)
    }

    //Local Data
      fun saveAllData(nasaData : List<NasaData>) = viewModelScope.launch {
        repository.saveAllData(nasaData)
    }
    suspend fun getLocalAllData(roverName: String) : List<NasaData> {
        return repository.getLocalAllData(roverName)
    }
    suspend fun getLocalSelectedCamera(cameraName: String, roverName: String) : List<NasaData>{
        return repository.getLocalSelectedCamera(cameraName, roverName)
    }
    fun deleteAllNasaData() = viewModelScope.launch { repository.deleteAllNasaData() }
    fun deleteAllCamera() = viewModelScope.launch { repository.deleteAllCamera() }
    fun deleteAllRovers() = viewModelScope.launch { repository.deleteAllRovers() }


}