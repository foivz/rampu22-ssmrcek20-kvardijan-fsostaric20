package hr.foi.rampu.fridgium.adapters


import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.entities.Namirnica
import hr.foi.rampu.fridgium.entities.NamirnicaPrikaz
import hr.foi.rampu.fridgium.rest.RestNamirnice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ReceptPrikaziViseAdapter(private val namirnice : List<NamirnicaPrikaz>) :
    RecyclerView.Adapter<ReceptPrikaziViseAdapter.ReceptPrikaziViseViewHolder>() {
    inner class ReceptPrikaziViseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var namirnicaRecept : TextView
        private var bojaDostupnost : SurfaceView
        init {
            Log.d("errorimidolaze", "Tu samerr")
            namirnicaRecept = view.findViewById(R.id.PV_Namirnica)
            bojaDostupnost = view.findViewById(R.id.SV_boja_prikazi_vise_item)


        }



        fun bind(namirnica: NamirnicaPrikaz) {
            var string = ""
            if(namirnica.kolicina>namirnica.kolicina_hladnjak){
                string += "${namirnica.naziv} \nKoličina u frižideru: ${namirnica.kolicina_hladnjak}${namirnica.mjernaJedinica.naziv} \nKoličina potrebna: ${namirnica.kolicina}${namirnica.mjernaJedinica.naziv}"
                bojaDostupnost.setBackgroundColor(Color.RED)
            }else{
                string += "${namirnica.naziv} \nKoličina u frižideru: ${namirnica.kolicina_hladnjak}${namirnica.mjernaJedinica.naziv
                } \nKoličina potrebna: ${namirnica.kolicina}${namirnica.mjernaJedinica.naziv
                }"
                bojaDostupnost.setBackgroundColor(Color.GREEN)
            }
            namirnicaRecept.text = string
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceptPrikaziViseViewHolder {
        val receptView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recept_prikazi_vise_item, parent, false)
        return ReceptPrikaziViseViewHolder(receptView)
    }

    override fun onBindViewHolder(holder: ReceptPrikaziViseViewHolder, position: Int) {
        holder.bind(namirnice[position])
    }

    override fun getItemCount(): Int {
        return namirnice.size
    }

    fun imaNamirnicaUHladnjaku() : Boolean {
        var ima = true
        for (namirnica in namirnice){
            if(namirnica.kolicina>namirnica.kolicina_hladnjak){
                ima = false
                break
            }
        }
        return ima
    }

    fun nabaviNamirnice(view: View) {
        val rest = RestNamirnice.namirnicaServis
        for(namirnica in namirnice){
            if(namirnica.kolicina>namirnica.kolicina_hladnjak){
                val kolicinaPrava = namirnica.kolicina_kupovina+(namirnica.kolicina-namirnica.kolicina_hladnjak)
                val namirnicaPrava = Namirnica(namirnica.id, namirnica.naziv, -1f, namirnica.mjernaJedinica, kolicinaPrava)
                rest.azurirajNamirnicu(namirnicaPrava).enqueue(
                    object : Callback<Boolean> {
                        override fun onResponse(call: Call<Boolean>?, response: Response<Boolean>?) {
                            if (response != null) {
                                Log.d("BAZA", response.message().toString())
                            }
                        }

                        override fun onFailure(call: Call<Boolean>?, t: Throwable?) {
                            Toast.makeText(
                                view.context,
                                "Došlo je do greške sa servisom!",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }
                )
            }
        }
        Toast.makeText(
            view.context,
            "Namirnice dodane u popis za kupnju",
            Toast.LENGTH_LONG
        ).show()
    }

    fun napraviRecept(view: View) {
        val rest = RestNamirnice.namirnicaServis
        for(namirnica in namirnice){
                val kolicinaPrava = namirnica.kolicina_hladnjak-namirnica.kolicina
                val namirnicaPrava = Namirnica(namirnica.id, namirnica.naziv, kolicinaPrava, namirnica.mjernaJedinica, -1f)
                rest.azurirajNamirnicu(namirnicaPrava).enqueue(
                    object : Callback<Boolean> {
                        override fun onResponse(call: Call<Boolean>?, response: Response<Boolean>?) {
                            if (response != null) {
                                Log.d("BAZA", response.message().toString())
                            }
                        }

                        override fun onFailure(call: Call<Boolean>?, t: Throwable?) {
                            Toast.makeText(
                                view.context,
                                "Došlo je do greške sa servisom!",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }
                )

        }
        Toast.makeText(
            view.context,
            "Namirnice maknute iz hladnjaka",
            Toast.LENGTH_LONG
        ).show()
    }


}
