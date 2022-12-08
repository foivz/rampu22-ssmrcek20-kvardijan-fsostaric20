package hr.foi.rampu.fridgium.entities

import com.google.gson.annotations.SerializedName

data class Namirnica(
    val id: Int?,
    val naziv: String,
    val kolicina_hladnjak: Int,
    @SerializedName("mjerna_jedinica_id") val mjernaJedinica: MjernaJedinica,
    val kolicina_kupovina: Int
)
