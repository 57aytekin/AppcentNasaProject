package com.example.appcentnasaproject.data.response

import androidx.lifecycle.LiveData
import com.example.appcentnasaproject.data.entities.NasaData
import com.google.gson.annotations.SerializedName

data class NasaDataResponse(
    @SerializedName("photos")
    val photoList : List<NasaData>
)