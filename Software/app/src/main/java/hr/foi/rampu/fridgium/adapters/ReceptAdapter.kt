package hr.foi.rampu.fridgium.adapters

import android.app.AlertDialog
import android.graphics.Color
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.entities.Recept


class ReceptAdapter(private val ReceptList: List<Recept>) :
    RecyclerView.Adapter<ReceptAdapter.ReceptViewHolder>() {
    inner class ReceptViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nazivRecept: TextView
        private val opisRecept: TextView
        private val namirniceRecept: TextView
        private val bojaRecept: SurfaceView
        private val btnPrikaziVise:Button


        init {
            nazivRecept = view.findViewById(R.id.tv_naziv_recept)
            opisRecept = view.findViewById(R.id.tv_opis_recept)
            namirniceRecept = view.findViewById(R.id.tv_namirnice_recepta)
            bojaRecept = view.findViewById(R.id.SV_bojadostupno)
            btnPrikaziVise = view.findViewById(R.id.btn_prikazi_vise)
            btnPrikaziVise.setOnClickListener{
                val recept : Recept = ReceptList[this.adapterPosition]
                val prikaziVise = LayoutInflater.from(view.context).inflate(R.layout.fragment_prikazi_vise_recept, null)
                val recyclerView = view.findViewById<RecyclerView>(R.id.PV_recyclerview_namirnice)
                val dialog = AlertDialog.Builder(view.context).setView(prikaziVise).show()
                val opisReceptPV = dialog.findViewById<TextView>(R.id.tv_opis_recepta_prikazi_vise)
                val nazivReceptPV = dialog.findViewById<TextView>(R.id.tvIme_Recepta)
                nazivReceptPV.text = recept.naziv
                opisReceptPV.text = recept.opis
                recyclerView.adapter = ReceptPrikaziViseAdapter(recept.namirnice)

            }
        }

        fun bind(recept: Recept) {
            var string = ""
            nazivRecept.text = recept.naziv
            opisRecept.text = recept.opis
            for (n in recept.namirnice) {
                string += if (n.kolicina_hladnjak < n.kolicina) {
                    bojaRecept.setBackgroundColor(Color.RED)
                    n.naziv + " " + n.kolicina + n.mjernaJedinica.naziv + "\n"
                } else {
                    bojaRecept.setBackgroundColor(Color.GREEN)
                    n.naziv + " " + n.kolicina + n.mjernaJedinica.naziv + "\n"
                }
            }

            namirniceRecept.text = string


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceptViewHolder {
        val receptView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recept_list_item, parent, false)
        return ReceptViewHolder(receptView)
    }

    override fun onBindViewHolder(holder: ReceptViewHolder, position: Int) {
        holder.bind(ReceptList[position])
    }

    override fun getItemCount(): Int {
        return ReceptList.size
    }

}