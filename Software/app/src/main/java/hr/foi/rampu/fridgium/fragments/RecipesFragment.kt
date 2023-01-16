package hr.foi.rampu.fridgium.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.adapters.NoviReceptAdapter
import hr.foi.rampu.fridgium.adapters.ReceptAdapter
import hr.foi.rampu.fridgium.entities.MjernaJedinica
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
    private lateinit var recyclerViewNamirnice: RecyclerView
    private lateinit var gumbDodaj : FloatingActionButton
    private lateinit var imgbutton: ImageButton
    private lateinit var namirniceSpinenr : Spinner
    private val servis = RestRecept.ReceptService
    private val servisnr = RestNamirnicaRecepta.namirnicaReceptaServis
    private val servisn = RestNamirnice.namirnicaServis
    private var popisnamirnica: MutableList<NamirnicaPrikaz> = arrayListOf()
    private var popisrecepta: MutableList<Recept> = arrayListOf()
    private lateinit var popis : List<NamirnicaPrikaz>
    private lateinit var naziv : String
    private lateinit var opis : String


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
        namirniceSpinenr = view.findViewById(R.id.fragment_recept_spinner_namirnice)
        namirniceSpinenr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position == 0){
                    recyclerView.adapter = ReceptAdapter(popisrecepta, ::refreshDisplay)
                    return
                }
                val filtriranipopis : MutableList<Recept> = arrayListOf()
                for(r in popisrecepta){
                    for(n in r.namirnice){
                        if(n.id == popisnamirnica[position].id){
                            filtriranipopis.add(r)
                        }
                    }

                }
                recyclerView.adapter = ReceptAdapter(filtriranipopis, ::refreshDisplay)
                recyclerView.adapter!!.notifyDataSetChanged()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Code to be executed when nothing is selected
            }
        }
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
            refreshDisplay()
        }

        gumbDodaj.setOnClickListener{
            val dialogDodajRecept = LayoutInflater.from(view.context).inflate(R.layout.dodaj_novi_recept_dialog, null)
            val builder = AlertDialog.Builder(view.context).setView(dialogDodajRecept)
            var dialog = builder.create()
            builder.setPositiveButton("Spremi"){_,_->
                naziv = dialog.findViewById<EditText>(R.id.editText_naziv_dialog_novi_recept).text.toString().trim()
                opis = dialog.findViewById<EditText>(R.id.editText_opis_dialog_novi_recept).text.toString().trim()
                createNewRecept()
                refreshDisplay()
            }
            dialog = builder.create()
            dialog.show()
            recyclerViewNamirnice = dialog.findViewById(R.id.PV_recyclerview_namirnice_dialog_dodaj_recept)
            recyclerViewNamirnice.adapter = NoviReceptAdapter(popisnamirnica)
            recyclerViewNamirnice.layoutManager = LinearLayoutManager(dialog.context)
            imgbutton = dialog.findViewById(R.id.imgbtn_dialog_dodaj_recept)
            imgbutton.setOnClickListener{
                dialog.cancel()
            }

            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(ContextCompat.getColor(view.context, R.color.color_accent))

        }

        loadNamirnice()
    }

    private fun createNewRecept(){
        val adapter = recyclerViewNamirnice.adapter as NoviReceptAdapter
        popis = adapter.getItemsList()
        val recept = Recept(1000, naziv,opis, namirnice = arrayListOf())
        servis.postRecept(recept).enqueue(object :Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>?, response: Response<Boolean>?) {

                Toast.makeText(context,"Recept uspješno dodan",Toast.LENGTH_SHORT).show()
                servis.getReceptID(recept.naziv).enqueue(object  : Callback<Recept>{
                    override fun onResponse(call: Call<Recept>?, response: Response<Recept>?) {
                        addListNamirnica(popis, response!!.body().id)
                    }

                    override fun onFailure(call: Call<Recept>?, t: Throwable?) {

                    }

                })
            }

            override fun onFailure(call: Call<Boolean>?, t: Throwable?) {
                Toast.makeText(context,"Dogodila se greška pri dodavanju recepta",Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun addListNamirnica(namirnice : List<NamirnicaPrikaz>, receptID : Int) {
        for(n in namirnice){

            val objekt= tijelo(n.kolicina,receptID,n.id)
            Log.d("tijelo", objekt.toString())
            servisnr.postNamirniceRecepta(objekt).enqueue(object : Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>?, response: Response<Boolean>?) {

                }

                override fun onFailure(call: Call<Boolean>?, t: Throwable?) {

                }

            })
        }
    }

    private fun refreshDisplay(){
        refreshLayout.isRefreshing = false
        popisnamirnica = arrayListOf()
        popisrecepta = arrayListOf()
        loadNamirnice()
        recyclerView.adapter!!.notifyDataSetChanged()
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
                                            popisrecepta.add(r)

                                        }
                                        brojac++
                                        if (brojac == recept.size) {
                                            recyclerView.adapter = ReceptAdapter(popisrecepta, ::refreshDisplay)
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
                    populirajSpinner(popisnamirnica)
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

    fun populirajSpinner(popis : MutableList<NamirnicaPrikaz>){
        val default = NamirnicaPrikaz(0, "Ne filtriraj po namirnicama",0f,
            MjernaJedinica(0,"default"),0f,0f)
        popis.add(0, default)
        val spinnerAdapter = ArrayAdapter(
            requireView().context,
            android.R.layout.simple_spinner_item,
            popis.map { it.naziv }
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        namirniceSpinenr.adapter = spinnerAdapter
        namirniceSpinenr.setSelection(0)
    }

}