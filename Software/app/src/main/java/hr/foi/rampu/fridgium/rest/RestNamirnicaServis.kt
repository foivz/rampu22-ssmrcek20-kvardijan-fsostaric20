package hr.foi.rampu.fridgium.rest

import hr.foi.rampu.fridgium.entities.Namirnica
import retrofit2.Call
import retrofit2.http.*

interface RestNamirnicaServis {
    @GET("namirnice")
    fun dohvatiNamirnice(): Call<RestNamirnicaResponse>

    @Headers("Content-Type: application/json")

    @GET("namirnica/{naziv}")
    fun dohvatiNamirnicu(@Path("naziv") naziv: String): Call<RestNamirnicaResponse>

    @POST("namirnice")
    fun dodajNamirnicu(@Body namirnica: Namirnica): Call<Boolean>

    @PUT("namirnice")
    fun azurirajNamirnicu(@Body namirnica: Namirnica): Call<Boolean>

    @PUT("namirnica")
    fun azurirajNamirnicuNazivMJ(@Body namirnica: Namirnica): Call<Boolean>

    @DELETE("namirnice/{naziv}")
    fun izbrisiNamirnicu(@Path("naziv") naziv: String): Call<Boolean>
}