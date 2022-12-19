package hr.foi.rampu.fridgium.helpers

import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.entities.Namirnica
import hr.foi.rampu.fridgium.rest.RestNamirnice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KolicinaHelper(private val view: View) {
    private val rest = RestNamirnice.namirnicaServis
    private val kolicina = view.findViewById<EditText>(R.id.et_kolicina_lista_za_kupovinu)
    private var mjernaJedinica = view.findViewById<TextView>(R.id.tv_mjerna_jedninica_lista_za_kupovinu)

    fun azurirajKolicinu(odabranaNamirnica: Namirnica) : Namirnica {
        odabranaNamirnica.kolicina_kupovina = kolicina.text.toString().toFloat()
        val kolicina_hladnjak = odabranaNamirnica.kolicina_hladnjak
        odabranaNamirnica.kolicina_hladnjak = -1f
        AzurirajUbazi(odabranaNamirnica)
        odabranaNamirnica.kolicina_hladnjak = kolicina_hladnjak
        return odabranaNamirnica
    }

    fun prikaziMJ(odabranaNamirnica: Namirnica) {
        mjernaJedinica.text = odabranaNamirnica.mjernaJedinica.naziv
    }

    fun AzurirajUbazi(namirnica: Namirnica) {
        rest.azurirajNamirnicu(namirnica).enqueue(
            object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>?, response: Response<Boolean>?) {
                    if (response != null) {
                        Log.d("BAZA", response.message().toString())
                    }
                }

                override fun onFailure(call: Call<Boolean>?, t: Throwable?) {
                    prikazGreskeSaServerom()
                }

            }
        )
    }

    private fun prikazGreskeSaServerom() {
        Toast.makeText(
            view.context,
            "Došlo je do greške sa servisom!",
            Toast.LENGTH_LONG
        ).show()
    }

    fun prikazGreskeUpisa() {
        Toast.makeText(
            view.context,
            "Upišite sve podatke!",
            Toast.LENGTH_LONG
        ).show()
    }

    fun provjeriUpis(): Boolean {
        return !kolicina.text.isEmpty()
    }
}