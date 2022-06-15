package com.example.proyectofinal.entities

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET("getDtis")
    fun getBackEnd(): Call<List<Dti>>
}