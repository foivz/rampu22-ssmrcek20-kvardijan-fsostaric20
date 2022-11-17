package hr.foi.rampu.fridgium.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.entities.Recept

class ReceptAdapter(private val ReceptList: List<Recept>) : RecyclerView.Adapter<ReceptAdapter.ReceptViewHolder>(){
    inner class ReceptViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val nazivRecept:TextView
        private val opisRecept:TextView

        init {
            nazivRecept=view.findViewById(R.id.tv_naziv_recept)
            opisRecept=view.findViewById(R.id.tv_opis_recept)
        }

        fun bind(recept: Recept){
            nazivRecept.text = recept.naziv
            opisRecept.text = recept.opis
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceptViewHolder {
        val ReceptView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recept_list_item, parent, false)
        return ReceptViewHolder(ReceptView)
    }

    override fun onBindViewHolder(holder: ReceptViewHolder, position: Int) {
        holder.bind(ReceptList[position])
    }

    override fun getItemCount(): Int {
        return ReceptList.size
    }
}