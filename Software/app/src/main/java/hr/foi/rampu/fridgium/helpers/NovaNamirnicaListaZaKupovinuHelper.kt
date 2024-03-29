package hr.foi.rampu.fridgium.helpers

import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.adapters.ShoppingListaAdapter
import hr.foi.rampu.fridgium.entities.*
import hr.foi.rampu.fridgium.rest.RestMJedinica
import hr.foi.rampu.fridgium.rest.RestMJedinicaResponse
import hr.foi.rampu.fridgium.rest.RestNamirnicaResponse
import hr.foi.rampu.fridgium.rest.RestNamirnice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NovaNamirnicaListaZaKupovinuHelper(private val view: View) {

    private val spinNamirnica = view.findViewById<Spinner>(R.id.nova_lista_za_kupovinu_mjr_spn)
    private val naziv = view.findViewById<EditText>(R.id.nova_lista_za_kupovinu_naziv2)
    private val kolicina = view.findViewById<EditText>(R.id.nova_lista_za_kupovinu_kolicina2)
    private val restMJedinica = RestMJedinica.mJedinicaServis
    private val rest = RestNamirnice.namirnicaServis

    fun napuniSpinner() {
        restMJedinica.dohvatiMJedinice().enqueue(
            object : Callback<RestMJedinicaResponse> {
                override fun onResponse(
                    call: Call<RestMJedinicaResponse>?,
                    response: Response<RestMJedinicaResponse>?
                ) {
                    if (response?.isSuccessful == true) {
                        val responseBody = response.body()
                        val mJedinice = responseBody.results
                        val spinnerAdapter = ArrayAdapter(
                            view.context,
                            android.R.layout.simple_spinner_item,
                            mJedinice
                        )
                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinNamirnica.adapter = spinnerAdapter
                    } else {
                        prikazGreskeSaServerom()
                    }
                }

                override fun onFailure(call: Call<RestMJedinicaResponse>?, t: Throwable?) {
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

    private fun prikazGreskeUpisa() {
        Toast.makeText(
            view.context,
            "Upišite sve podatke!",
            Toast.LENGTH_LONG
        ).show()
    }

    fun provjeriNamirnicu() : Boolean{
        if (naziv.text.isEmpty() || kolicina.text.isEmpty()) {
            prikazGreskeUpisa()
            return false
        } else return true
    }

    fun napraviNamirnicu(): Namirnica {

        val namirnicaNaziv = naziv.text.toString().lowercase().replaceFirstChar { it.uppercaseChar() }
        val namirnicaKolicina = kolicina.text.toString().toFloat();
        val odabranaJedinica = spinNamirnica.selectedItem as MjernaJedinica

        return Namirnica(0, namirnicaNaziv, 0f, odabranaJedinica, namirnicaKolicina)

    }

    fun pretraziNamirnice(novaNamirnica: Namirnica, shoppingAdapter: ShoppingListaAdapter) {
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
                                namirnica.kolicina_kupovina = novaNamirnica.kolicina_kupovina
                                shoppingAdapter.dodajNamirnicu(namirnica)
                                break;
                            }
                        }
                        if (postoji) {
                            novaNamirnica.kolicina_hladnjak = -1f
                            AzurirajUbazi(novaNamirnica)
                            novaNamirnica.kolicina_hladnjak = 0f
                        } else {
                            DodajUBazu(novaNamirnica)
                            shoppingAdapter.dodajNamirnicu(novaNamirnica)
                        }

                    } else {
                        prikazGreskeSaServerom()
                    }
                }

                override fun onFailure(call: Call<RestNamirnicaResponse>?, t: Throwable?) {
                    prikazGreskeSaServerom()
                }
            }
        )
    }

    fun DodajUBazu(namirnica: Namirnica) {
        rest.dodajNamirnicu(namirnica).enqueue(
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

    fun IzbrisiUbazi(namirnica: Namirnica) {
        val novaNamirnica = namirnica.naziv
        rest.izbrisiNamirnicu(novaNamirnica).enqueue(
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
}