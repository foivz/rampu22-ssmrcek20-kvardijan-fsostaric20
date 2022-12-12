package hr.foi.rampu.fridgium.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.adapters.NamirnicaAdapter
import hr.foi.rampu.fridgium.entities.Namirnica
import hr.foi.rampu.fridgium.helpers.DodavanjeNamirniceHladnjakHelper
import hr.foi.rampu.fridgium.helpers.MockDataLoader
import hr.foi.rampu.fridgium.rest.RestMJedinica
import hr.foi.rampu.fridgium.rest.RestMJedinicaResponse
import hr.foi.rampu.fridgium.rest.RestNamirnicaResponse
import hr.foi.rampu.fridgium.rest.RestNamirnice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class FridgeFragment : Fragment() {

    private val probneNamirnice = MockDataLoader.DajProbnePodatke()
    private lateinit var recyclerView: RecyclerView
    private lateinit var hladnjakLoading: ProgressBar
    private lateinit var hladnjakPrazanTekst: TextView
    private lateinit var dodajNamirnicuUFrizider: FloatingActionButton
    private val rest = RestNamirnice.namirnicaServis
    private lateinit var osvjezi: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        probneNamirnice.forEach { Log.i("PROBNA NAMIRNICA", it.naziv) }
        return inflater.inflate(R.layout.fragment_fridge, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        hladnjakLoading = view.findViewById(R.id.hladnjak_loading)
        hladnjakPrazanTekst = view.findViewById(R.id.tv_hladnjak_prazan)
        recyclerView = view.findViewById(R.id.rv_namirnice_hladnjaka)
        dodajNamirnicuUFrizider = view.findViewById(R.id.fab_dodaj_namirnicu_u_hladnjak)
        osvjezi = view.findViewById(R.id.srl_hladnjak)
        //recyclerView.adapter = NamirnicaAdapter(MockDataLoader.DajProbnePodatke())
        //promjeniZaslon(false)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        ucitajSadrzajHladnjaka()

        dodajNamirnicuUFrizider.setOnClickListener{
            prikaziDialogDodavanjaNamirnice()
        }

        osvjezi.setOnRefreshListener {
            ucitajSadrzajHladnjaka()
            osvjezi.isRefreshing = false
        }
    }

    private fun ucitajSadrzajHladnjaka(){
        promjeniZaslon(true)

        rest.dohvatiNamirnice().enqueue(
            object : Callback<RestNamirnicaResponse>{
                override fun onResponse(
                    call: Call<RestNamirnicaResponse>?,
                    response: Response<RestNamirnicaResponse>?
                ) {
                    if (response?.isSuccessful == true){
                        val responseBody = response.body()
                        val namirnice = responseBody.results
                        val namirniceHladnjaka = mutableListOf<Namirnica>()

                        for (namirnica in namirnice){
                            if (namirnica.kolicina_hladnjak > 0){
                                namirniceHladnjaka.add(namirnica)
                            }
                        }

                        if (namirniceHladnjaka.isEmpty()){
                            recyclerView.visibility = View.INVISIBLE
                            hladnjakPrazanTekst.visibility = View.VISIBLE
                        }else{
                            recyclerView.visibility = View.VISIBLE
                            hladnjakPrazanTekst.visibility = View.INVISIBLE

                            recyclerView.adapter = NamirnicaAdapter(namirniceHladnjaka)
                        }
                        hladnjakLoading.isVisible = false
                    }else{
                        pokaziPorukuGreske()
                    }
                    promjeniZaslon(false)
                }

                override fun onFailure(call: Call<RestNamirnicaResponse>?, t: Throwable?) {
                    pokaziPorukuGreske()
                    promjeniZaslon(false)
                }
            }
        )
    }

    private fun pokaziPorukuGreske(){
        Toast.makeText(
            context,
            getString(R.string.poruka_greske_hladnjak),
            Toast.LENGTH_LONG).show()
    }

    private fun pokaziPorukuGreskeSpinneraMjernihJedinica(){
        Toast.makeText(
            context,
            getString(R.string.poruka_greske_spinner_mj),
            Toast.LENGTH_LONG).show()
    }

    private fun promjeniZaslon(ucitavanje: Boolean){
        recyclerView.isVisible = !ucitavanje
        hladnjakLoading.isVisible = ucitavanje
    }

    private fun prikaziDialogDodavanjaNamirnice(){
        val restMJ = RestMJedinica.mJedinicaServis

        val dodajNamirnicuDialog = LayoutInflater
            .from(context)
            .inflate(R.layout.dodaj_namirnicu_u_frizider_dialog, null)

        val pomagacDodavanjaNamirnica = DodavanjeNamirniceHladnjakHelper(dodajNamirnicuDialog)

        AlertDialog.Builder(context)
            .setView(dodajNamirnicuDialog)
            .setTitle("Dodavanje namirnice")
            .setPositiveButton("Dodaj namirnicu"){ _, _ ->
                val novaNamirnica = pomagacDodavanjaNamirnica.izgradiObjektNoveNamirnice()
                pomagacDodavanjaNamirnica.provjeriNamirnicu(novaNamirnica)
            }
            .show()

        restMJ.dohvatiMJedinice().enqueue(
            object : Callback<RestMJedinicaResponse>{
                override fun onResponse(
                    call: Call<RestMJedinicaResponse>?,
                    response: Response<RestMJedinicaResponse>?
                ) {
                    if (response != null) {
                        val responseBody = response.body()
                        val listaMjernihJedinica = responseBody.results

                        pomagacDodavanjaNamirnica.popuniSpinner(listaMjernihJedinica)
                    }
                }

                override fun onFailure(call: Call<RestMJedinicaResponse>?, t: Throwable?) {
                    pokaziPorukuGreskeSpinneraMjernihJedinica()
                }

            }
        )
    }
}

