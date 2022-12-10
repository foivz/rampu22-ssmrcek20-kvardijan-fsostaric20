package hr.foi.rampu.fridgium.entities

data class MjernaJedinica(
    val id: Int,
    val naziv: String)
{
    override fun toString(): String {
        return naziv
    }
}
