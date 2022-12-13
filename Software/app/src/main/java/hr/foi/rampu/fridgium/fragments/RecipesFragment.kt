package hr.foi.rampu.fridgium.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.adapters.ReceptAdapter
import hr.foi.rampu.fridgium.entities.NamirnicaPrikaz
import hr.foi.rampu.fridgium.entities.Recept
import hr.foi.rampu.fridgium.rest.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val servis = RestRecept.ReceptService
    private val servisnr = RestNamirnicaRecepta.namirnicaReceptaServis
    private val servisn = RestNamirnice.namirnicaServis
    private var popisnamirnica : MutableList<NamirnicaPrikaz> = arrayListOf()
    private var popisrecepta : MutableList<Recept> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.rv_recepti)
        recyclerView.layoutManager= LinearLayoutManager(view.context)
        loadNamirnice()



    }



    private fun displayWebServiceErrorMessage() {
        Toast.makeText(
            context,
            getString(R.string.tekst_toast_recept_err),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun loadRecept(){
        var brojac = 0
        servis.getRecept().enqueue(
            object : Callback<RestReceptResponse>{
                override fun onResponse(call: Call<RestReceptResponse>?, response: Response<RestReceptResponse>?) {
                    if(response?.isSuccessful == true){
                        val responseBody = response.body()
                        val recept = responseBody.results
                        for(r in recept){
                            servisnr.getNamirniceRecepta(r.id).enqueue(object : Callback<RestNamirnicaReceptaResponse> {
                                override fun onResponse(
                                    call: Call<RestNamirnicaReceptaResponse>?,
                                    response: Response<RestNamirnicaReceptaResponse>?
                                ) {
                                    if(response?.isSuccessful == true){
                                        val responseBodyy = response.body()
                                        val popisnamirnice = responseBodyy.results
                                        r.namirnice = mutableListOf()
                                        for(pn in popisnamirnice){

                                            for(n in popisnamirnica){
                                                if( pn.namirnica_id == n.id ){
                                                    n.kolicina = pn.kolicina

                                                    r.namirnice.add(n)

                                                }

                                            }
                                        }
                                        Log.d("async", "Namirnice u receptu" +r.namirnice.toString() )

                                        popisrecepta.add(r)

                                    }
                                    brojac++
                                    if(brojac==recept.size){
                                        Log.d("async", "ZAVRSIL2")
                                        recyclerView.adapter = ReceptAdapter(popisrecepta)
                                    }
                                }

                                override fun onFailure(
                                    call: Call<RestNamirnicaReceptaResponse>?,
                                    t: Throwable?
                                ) {
                                    displayWebServiceErrorMessage()
                                }
                            })

                                Log.d("async", "ZAVRSIL")
                                Log.d("async", popisnamirnica.toString())




                        }

                    } else {
                        displayWebServiceErrorMessage()
                    }
                }

                override fun onFailure(call: Call<RestReceptResponse>?, t: Throwable?) {
                    displayWebServiceErrorMessage()
                }
            }

        )
    }

    private fun loadNamirnice(){
        servisn.dohvatiNamirnice().enqueue(object : Callback<RestNamirnicaResponse> {
            override fun onResponse(
                call: Call<RestNamirnicaResponse>?,
                response: Response<RestNamirnicaResponse>?
            ) {
                if(response?.isSuccessful == true){
                    val responseBody = response.body()
                    val namirnice = responseBody.results
                    for(n in namirnice){
                        val np = NamirnicaPrikaz(n.id,n.naziv,n.kolicina_hladnjak,n.mjernaJedinica,n.kolicina_kupovina,0)

                        popisnamirnica.add(np)
                    }
                    loadRecept()
                }
            }

            override fun onFailure(
                call: Call<RestNamirnicaResponse>?,
                t: Throwable?
            ) {
                displayWebServiceErrorMessage()
            }
        })
    }



}