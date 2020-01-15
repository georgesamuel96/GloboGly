package com.example.georgesamuel.globogly.services

import com.example.georgesamuel.globogly.models.Destination
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DestinationService {

    @GET("destination")
    fun getDestinationList() : Call<List<Destination>>

    @GET("destination/{id}")
    fun getDestination(@Path("id") id: Int): Call<Destination>
}