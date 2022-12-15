package hr.foi.rampu.fridgium.rest

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RestNamirnicaReceptaServis {
    @GET("namirnicerecepta/namirnice/{id}")
    fun getNamirniceRecepta(@Path("id") id:Int)  : Call<RestNamirnicaReceptaResponse>
}