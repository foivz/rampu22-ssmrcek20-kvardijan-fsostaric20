package hr.foi.rampu.fridgium.rest

import hr.foi.rampu.fridgium.entities.Namirnica
import hr.foi.rampu.fridgium.entities.UnosNamirniceShopping
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface RestNamirnicaServis {
    @GET("namirnice")
    fun dohvatiNamirnice(): Call<RestNamirnicaResponse>
    @Headers("Content-Type: application/json")
    @POST("namirnice")
    fun dodajNamirnicu(@Body unosNamirniceShopping: UnosNamirniceShopping): Call<Boolean>
}