package com.example.georgesamuel.globogly.services

import com.example.georgesamuel.globogly.models.Destination
import retrofit2.Call
import retrofit2.http.GET

interface DestinationService {

    @GET("destination")
    fun getDestinationList() : Call<List<Destination>>
}