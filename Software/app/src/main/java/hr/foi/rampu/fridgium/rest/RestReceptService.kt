package hr.foi.rampu.fridgium.rest

import retrofit2.Call
import retrofit2.http.GET

interface RestReceptService {
    @GET("recepti")
    fun getRecept() : Call<RestReceptResponse>


}