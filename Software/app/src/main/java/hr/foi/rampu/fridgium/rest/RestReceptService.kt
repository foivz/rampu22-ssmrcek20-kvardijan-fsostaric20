package hr.foi.rampu.fridgium.rest

interface RestReceptService {
    @GET("recepti")
    fun getRecept() : Call<RestReceptResponse>
}