package hr.foi.rampu.fridgium.rest

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class tijelo(var kolicina: Float, var receptID : Int , var namirnicaID : Int)

interface RestNamirnicaReceptaServis {
    @GET("namirnicerecepta/namirnice/{id}")
    fun getNamirniceRecepta(@Path("id") id:Int)  : Call<RestNamirnicaReceptaResponse>
    @DELETE("namirnicerecepta/recepti/{id}")
    fun deleteNamirniceRecepta(@Path("id") id:Int) : Call<Boolean>

    @POST("namirnicerecepta")
    fun postNamirniceRecepta(@Body tijelo: tijelo) : Call<Boolean>
}