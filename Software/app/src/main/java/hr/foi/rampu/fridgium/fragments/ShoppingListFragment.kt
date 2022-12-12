package hr.foi.rampu.fridgium.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.adapters.ShoppingListaAdapter
import hr.foi.rampu.fridgium.entities.Namirnica
import hr.foi.rampu.fridgium.helpers.NovaNamirnicaListaZaKupovinuHelper
import hr.foi.rampu.fridgium.rest.RestNamirnicaResponse
import hr.foi.rampu.fridgium.rest.RestNamirnice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ShoppingListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyTextView: TextView
    private lateinit var emptyImageView: ImageView
    private lateinit var loadingCircle: ProgressBar
    private lateinit var btnCreateNamirnica: FloatingActionButton
    private lateinit var refresh: SwipeRefreshLayout
    private val rest = RestNamirnice.namirnicaServis


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shopping_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        refresh = view.findViewById(R.id.swr_shopping_list)
        loadingCircle = view.findViewById(R.id.pb_shopping_list_loading)
        recyclerView = view.findViewById(R.id.rv_shopping_list)
        emptyTextView = view.findViewById(R.id.empty_text_view)
        emptyImageView = view.findViewById(R.id.empty_image_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        loadNamirnice()
        btnCreateNamirnica = view.findViewById(R.id.btn_dodaj_nove_namirnice_u_listu_za_kupovinu)
        btnCreateNamirnica.setOnClickListener {
            showDialog(view)
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && recyclerView.canScrollVertically(-1)) {
                    btnCreateNamirnica.hide()
                }
                else btnCreateNamirnica.show()
            }
        })

        refresh.setOnRefreshListener {
            loadNamirnice()
            refresh.isRefreshing = false
        }
    }

    private fun showDialog(view: View) {
        val novaNamirnicaListaZaKupovinuHelper = LayoutInflater.from(context).inflate(R.layout.forma_nova_namirnica_za_listu_namirnica,null)
        val helper = NovaNamirnicaListaZaKupovinuHelper(novaNamirnicaListaZaKupovinuHelper)

        val dialog: AlertDialog = AlertDialog.Builder(context)
            .setView(novaNamirnicaListaZaKupovinuHelper)
            .setTitle(getString(R.string.nova_namirnica_lista_za_kupovinu))
            .setPositiveButton(getString(R.string.dodaj)) { _, _ ->
                val shoppingAdapter = (recyclerView.adapter as ShoppingListaAdapter)
                if(shoppingAdapter.itemCount == 0){
                    loadingCircle.visibility = View.INVISIBLE
                    recyclerView.visibility = View.VISIBLE
                    emptyTextView.visibility = View.INVISIBLE
                    emptyImageView.visibility = View.INVISIBLE
                }
                val novaNamirnica = helper.napraviNamirnicu()
                helper.pretraziNamirnice(novaNamirnica,shoppingAdapter)
            }
            .show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(view.context,R.color.color_accent))
        helper.napuniSpinner()
    }

    private fun loadNamirnice() {
        recyclerView.visibility = View.INVISIBLE
        emptyTextView.visibility = View.INVISIBLE
        emptyImageView.visibility = View.INVISIBLE
        loadingCircle.visibility = View.VISIBLE

        rest.dohvatiNamirnice().enqueue(
            object : Callback<RestNamirnicaResponse> {
                override fun onResponse(call: Call<RestNamirnicaResponse>?, response: Response<RestNamirnicaResponse>?) {
                    if (response?.isSuccessful == true) {
                        val responseBody = response.body()
                        val namirnice = responseBody.results

                        val namirnicePrave = arrayListOf<Namirnica>()
                        for(namirnica in namirnice){
                            if(namirnica.kolicina_kupovina > 0){
                                namirnicePrave.add(namirnica)
                            }
                        }

                        if(namirnicePrave.isEmpty()){
                            recyclerView.visibility = View.INVISIBLE
                            emptyTextView.visibility = View.VISIBLE
                            emptyImageView.visibility = View.VISIBLE
                            loadingCircle.visibility = View.INVISIBLE
                        }
                        else{
                            loadingCircle.visibility = View.INVISIBLE
                            recyclerView.visibility = View.VISIBLE
                            emptyTextView.visibility = View.INVISIBLE
                            emptyImageView.visibility = View.INVISIBLE
                        }
                        recyclerView.adapter = ShoppingListaAdapter(namirnicePrave)
                        recyclerView.layoutManager = LinearLayoutManager(view!!.context)
                        val divider = MaterialDividerItemDecoration(view!!.context,LinearLayoutManager.VERTICAL)
                        divider.dividerInsetStart = 20
                        divider.dividerInsetEnd = 20
                        divider.isLastItemDecorated = false
                        recyclerView.addItemDecoration(divider)

                    } else {
                        displayRestServiceErrorMessage()
                        loadingCircle.isVisible = false
                    }
                }

                override fun onFailure(call: Call<RestNamirnicaResponse>?, t: Throwable?) {
                    displayRestServiceErrorMessage()
                    loadingCircle.isVisible = false
                }
            }
        )
    }

    private fun displayRestServiceErrorMessage() {
        Toast.makeText(
            context,
            "Došlo je do greške",
            Toast.LENGTH_LONG
        ).show()
    }
}