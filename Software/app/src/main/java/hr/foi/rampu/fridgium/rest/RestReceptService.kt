package hr.foi.rampu.fridgium.rest

import hr.foi.rampu.fridgium.entities.Recept
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RestReceptService {
    @GET("recepti")
    fun getRecept() : Call<RestReceptResponse>
    @GET("recept/{naziv}")
    fun getReceptID(@Path("naziv") naziv: String) : Call<Recept>
    @DELETE("recept/{id}")
    fun deleteRecept(@Path("id") id:Int) : Call<Boolean>
    @POST("recepti")
    fun postRecept(@Body recept: Recept) : Call<Boolean>

}