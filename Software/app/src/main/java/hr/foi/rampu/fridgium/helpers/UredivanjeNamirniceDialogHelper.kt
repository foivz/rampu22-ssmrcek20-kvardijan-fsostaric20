package hr.foi.rampu.fridgium.helpers

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.*
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.entities.MjernaJedinica
import hr.foi.rampu.fridgium.entities.Namirnica
import hr.foi.rampu.fridgium.rest.RestMJedinica
import hr.foi.rampu.fridgium.rest.RestMJedinicaResponse
import hr.foi.rampu.fridgium.rest.RestNamirnice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UredivanjeNamirniceDialogHelper(view: View) {
    val pogled = view
    private val nazivNamirnice: EditText = view.findViewById(R.id.et_naziv_namirnice_uredi)
    private val mjernaJedinicaSpinner: Spinner =
        view.findViewById(R.id.spn_kategorija_namirnice_uredi)
    private val restMJ = RestMJedinica.mJedinicaServis
    val rest = RestNamirnice.namirnicaServis

    private val pomagacFavorita = FavoritiHelper(pogled)
    private val pomagacPrikaza = DisplayHelper()
    private val minKol = view.findViewById<EditText>(R.id.et_minimalna_kolicina_favorit)
    private val minKolLablela =
        view.findViewById<TextView>(R.id.et_minimalna_kolicina_favorit_tekst)

    fun popuniNaziv(naziv: String) {
        nazivNamirnice.setText(naziv)
    }

    fun azurirajPodatke(namirnica: Namirnica) {
        namirnica.mjernaJedinica = mjernaJedinicaSpinner.selectedItem as MjernaJedinica
        namirnica.naziv = nazivNamirnice.text.toString()
        namirnica.naziv = namirnica.naziv.lowercase().replaceFirstChar { it.uppercaseChar() }
        rest.azurirajNamirnicuNazivMJ(namirnica).enqueue(
            object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>?, response: Response<Boolean>?) {
                    if (response != null) {
                        Log.d("BAZA", response.message().toString())
                    }
                }

                override fun onFailure(call: Call<Boolean>?, t: Throwable?) {
                    Toast.makeText(pogled.context, "Neuspjesno ažuriranje", Toast.LENGTH_LONG)
                        .show()
                }

            }
        )
    }

    fun popuniMJSpinner(mjerneJedinice: List<MjernaJedinica>, namirnicaZaSpinner: Namirnica) {
        val spinnerAdapter = ArrayAdapter(
            pogled.context,
            android.R.layout.simple_spinner_item,
            mjerneJedinice
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mjernaJedinicaSpinner.adapter = spinnerAdapter
        var index = 0
        for (mj in mjerneJedinice) {
            if (mj == namirnicaZaSpinner.mjernaJedinica) {
                break
            } else {
                index++
            }
        }
        mjernaJedinicaSpinner.setSelection(index)
    }

    fun dohvatiMJ(namirnica: Namirnica) {
        restMJ.dohvatiMJedinice().enqueue(
            object : Callback<RestMJedinicaResponse> {
                override fun onResponse(
                    call: Call<RestMJedinicaResponse>?,
                    response: Response<RestMJedinicaResponse>?
                ) {
                    if (response != null) {
                        val responseBody = response.body()
                        val listaMjernihJedinica = responseBody.results

                        popuniMJSpinner(listaMjernihJedinica, namirnica)
                    }
                }

                override fun onFailure(call: Call<RestMJedinicaResponse>?, t: Throwable?) {
                    Toast.makeText(
                        pogled.context,
                        "Neuspješno dohvaćanje mjernih jedinica",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        )
    }

    fun dodajiliMakniFavorit(nazivNamirnice: String) {
        if (pomagacFavorita.provjeriFavorit(nazivNamirnice)) {
            pomagacFavorita.makniIzFavorita(nazivNamirnice)
        } else {
            pomagacFavorita.dodajUFavorite(nazivNamirnice, minKol.text.toString().toFloat())
        }
    }

    @SuppressLint("SetTextI18n")
    fun dodajiliMakniMinKolUpis(nazivNamirnice: String) {
        if (pomagacFavorita.provjeriFavorit(nazivNamirnice)) {
            val vrijednost = pomagacFavorita.dajVrijednostFavorita(nazivNamirnice)
            minKol.visibility = View.GONE
            if (pomagacPrikaza.provjeriBroj(vrijednost)) {
                val vrijednostInt = pomagacPrikaza.dajBroj(vrijednost)
                minKolLablela.text = "Minimalna količina u hladnjaku je trenutno $vrijednostInt"
            } else {
                minKolLablela.text = "Minimalna količina u hladnjaku je trenutno $vrijednost"
            }

        }
    }
}