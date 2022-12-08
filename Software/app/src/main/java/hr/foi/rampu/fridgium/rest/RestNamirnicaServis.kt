package hr.foi.rampu.fridgium.rest

import hr.foi.rampu.fridgium.entities.Namirnica
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RestNamirnicaServis {
    @GET("namirnice")
    fun dohvatiNamirnice(): Call<RestNamirnicaResponse>
    @POST("namirnica")
    fun dodajNamirnicu(@Body namirnica: Namirnica): Call<Namirnica>
}