package hr.foi.rampu.fridgium.helpers

import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.adapters.NamirnicaAdapter
import hr.foi.rampu.fridgium.entities.MjernaJedinica
import hr.foi.rampu.fridgium.entities.Namirnica
import hr.foi.rampu.fridgium.rest.RestNamirnicaResponse
import hr.foi.rampu.fridgium.rest.RestNamirnicaServis
import hr.foi.rampu.fridgium.rest.RestNamirnice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DodavanjeNamirniceHladnjakHelper(view: View) {
    private val mjernaJedinicaSpinner =
        view.findViewById<Spinner>(R.id.spn_kategorija_nove_namirnice)
    private val nazivNamirnice = view.findViewById<EditText>(R.id.et_naziv_nove_namirnice)
    private val kolicinaNamirnice = view.findViewById<EditText>(R.id.et_kolicina_nove_namirnice)
    val pogled = view
    val rest: RestNamirnicaServis = RestNamirnice.namirnicaServis
    val pomagacFavorita = FavoritiHelper(pogled)

    fun popuniSpinner(mjerneJedinice: List<MjernaJedinica>) {
        val spinnerAdapter = ArrayAdapter(
            pogled.context,
            android.R.layout.simple_spinner_item,
            mjerneJedinice
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mjernaJedinicaSpinner.adapter = spinnerAdapter
    }

    fun izgradiObjektNoveNamirnice(): Namirnica {
        val odabranaMJ = mjernaJedinicaSpinner.selectedItem as MjernaJedinica
        return Namirnica(
            0,
            nazivNamirnice.text.toString().lowercase().replaceFirstChar { it.uppercaseChar() },
            kolicinaNamirnice.text.toString().toFloat(),
            odabranaMJ,
            0f
        )
    }

    fun dodajNamirnicuUBazu(namirnica: Namirnica) {
        namirnica.naziv = namirnica.naziv.lowercase().replaceFirstChar { it.uppercaseChar() }
        if (namirnica.kolicina_hladnjak < 0f) namirnica.kolicina_hladnjak = 0f
        rest.dodajNamirnicu(namirnica).enqueue(
            object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>?, response: Response<Boolean>?) {
                    if (response != null) {
                        Log.d("BAZA", response.message().toString())
                    }
                }

                override fun onFailure(call: Call<Boolean>?, t: Throwable?) {
                    Toast.makeText(pogled.context, "Dodavanje neuspješno", Toast.LENGTH_LONG).show()
                }

            }
        )
    }

    fun azurirajNamirnicuUBazi(namirnica: Namirnica) {
        namirnica.naziv = namirnica.naziv.lowercase().replaceFirstChar { it.uppercaseChar() }
        if (namirnica.kolicina_hladnjak < 0f) namirnica.kolicina_hladnjak = 0f
        rest.azurirajNamirnicu(namirnica).enqueue(
            object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>?, response: Response<Boolean>?) {
                    if (response != null) {
                        Log.d("BAZA", response.message().toString())
                        pomagacFavorita.dodajFavoritNaShoppingListu(namirnica.naziv)
                    }
                }

                override fun onFailure(call: Call<Boolean>?, t: Throwable?) {
                    Toast.makeText(pogled.context, "Ažuriranje neušpjesno", Toast.LENGTH_LONG).show()
                }

            }
        )
    }

    fun provjeriNamirnicu(novaNamirnica: Namirnica, namirnicaAdapter: NamirnicaAdapter) {
        rest.dohvatiNamirnice().enqueue(
            object : Callback<RestNamirnicaResponse> {
                override fun onResponse(
                    call: Call<RestNamirnicaResponse>?,
                    response: Response<RestNamirnicaResponse>?
                ) {
                    if (response?.isSuccessful == true) {
                        var postoji = false
                        val responseBody = response.body()
                        val namirnice = responseBody.results
                        for (namirnica in namirnice) {
                            if (namirnica.naziv == novaNamirnica.naziv) {
                                postoji = true

                                namirnica.kolicina_hladnjak = novaNamirnica.kolicina_hladnjak
                                namirnicaAdapter.dodajNamirnicu(namirnica)

                                break
                            }
                        }
                        if (postoji) {
                            novaNamirnica.kolicina_kupovina = -1f
                            azurirajNamirnicuUBazi(novaNamirnica)
                        } else {
                            dodajNamirnicuUBazu(novaNamirnica)
                            namirnicaAdapter.dodajNamirnicu(novaNamirnica)
                        }
                    }
                }

                override fun onFailure(call: Call<RestNamirnicaResponse>?, t: Throwable?) {
                    Toast.makeText(pogled.context, "Dodavanje neušpjesno", Toast.LENGTH_LONG).show()
                }

            }
        )
    }
}