package hr.foi.rampu.fridgium.helpers

import hr.foi.rampu.fridgium.entities.MjernaJedinica
import hr.foi.rampu.fridgium.entities.Namirnica

object MockDataLoader {

    fun DajProbnePodatke(): List<Namirnica> = listOf(
        Namirnica(1, "Jaje", 3,
            MjernaJedinica(1, "Komad"), 5),
        Namirnica(2, "Mlijeko", 1,
            MjernaJedinica(2, "l"), 5),
        Namirnica(3, "Maslac", 300,
            MjernaJedinica(3, "g"), 0),
    )

}