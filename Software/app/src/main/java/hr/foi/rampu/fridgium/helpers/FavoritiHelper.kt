package hr.foi.rampu.fridgium.helpers

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import hr.foi.rampu.fridgium.entities.Namirnica
import hr.foi.rampu.fridgium.rest.RestNamirnicaResponse
import hr.foi.rampu.fridgium.rest.RestNamirnicaServis
import hr.foi.rampu.fridgium.rest.RestNamirnice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoritiHelper(view: View) {
    val pogled = view
    val rest: RestNamirnicaServis = RestNamirnice.namirnicaServis

    fun dodajUFavorite(nazivNamirnice: String, minimalnaKolicina: Float){
        pogled.context?.getSharedPreferences("favoriti_preferences", Context.MODE_PRIVATE)?.apply {
            edit().putFloat(nazivNamirnice, minimalnaKolicina).apply()
            Toast.makeText(
                pogled.context,
                "Namirnica $nazivNamirnice je dodana u favorite s kolicinom $minimalnaKolicina",
                Toast.LENGTH_LONG)
                .show()
        }
    }

    fun makniIzFavorita(nazivNamirnice: String){
        pogled.context?.getSharedPreferences("favoriti_preferences", Context.MODE_PRIVATE)?.apply {
                edit().remove(nazivNamirnice).apply()
                Toast.makeText(
                    pogled.context, "Namirnica $nazivNamirnice je maknuta iz favorita", Toast.LENGTH_LONG)
                    .show()
        }
    }

    fun provjeriFavorit(nazivNamirnice: String): Boolean{ //kaze dal je u favoritima il ne
        var provjeriPostojanost = -1f
        pogled.context?.getSharedPreferences("favoriti_preferences", Context.MODE_PRIVATE)?.apply {
            provjeriPostojanost = getFloat(nazivNamirnice, -1f)
        }
/*        Toast.makeText(
            pogled.context, "Namirnica $nazivNamirnice ime zadan $provjeriPostojanost", Toast.LENGTH_SHORT)
            .show()*/
        return provjeriPostojanost != -1f
    }

    fun dodajFavoritNaShoppingListu(nazivNamirnice: String){   //dodaje na shopping listu namirnicu
        var minKolicina = -1f

        pogled.context?.getSharedPreferences("favoriti_preferences", Context.MODE_PRIVATE)?.apply {
            minKolicina = getFloat(nazivNamirnice, -1f)
        }
        if (minKolicina != -1f){
            var mojaNamirnica : Namirnica
            rest.dohvatiNamirnicu(nazivNamirnice).enqueue(
                object : Callback<RestNamirnicaResponse> {
                    override fun onResponse(
                        call: Call<RestNamirnicaResponse>?,
                        response: Response<RestNamirnicaResponse>?
                    ) {
                        if (response != null) {
                            val rezultati = response.body()
                            mojaNamirnica = rezultati.results[0]

                            Log.d("BAZA",rezultati.toString())
                            Log.d("BAZA",rezultati.results.toString())
                            Log.d("BAZA",mojaNamirnica.toString())
                            Log.d("BAZA",mojaNamirnica.toString() + "ovo je moja namirnica")
                            Log.d("BAZA","kolicina u hladnjaku je " + mojaNamirnica.kolicina_hladnjak
                                    + " kolicina min je " + minKolicina + " kolicina kupovina je " + mojaNamirnica.kolicina_kupovina)
                            if (mojaNamirnica.kolicina_hladnjak < minKolicina){
                                val razlika = minKolicina - mojaNamirnica.kolicina_hladnjak
                                val kolicinaZaDodati : Float
                                if(razlika > mojaNamirnica.kolicina_kupovina){
                                    Log.d("BAZA","TU SAM")
                                    kolicinaZaDodati = razlika - mojaNamirnica.kolicina_kupovina
                                    dodajKolicinuUShoppingListu(mojaNamirnica, kolicinaZaDodati)
                                }
                            }

                        }
                    }
                    override fun onFailure(call: Call<RestNamirnicaResponse>?, t: Throwable?) {
                        Toast.makeText(
                            pogled.context, "Neuspjesno azuriranje namirnice u shopping listi", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
            )
        }
    }

    fun dodajKolicinuUShoppingListu(mojaNamirnica : Namirnica, razlika: Float){
        mojaNamirnica.kolicina_hladnjak = -1f
        mojaNamirnica.kolicina_kupovina += razlika

        rest.azurirajNamirnicu(mojaNamirnica).enqueue(
            object : Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>?, response: Response<Boolean>?) {
                    if (response != null) {
                        Log.d("BAZA",response.message().toString())
                    }
                }

                override fun onFailure(call: Call<Boolean>?, t: Throwable?) {
                    Toast.makeText(
                        pogled.context, "Neuspjesno azuriranje namirnice u shopping listi", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        )
    }

    fun dajVrijednostFavorita(nazivNamirnice: String): Float{ //vraca granicu favorita u frizideru
        var vrijednost = -1f
        pogled.context?.getSharedPreferences("favoriti_preferences", Context.MODE_PRIVATE)?.apply {
            vrijednost = getFloat(nazivNamirnice, -1f)
        }
        return vrijednost
    }
}