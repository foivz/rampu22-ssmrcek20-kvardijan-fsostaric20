package hr.foi.rampu.fridgium.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestMJedinica {
    private const val BASE_URL = "https://site--api--hm2b4zvqj8rq.code.run/"

    private var instance: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val mJedinicaServis: RestMJedinicaServis = instance.create(RestMJedinicaServis::class.java)
}