package hr.foi.rampu.fridgium.helpers

import android.view.View
import android.widget.EditText
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.entities.Namirnica

class DodavanjeKolicineNamirnicaDialogHelper(view: View) {

    val trenutnaKolicina = view.findViewById<EditText>(R.id.et_dodaj_kolicinu_namirnice)

    fun popuniKolicinu(kolicina: Int){
        trenutnaKolicina.setText(kolicina.toString())
    }

    fun azurirajNamirnicu(namirnica: Namirnica){
        val kol = namirnica.kolicina_hladnjak + trenutnaKolicina.text.toString().toInt()
        val azuriranaNamirnica = Namirnica(
            namirnica.id,
            namirnica.naziv,
            kol,
            namirnica.mjernaJedinica,
            namirnica.kolicina_kupovina,
        )

        //tuj napravi upit prema bazi
    }
}