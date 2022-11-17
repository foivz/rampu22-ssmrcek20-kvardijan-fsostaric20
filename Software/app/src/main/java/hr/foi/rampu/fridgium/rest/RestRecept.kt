package hr.foi.rampu.fridgium.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestRecept {
    const val BASE_URL = "https://fridgiumappapi.herokuapp.com/"

    private var instance: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val ReceptService = instance.create(RestReceptService::class.java)
}