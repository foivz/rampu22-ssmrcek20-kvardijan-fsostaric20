package hr.foi.rampu.fridgium.entities

import com.google.gson.annotations.SerializedName

data class Namirnica(
    val id: Int,
    var naziv: String,
    var kolicina_hladnjak: Float,
    @SerializedName("mjerna_jedinica_id") var mjernaJedinica: MjernaJedinica,
    var kolicina_kupovina: Float
)
