package com.example.appcentnasaproject.data.entities

data class NasaData(
    val camera: Camera,
    val earth_date: String,
    val id: Int,
    val img_src: String,
    val rover: Rover,
    val sol: Int
)