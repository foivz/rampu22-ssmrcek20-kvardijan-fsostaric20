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

class OduzimanjeKolicineNamirnicaDialogHelper(view: View) {

    private val trenutnaKolicina: EditText = view.findViewById(R.id.et_oduzmi_kolicinu_namirnice)
    private val rest = RestNamirnice.namirnicaServis
    private val pogled = view
    val pomagacFavorita = FavoritiHelper(pogled)

    fun azurirajNamirnicu(namirnica: Namirnica){
        val kol = namirnica.kolicina_hladnjak - trenutnaKolicina.text.toString().toFloat()
        val azuriranaNamirnica = Namirnica(
            namirnica.id,
            namirnica.naziv,
            kol,
            namirnica.mjernaJedinica,
            -1f,
        )

        rest.azurirajNamirnicu(azuriranaNamirnica).enqueue(
            object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>?, response: Response<Boolean>?) {
                    if (response != null) {
                        Log.d("BAZA",response.message().toString())
                        pomagacFavorita.dodajFavoritNaShoppingListu(azuriranaNamirnica.naziv)
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