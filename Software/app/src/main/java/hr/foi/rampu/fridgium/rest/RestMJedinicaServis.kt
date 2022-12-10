package hr.foi.rampu.fridgium.rest

import retrofit2.Call
import retrofit2.http.GET

interface RestMJedinicaServis {
    @GET("mjernejedinice")
    fun dohvatiMJedinice(): Call<RestMJedinicaResponse>
}