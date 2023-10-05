package com.example.superherocoroutines

class Repository(private val repoClient: ApiClient) {
    suspend fun getCurrentMeme(): MutableList<JsData>{
        val apiInterface = repoClient.client.create(ApiInterface::class.java)
        return apiInterface.getMemes()
    }
}