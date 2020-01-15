package com.example.georgesamuel.globogly.services

import com.example.georgesamuel.globogly.models.Destination
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface DestinationService {

    @GET("destination")
    fun getDestinationList() : Call<List<Destination>>

    /*
        Instead of using query, you can use HashMap to filter
     */
    @GET("destination")
    fun getDestinationList(@QueryMap filter: HashMap<String, String>): Call<List<Destination>>

    @GET("destination/{id}")
    fun getDestination(@Path("id") id: Int): Call<Destination>
}