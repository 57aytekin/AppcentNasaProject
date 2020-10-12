package com.example.appcentnasaproject.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "NasaCamera")
data class Camera(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val cameraId: Int,
    @SerializedName("name")
    val cameraName: String,
    val full_name: String,
    @SerializedName("rover_id")
    val camera_rover_id: Int
)