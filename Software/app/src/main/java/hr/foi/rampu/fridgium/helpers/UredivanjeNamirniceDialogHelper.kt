package hr.foi.rampu.fridgium.helpers

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
    val nazivNamirnice = view.findViewById<EditText>(R.id.et_naziv_namirnice_uredi)
    val mjernaJedinicaSpinner = view.findViewById<Spinner>(R.id.spn_kategorija_namirnice_uredi)
    val restMJ = RestMJedinica.mJedinicaServis
    val rest = RestNamirnice.namirnicaServis

    val pomagacFavorita = FavoritiHelper(pogled)
    val minKol = view.findViewById<EditText>(R.id.et_minimalna_kolicina_favorit)
    val minKolLablela = view.findViewById<TextView>(R.id.et_minimalna_kolicina_favorit_tekst)
    val favoritiNaslov = view.findViewById<TextView>(R.id.tv_postavke_favorita)

    fun popuniNaziv(naziv: String){
        nazivNamirnice.setText(naziv)
    }

    fun azurirajPodatke(namirnica: Namirnica){
        val azurnaNamirnica = namirnica
        azurnaNamirnica.mjernaJedinica = mjernaJedinicaSpinner.selectedItem as MjernaJedinica
        azurnaNamirnica.naziv = nazivNamirnice.text.toString()
        rest.azurirajNamirnicuNazivMJ(azurnaNamirnica).enqueue(
            object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>?, response: Response<Boolean>?) {
                    if (response != null) {
                        Log.d("BAZA", response.message().toString())
                    }
                }

                override fun onFailure(call: Call<Boolean>?, t: Throwable?) {
                    Toast.makeText(pogled.context, "Neuspjesno azuriranje", Toast.LENGTH_LONG).show()
                }

            }
        )
    }

    fun popuniMJSpinner(mjerneJedinice: List<MjernaJedinica>, namirnicaZaSpinner: Namirnica){
        val spinnerAdapter = ArrayAdapter(
            pogled.context,
            android.R.layout.simple_spinner_item,
            mjerneJedinice
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mjernaJedinicaSpinner.adapter = spinnerAdapter
        var index = 0
        for (mj in mjerneJedinice){
            if (mj == namirnicaZaSpinner.mjernaJedinica){
                break
            }else{
                index++
            }
        }
        mjernaJedinicaSpinner.setSelection(index)
    }

    fun dohvatiMJ(namirnica: Namirnica){
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
                    Toast.makeText(pogled.context, "Neuspjesno dohvacanje mjernih jedinica", Toast.LENGTH_LONG).show()
                }

            }
        )
    }

    fun DodajiliMakniFavorit(nazivNamirnice: String){
        if (pomagacFavorita.ProvjeriFavorit(nazivNamirnice)){
            pomagacFavorita.MakniIzFavorita(nazivNamirnice)
        }else{
            pomagacFavorita.DodajUFavorite(nazivNamirnice, minKol.text.toString().toFloat())
        }
    }

    fun DodajiliMakniMinKolUpis(nazivNamirnice: String){
        if (pomagacFavorita.ProvjeriFavorit(nazivNamirnice)){
            val vrijednost = pomagacFavorita.DajVrijednostFavorita(nazivNamirnice).toString()
            minKol.visibility = View.GONE
            minKolLablela.setText("Minimalna količina u hladnjaku je trenutno $vrijednost")
        }
    }
}