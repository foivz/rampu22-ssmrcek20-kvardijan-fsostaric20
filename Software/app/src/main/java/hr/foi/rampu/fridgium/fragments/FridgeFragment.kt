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
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.adapters.NamirnicaAdapter
import hr.foi.rampu.fridgium.helpers.MockDataLoader
import hr.foi.rampu.fridgium.rest.RestNamirnicaResponse
import hr.foi.rampu.fridgium.rest.RestNamirnice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FridgeFragment : Fragment() {

    private val probneNamirnice = MockDataLoader.DajProbnePodatke()
    private lateinit var recyclerView: RecyclerView
    private lateinit var hladnjakLoading: ProgressBar
    private val rest = RestNamirnice.namirnicaServis

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        probneNamirnice.forEach { Log.i("PROBNA NAMIRNICA", it.naziv) }
        return inflater.inflate(R.layout.fragment_fridge, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        hladnjakLoading = view.findViewById(R.id.hladnjak_loading)

        recyclerView = view.findViewById(R.id.rv_namirnice_hladnjaka)
        //recyclerView.adapter = NamirnicaAdapter(MockDataLoader.DajProbnePodatke())
        //promjeniZaslon(false)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        ucitajSadrzajHladnjaka()
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
                        recyclerView.adapter = NamirnicaAdapter(namirnice)

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

    private fun promjeniZaslon(ucitavanje: Boolean){
        recyclerView.isVisible = !ucitavanje
        hladnjakLoading.isVisible = ucitavanje
    }
}

