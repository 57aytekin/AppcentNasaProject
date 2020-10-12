package com.example.appcentnasaproject.data.entities

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class NasaData(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val nasaDataId: Int,

    @SerializedName("camera")
    @Embedded
    @NonNull
    val nasaCamera: Camera? = null,

    @Embedded
    @NonNull
    val rover: Rover? = null,
    val earth_date: String,
    @SerializedName("img_src")
    val nasaDataImage: String,
    val sol: Int
)