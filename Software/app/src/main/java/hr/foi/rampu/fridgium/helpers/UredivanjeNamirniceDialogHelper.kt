package hr.foi.rampu.fridgium.helpers

import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.entities.MjernaJedinica
import hr.foi.rampu.fridgium.entities.Namirnica
import hr.foi.rampu.fridgium.rest.RestMJedinica
import hr.foi.rampu.fridgium.rest.RestMJedinicaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UredivanjeNamirniceDialogHelper(view: View) {
    val pogled = view
    val nazivNamirnice = view.findViewById<EditText>(R.id.et_naziv_namirnice_uredi)
    val mjernaJedinicaSpinner = view.findViewById<Spinner>(R.id.spn_kategorija_namirnice_uredi)

    fun popuniNaziv(naziv: String){
        nazivNamirnice.setText(naziv)
    }

    fun popuniMJSpinner(mjerneJedinice: List<MjernaJedinica>, odabranaNamirnica: Namirnica){
        val spinnerAdapter = ArrayAdapter(
            pogled.context,
            android.R.layout.simple_spinner_item,
            mjerneJedinice
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mjernaJedinicaSpinner.adapter = spinnerAdapter
        var index = 0
        for (mj in mjerneJedinice){
            if (mj == odabranaNamirnica.mjernaJedinica){
                break
            }else{
                index++
            }
        }
        mjernaJedinicaSpinner.setSelection(index)
    }

    fun dohvatiMJ(odabranaNamirnica: Namirnica){
        val restMJ = RestMJedinica.mJedinicaServis

        restMJ.dohvatiMJedinice().enqueue(
            object : Callback<RestMJedinicaResponse> {
                override fun onResponse(
                    call: Call<RestMJedinicaResponse>?,
                    response: Response<RestMJedinicaResponse>?
                ) {
                    if (response != null) {
                        val responseBody = response.body()
                        val listaMjernihJedinica = responseBody.results

                        popuniMJSpinner(listaMjernihJedinica, odabranaNamirnica)
                    }
                }

                override fun onFailure(call: Call<RestMJedinicaResponse>?, t: Throwable?) {
                    Toast.makeText(pogled.context, "Neuspjesno dohvacanje mjernih jedinica", Toast.LENGTH_LONG).show()
                }

            }
        )
    }
}