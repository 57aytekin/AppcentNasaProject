package com.example.appcentnasaproject.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Rover(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val roverId: Int,
    val landing_date: String,
    val launch_date: String,
    @SerializedName("name")
    val roverName: String,
    val status: String
)