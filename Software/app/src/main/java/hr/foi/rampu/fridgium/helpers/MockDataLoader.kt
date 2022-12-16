package hr.foi.rampu.fridgium.helpers

import hr.foi.rampu.fridgium.entities.MjernaJedinica
import hr.foi.rampu.fridgium.entities.Namirnica

object MockDataLoader {

    fun dajProbnePodatke(): List<Namirnica> = listOf(
        Namirnica(1, "Jaje", 3f,
            MjernaJedinica(1, "Komad"), 5f),
        Namirnica(2, "Mlijeko", 1f,
            MjernaJedinica(2, "l"), 5f),
        Namirnica(3, "Maslac", 300f,
            MjernaJedinica(3, "g"), 0f),
    )
}