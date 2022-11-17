package hr.foi.rampu.fridgium.rest

import retrofit2.Call
import retrofit2.http.GET

interface RestNamirnicaServis {
    @GET("namirnice")
    fun dohvatiNamirnice(): Call<RestNamirnicaResponse>
}