package hr.foi.rampu.fridgium.entities

data class Namirnica(
    val id: Int,
    val naziv: String,
    val kolicina_hladnjak: Int,
    val mjernaJedinica: MjernaJedinica,
    val kolicina_kupovina: Int
)
