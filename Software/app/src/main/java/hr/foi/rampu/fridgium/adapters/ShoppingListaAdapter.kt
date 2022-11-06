package hr.foi.rampu.fridgium.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.entities.Namirnica

class ShoppingListaAdapter(private val shoppingList: List<Namirnica>) : RecyclerView.Adapter<ShoppingListaAdapter.ShoppingListViewHolder>() {
    inner class ShoppingListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val namirnicaNaziv: TextView
        private val namirnicaKolicina: TextView
        private val namirnicaDelete: ImageView
        private val namirnicaFridge: ImageView

        init {
            namirnicaNaziv = view.findViewById(R.id.tv_naziv)
            namirnicaKolicina = view.findViewById(R.id.tv_kolicina)
            namirnicaDelete = view.findViewById(R.id.img_delete)
            namirnicaFridge = view.findViewById(R.id.img_fridge)
        }

        fun bind(namirnica: Namirnica) {
            namirnicaNaziv.text = namirnica.naziv
            namirnicaKolicina.text = namirnica.kolicina_kupovina.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
        val shoppingView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.shopping_list_item, parent, false)
        return ShoppingListViewHolder(shoppingView)
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        holder.bind(shoppingList[position])
    }

    override fun getItemCount() = shoppingList.size
}