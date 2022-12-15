package hr.foi.rampu.fridgium.adapters


import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.entities.NamirnicaPrikaz


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


}
