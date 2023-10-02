package com.example.superherocoroutines

import android.app.Application
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication: Application() {
    lateinit var apiInterface: ApiInterface

    lateinit var repo: Repository
    override fun onCreate() {
        super.onCreate()
        instance = this
        apiInterface = getApiClient().create(ApiInterface::class.java)
        repo = Repository(apiInterface)
    }
    private fun getApiClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://akabab.github.io")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()
    }
    companion object {
        private lateinit var instance: MyApplication
        fun getApp(): MyApplication {
            return instance
        }
    }
}
