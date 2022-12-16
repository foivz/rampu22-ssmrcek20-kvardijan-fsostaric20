package hr.foi.rampu.fridgium.helpers

import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.entities.MjernaJedinica
import hr.foi.rampu.fridgium.entities.Namirnica
import hr.foi.rampu.fridgium.rest.RestNamirnicaResponse
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
    val rest = RestNamirnice.namirnicaServis

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
            nazivNamirnice.text.toString(),
            kolicinaNamirnice.text.toString().toFloat(),
            odabranaMJ,
            0f
        )
    }

    fun dodajNamirnicuUBazu(namirnica: Namirnica) {
        rest.dodajNamirnicu(namirnica).enqueue(
            object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>?, response: Response<Boolean>?) {
                    if (response != null) {
                        Log.d("BAZA",response.message().toString())
                    }
                }

                override fun onFailure(call: Call<Boolean>?, t: Throwable?) {
                    Toast.makeText(pogled.context, "Dodavanje neuspjesno", Toast.LENGTH_LONG).show()
                }

            }
        )
    }

    fun azurirajNamirnicuUBazi(namirnica: Namirnica) {
        rest.azurirajNamirnicu(namirnica).enqueue(
            object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>?, response: Response<Boolean>?) {
                    if (response != null) {
                        Log.d("BAZA",response.message().toString())
                        //IZMJENI ENTRY ZA FAVORITA U LOCAL STORAGE
                        //KOLICINA FAVORITA CHECK
                    }
                }

                override fun onFailure(call: Call<Boolean>?, t: Throwable?) {
                    Toast.makeText(pogled.context, "Dodavanje neuspjesno", Toast.LENGTH_LONG).show()
                }

            }
        )
    }

    fun provjeriNamirnicu(novaNamirnica: Namirnica){
       rest.dohvatiNamirnice().enqueue(
           object : Callback<RestNamirnicaResponse>{
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
                               break
                           }
                       }
                       if(postoji){
                           novaNamirnica.kolicina_kupovina = -1f
                           azurirajNamirnicuUBazi(novaNamirnica)
                       } else{
                           dodajNamirnicuUBazu(novaNamirnica)
                       }
                   } else {
                       Toast.makeText(pogled.context, "Dodavanje neuspjesno", Toast.LENGTH_LONG).show()
                   }
               }

               override fun onFailure(call: Call<RestNamirnicaResponse>?, t: Throwable?) {
                   Toast.makeText(pogled.context, "Dodavanje neuspjesno", Toast.LENGTH_LONG).show()
               }

           }
       )
    }
}