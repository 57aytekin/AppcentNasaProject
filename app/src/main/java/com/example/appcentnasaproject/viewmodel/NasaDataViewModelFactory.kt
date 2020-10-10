package com.example.appcentnasaproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appcentnasaproject.repository.NasaDataRepository

@Suppress("UNCHECKED_CAST")
class NasaDataViewModelFactory(
    private val repository: NasaDataRepository
) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NasaDataViewModel(repository) as T
    }
}