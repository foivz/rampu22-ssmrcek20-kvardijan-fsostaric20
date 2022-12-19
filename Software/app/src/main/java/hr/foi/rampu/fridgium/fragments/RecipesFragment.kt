package hr.foi.rampu.fridgium.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.adapters.ReceptAdapter
import hr.foi.rampu.fridgium.entities.NamirnicaPrikaz
import hr.foi.rampu.fridgium.entities.Recept
import hr.foi.rampu.fridgium.rest.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipesFragment : Fragment() {
    private lateinit var refreshLayout : SwipeRefreshLayout
    private lateinit var loading: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var gumbDodaj : FloatingActionButton
    private val servis = RestRecept.ReceptService
    private val servisnr = RestNamirnicaRecepta.namirnicaReceptaServis
    private val servisn = RestNamirnice.namirnicaServis
    private var popisnamirnica: MutableList<NamirnicaPrikaz> = arrayListOf()
    private var popisrecepta: MutableList<Recept> = arrayListOf()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.rv_recepti)
        refreshLayout = view.findViewById(R.id.fragment_recept_swiperefresh)
        loading = view.findViewById(R.id.fragment_recept_loading)
        gumbDodaj = view.findViewById(R.id.fragment_recept_dodaj_recept)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && recyclerView.canScrollVertically(-1)) {
                    gumbDodaj.hide()
                }
                else gumbDodaj.show()
            }
        })
        refreshLayout.setOnRefreshListener {
            refreshLayout.isRefreshing = false
            popisnamirnica = arrayListOf()
            popisrecepta = arrayListOf()
            loadNamirnice()
            recyclerView.adapter!!.notifyDataSetChanged()
        }

        gumbDodaj.setOnClickListener{

        }

        loadNamirnice()
    }



    private fun displayWebServiceErrorMessage() {
        Toast.makeText(
            context,
            getString(R.string.tekst_toast_recept_err),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun loadRecept() {
        var brojac = 0
        servis.getRecept().enqueue(
            object : Callback<RestReceptResponse> {
                override fun onResponse(
                    call: Call<RestReceptResponse>?,
                    response: Response<RestReceptResponse>?
                ) {
                    if (response?.isSuccessful == true) {
                        val responseBody = response.body()
                        val recept = responseBody.results
                        for (r in recept) {
                            servisnr.getNamirniceRecepta(r.id)
                                .enqueue(object : Callback<RestNamirnicaReceptaResponse> {
                                    override fun onResponse(
                                        call: Call<RestNamirnicaReceptaResponse>?,
                                        response: Response<RestNamirnicaReceptaResponse>?
                                    ) {
                                        if (response?.isSuccessful == true) {
                                            val responseBodyy = response.body()
                                            val popisnamirnice = responseBodyy.results
                                            r.namirnice = mutableListOf()
                                            for (pn in popisnamirnice) {

                                                for (n in popisnamirnica) {
                                                    if (pn.namirnica_id == n.id) {
                                                        val namirnica = NamirnicaPrikaz(
                                                            n.id,
                                                            n.naziv,
                                                            n.kolicina_hladnjak,
                                                            n.mjernaJedinica,
                                                            n.kolicina_kupovina,
                                                            pn.kolicina
                                                        )


                                                        r.namirnice.add(namirnica)

                                                    }

                                                }
                                            }
                                            Log.d(
                                                "async",
                                                "Namirnice u receptu" + r.namirnice.toString()
                                            )

                                            popisrecepta.add(r)

                                        }
                                        brojac++
                                        if (brojac == recept.size) {
                                            Log.d("async", "ZAVRSIL2")
                                            recyclerView.adapter = ReceptAdapter(popisrecepta)
                                            prikaziLoading(true)
                                        }
                                    }

                                    override fun onFailure(
                                        call: Call<RestNamirnicaReceptaResponse>?,
                                        t: Throwable?
                                    ) {
                                        displayWebServiceErrorMessage()
                                        prikaziLoading(true)
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

    private fun loadNamirnice() {
        prikaziLoading(false)
        servisn.dohvatiNamirnice().enqueue(object : Callback<RestNamirnicaResponse> {
            override fun onResponse(
                call: Call<RestNamirnicaResponse>?,
                response: Response<RestNamirnicaResponse>?
            ) {
                if (response?.isSuccessful == true) {
                    val responseBody = response.body()
                    val namirnice = responseBody.results
                    for (n in namirnice) {
                        val np = NamirnicaPrikaz(
                            n.id,
                            n.naziv,
                            n.kolicina_hladnjak,
                            n.mjernaJedinica,
                            n.kolicina_kupovina,
                            0f
                        )

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

    fun prikaziLoading(bool : Boolean){
        loading.isVisible = !bool
    }

}