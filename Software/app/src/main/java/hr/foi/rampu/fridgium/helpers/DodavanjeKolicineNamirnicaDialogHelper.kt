package hr.foi.rampu.fridgium.helpers

import android.view.View
import android.widget.EditText
import hr.foi.rampu.fridgium.R

class DodavanjeKolicineNamirnicaDialogHelper(private val view: View) {

    fun popuniKolicinu(kolicina: Int){
        val trenutnaKolicina = view.findViewById<EditText>(R.id.et_dodaj_kolicinu_namirnice)

        trenutnaKolicina.setText(kolicina.toString())
    }
}