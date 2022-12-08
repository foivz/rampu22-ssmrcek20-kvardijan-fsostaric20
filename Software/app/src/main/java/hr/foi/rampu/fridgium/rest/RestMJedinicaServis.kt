package hr.foi.rampu.fridgium.rest

import retrofit2.Call
import retrofit2.http.GET

interface RestMJedinicaServis {
    @GET("mjerna_jedinica")
    fun dohvatiMJedinice(): Call<RestMJedinicaResponse>
}