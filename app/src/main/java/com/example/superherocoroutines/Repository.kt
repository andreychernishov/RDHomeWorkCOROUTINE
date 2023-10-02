package com.example.superherocoroutines

import retrofit2.Retrofit

class Repository(private val apiInterface: ApiInterface) {
    suspend fun getCurretnMeme(): MutableList<JsData>{

        return apiInterface.getMemes()
    }
}