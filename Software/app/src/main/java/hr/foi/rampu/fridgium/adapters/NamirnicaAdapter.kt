package hr.foi.rampu.fridgium.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.entities.Namirnica

class NamirnicaAdapter(private val namirnicaList: List<Namirnica>) :
    RecyclerView.Adapter<NamirnicaAdapter.NamirnicaViewHolder>() {

    inner class NamirnicaViewHolder(view: View): RecyclerView.ViewHolder(view){
        protected val pogled = view
        private val imeNamirnice: TextView
        private val ikonicaNamirnice: ImageView
        init {
            ikonicaNamirnice = view.findViewById(R.id.img_ikona_namirnice_hladnjak)
            imeNamirnice = view.findViewById(R.id.tv_ime_namirnice)
        }

        fun bind(namirnica: Namirnica){
            imeNamirnice.text = namirnica.naziv
            val draw = ContextCompat.getDrawable(pogled.context,OdaberiIkonicu(namirnica.naziv))
            ikonicaNamirnice.setImageDrawable(draw)
        }

        fun OdaberiIkonicu(naziv: String): Int {
            return when(naziv){
                "Jaje" -> R.drawable.egg_svgrepo_com
                "Maslac" -> R.drawable.butter_svgrepo_com
                "Mlijeko" -> R.drawable.milk_svgrepo_com
                else -> R.drawable.ic_baseline_dining_24
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NamirnicaViewHolder {
        val namirnicaView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.hladnjak_lista_namirnica_item, parent, false)
        return NamirnicaViewHolder(namirnicaView)
    }

    override fun onBindViewHolder(holder: NamirnicaViewHolder, position: Int) {
        holder.bind(namirnicaList[position])
    }

    override fun getItemCount(): Int {
        return namirnicaList.size
    }

}