package com.example.appcentnasaproject.data.api

import com.example.appcentnasaproject.data.response.NasaDataResponse
import com.example.appcentnasaproject.util.NetworkConnectionInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


//https://api.nasa.gov/mars-photos/api/v1/rovers/opportunity/photos?sol=1000&api_key=GSqwPVQts9WEqV0ExIpIrdqINr0IPSKb1G4L0qfN
//https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&page=1&camera=MAST&api_key=DEMO_KEY
const val API_KEY = /*"DEMO_KEY"*/"GSqwPVQts9WEqV0ExIpIrdqINr0IPSKb1G4L0qfN"
const val BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/"

interface NasaApi {

    @GET("rovers/{rover_name}/photos?sol=1000")
    suspend fun getRovers(
        @Path("rover_name") roverName : String,
        @Query("page") page : Int,
        @Query("camera") camera : String
    ): NasaDataResponse

    @GET("rovers/{rover_name}/photos?sol=1000")
    suspend fun getAllCamera(
        @Path("rover_name") roverName : String,
        @Query("page") page : Int
    ): NasaDataResponse

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): NasaApi {
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NasaApi::class.java)
        }
    }
}