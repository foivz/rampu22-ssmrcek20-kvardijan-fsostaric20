package hr.foi.rampu.fridgium.rest

import hr.foi.rampu.fridgium.entities.Namirnica
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface RestNamirnicaServis {
    @GET("namirnice")
    fun dohvatiNamirnice(): Call<RestNamirnicaResponse>

    @PUT("namirnice")
    fun azurirajNamirnicu(@Body namirnica: Namirnica): Call<Boolean>
}