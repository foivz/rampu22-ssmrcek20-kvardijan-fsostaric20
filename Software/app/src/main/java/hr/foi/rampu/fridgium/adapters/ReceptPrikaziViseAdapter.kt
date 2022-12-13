package hr.foi.rampu.fridgium.adapters


import android.graphics.Color.GREEN
import android.hardware.camera2.params.RggbChannelVector.RED
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.entities.NamirnicaPrikaz


class ReceptPrikaziViseAdapter(private val namirnice : List<NamirnicaPrikaz>) :
    RecyclerView.Adapter<ReceptPrikaziViseAdapter.ReceptPrikaziViseViewHolder>() {
        inner class ReceptPrikaziViseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            private val naslovRecept: TextView
            private val opisRecept: TextView
            private var namirnicaRecept : TextView
            init {
                naslovRecept = view.findViewById(R.id.tvIme_Recepta)
                opisRecept = view.findViewById(R.id.tv_opis_recepta_prikazi_vise)
                namirnicaRecept = view.findViewById(R.id.PV_Namirnica)
            }

            fun bind(namirnica: NamirnicaPrikaz) {

                if(namirnica.kolicina>namirnica.kolicina_hladnjak){
                    namirnicaRecept.text = namirnica.naziv
                    namirnicaRecept.highlightColor = RED
                }else{
                    namirnicaRecept.text = namirnica.naziv
                    namirnicaRecept.highlightColor = GREEN
                }

            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceptPrikaziViseViewHolder {
            val receptView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.recept_prikazi_vise_item, parent, false)
            return ReceptPrikaziViseViewHolder(receptView)
        }

        override fun getItemCount(): Int {
            return namirnice.size
        }

        override fun onBindViewHolder(holder: ReceptPrikaziViseViewHolder, position: Int) {
            holder.bind(namirnice[position])
        }

}