package hr.foi.rampu.fridgium.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.adapters.ReceptAdapter
import hr.foi.rampu.fridgium.rest.RestRecept
import hr.foi.rampu.fridgium.rest.RestReceptResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val servis = RestRecept.ReceptService


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.rv_recepti)
        recyclerView.layoutManager= LinearLayoutManager(view.context)
        loadRecept()

    }

    private fun displayWebServiceErrorMessage() {
        Toast.makeText(
            context,
            getString(R.string.tekst_toast_recept_err),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun loadRecept(){

        servis.getRecept().enqueue(
            object : Callback<RestReceptResponse>{
                override fun onResponse(call: Call<RestReceptResponse>?, response: Response<RestReceptResponse>?) {
                    if(response?.isSuccessful == true){
                        val responseBody = response.body()
                        val recept = responseBody.results
                        recyclerView.adapter = ReceptAdapter(recept)
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

}