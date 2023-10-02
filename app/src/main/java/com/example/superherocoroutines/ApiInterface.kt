package com.example.superherocoroutines

import retrofit2.http.GET

interface ApiInterface {
    @GET("/superhero-api/api/all.json")
    suspend fun getMemes(): MutableList<JsData>
}