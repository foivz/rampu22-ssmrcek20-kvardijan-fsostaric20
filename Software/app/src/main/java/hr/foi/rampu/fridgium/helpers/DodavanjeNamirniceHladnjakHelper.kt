package hr.foi.rampu.fridgium.helpers

import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.entities.MjernaJedinica

class DodavanjeNamirniceHladnjakHelper(view: View) {
    private val mjernaJedinicaSpinner = view.findViewById<Spinner>(R.id.spn_kategorija_nove_namirnice)
    private val nazivNamirnice = view.findViewById<EditText>(R.id.et_naziv_nove_namirnice)
    private val kolicinaNamirnice = view.findViewById<EditText>(R.id.et_kolicina_nove_namirnice)
    val pogled = view

    fun popuniSpinner(mjerneJedinice : List<MjernaJedinica>){
        val spinnerAdapter = ArrayAdapter(
            pogled.context,
            android.R.layout.simple_spinner_item,
            mjerneJedinice
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mjernaJedinicaSpinner.adapter = spinnerAdapter
    }
}