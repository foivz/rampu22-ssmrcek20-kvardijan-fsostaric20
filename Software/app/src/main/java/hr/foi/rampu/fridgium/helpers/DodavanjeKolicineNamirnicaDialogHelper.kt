package hr.foi.rampu.fridgium.helpers

import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.entities.Namirnica
import hr.foi.rampu.fridgium.rest.RestNamirnice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DodavanjeKolicineNamirnicaDialogHelper(view: View) {

    val trenutnaKolicina = view.findViewById<EditText>(R.id.et_dodaj_kolicinu_namirnice)
    private val rest = RestNamirnice.namirnicaServis
    val pogled = view

    fun popuniKolicinu(kolicina: Int){
        trenutnaKolicina.setText(kolicina.toString())
    }

    fun azurirajNamirnicu(namirnica: Namirnica){
        val kol = namirnica.kolicina_hladnjak + trenutnaKolicina.text.toString().toFloat()
        val azuriranaNamirnica = Namirnica(
            namirnica.id,
            namirnica.naziv,
            kol,
            namirnica.mjernaJedinica,
            -1f,
        )

        rest.azurirajNamirnicu(azuriranaNamirnica).enqueue(
            object : Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>?, response: Response<Boolean>?) {
                    if (response != null) {
                        Log.d("BAZA",response.message().toString())
                    }
                }

                override fun onFailure(call: Call<Boolean>?, t: Throwable?) {
                    prikaziPorukuGreske()
                }
            }
        )
    }

    fun prikaziPorukuGreske(){
        Toast.makeText(pogled.context, "Dodavanje neuspjesno", Toast.LENGTH_LONG).show()
    }
}