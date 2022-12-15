package hr.foi.rampu.fridgium.rest

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface RestReceptService {
    @GET("recepti")
    fun getRecept() : Call<RestReceptResponse>
    @DELETE("recept/{id}")
    fun deleteRecept(@Path("id") id:Int) : Call<Boolean>

}