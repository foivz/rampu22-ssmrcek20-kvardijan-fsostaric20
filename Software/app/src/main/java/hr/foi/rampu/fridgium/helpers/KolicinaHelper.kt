package hr.foi.rampu.fridgium.helpers

import android.view.View
import android.widget.EditText
import android.widget.TextView
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.entities.Namirnica

class KolicinaHelper(private val view: View) {

    private val kolicina = view.findViewById<EditText>(R.id.et_kolicina_lista_za_kupovinu)
    private var mjernaJedinica = view.findViewById<TextView>(R.id.tv_mjerna_jedninica_lista_za_kupovinu)

    fun azurirajKolicinu(odabranaNamirnica: Namirnica) : Namirnica {
        TODO("Not yet implemented")
    }

    fun prikaziMJ(odabranaNamirnica: Namirnica) {
        mjernaJedinica.text = odabranaNamirnica.mjernaJedinica.naziv
    }
}