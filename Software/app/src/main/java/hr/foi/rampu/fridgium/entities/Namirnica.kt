package hr.foi.rampu.fridgium.entities

import com.google.gson.annotations.SerializedName

data class Namirnica(
    val id: Int,
    val naziv: String,
    var kolicina_hladnjak: Float,
    @SerializedName("mjerna_jedinica_id") val mjernaJedinica: MjernaJedinica,
    val kolicina_kupovina: Float
)
