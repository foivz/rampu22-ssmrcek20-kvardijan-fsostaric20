package hr.foi.rampu.fridgium.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.entities.Namirnica
import org.w3c.dom.Text

class NamirnicaAdapter(private val namirnicaList: List<Namirnica>) : RecyclerView.Adapter<NamirnicaAdapter.NamirnicaViewHolder>() {

    inner class NamirnicaViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val imeNamirnice: TextView
        init {
            imeNamirnice = view.findViewById<TextView>(R.id.tv_ime_namirnice)
        }

        fun bind(namirnica: Namirnica){
            imeNamirnice.text = namirnica.naziv
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