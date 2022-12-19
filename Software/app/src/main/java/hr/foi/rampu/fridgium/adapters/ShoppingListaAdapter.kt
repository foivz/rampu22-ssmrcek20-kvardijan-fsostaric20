package hr.foi.rampu.fridgium.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.entities.Namirnica
import hr.foi.rampu.fridgium.helpers.DisplayHelper
import hr.foi.rampu.fridgium.helpers.KolicinaHelper
import hr.foi.rampu.fridgium.helpers.NovaNamirnicaListaZaKupovinuHelper

class ShoppingListaAdapter(private val shoppingList: MutableList<Namirnica>) : RecyclerView.Adapter<ShoppingListaAdapter.ShoppingListViewHolder>() {
    inner class ShoppingListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val namirnicaNaziv: TextView
        private val namirnicaKolicina: TextView
        private val namirnicaDelete: ImageButton
        private val namirnicaFridge: ImageButton
        private val pomagacPrikaza: DisplayHelper

        init {
            pomagacPrikaza = DisplayHelper()
            namirnicaNaziv = view.findViewById(R.id.tv_naziv)
            namirnicaKolicina = view.findViewById(R.id.tv_kolicina)
            namirnicaDelete = view.findViewById(R.id.img_delete)
            namirnicaFridge = view.findViewById(R.id.img_fridge)
            val novaNamirnicaListaZaKupovinuHelper = LayoutInflater.from(view.context).inflate(R.layout.forma_nova_namirnica_za_listu_namirnica,null)
            val helper = NovaNamirnicaListaZaKupovinuHelper(novaNamirnicaListaZaKupovinuHelper)

            namirnicaFridge.setOnClickListener{
                val pozicija = this.adapterPosition
                val namirnica = shoppingList[pozicija]
                val novaNamirnica = Namirnica(namirnica.id,namirnica.naziv,namirnica.kolicina_kupovina+namirnica.kolicina_hladnjak,namirnica.mjernaJedinica,0f)
                helper.AzurirajUbazi(novaNamirnica)
                shoppingList.removeAt(pozicija)
                notifyItemRemoved(pozicija)
                Toast.makeText(
                    view.context,
                    "Namirnica je dodana u hladnjak",
                    Toast.LENGTH_LONG
                ).show()
            }
            namirnicaDelete.setOnClickListener {
                val pozicija = this.adapterPosition
                val namirnica = shoppingList[pozicija]
                val novaNamirnica = Namirnica(namirnica.id,namirnica.naziv,-1f,namirnica.mjernaJedinica,0f)
                helper.AzurirajUbazi(novaNamirnica)
                shoppingList.removeAt(pozicija)
                notifyItemRemoved(pozicija)
            }
            view.setOnLongClickListener {
                prikaziDialogUredivanjaKolicine(view)
                return@setOnLongClickListener true
            }
        }

        private fun prikaziDialogUredivanjaKolicine(view: View) {
            val kolicinaHelper = LayoutInflater.from(view.context)
                .inflate(R.layout.forma_za_promjenu_kolicine, null)
            val helper = KolicinaHelper(kolicinaHelper)
            val pozicija = this.adapterPosition
            var odabranaNamirnica = shoppingList[pozicija]
            val dialog: AlertDialog = AlertDialog.Builder(view.context)
                .setView(kolicinaHelper)
                .setTitle("Promijeni količinu:")
                .setPositiveButton("Promjeni") { _, _ ->
                    odabranaNamirnica = helper.azurirajKolicinu(odabranaNamirnica)
                    shoppingList.removeAt(pozicija)
                    notifyItemRemoved(pozicija)
                    shoppingList.add(pozicija,odabranaNamirnica)
                    notifyItemInserted(pozicija)
                }
                .show()
            helper.prikaziMJ(odabranaNamirnica)
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(ContextCompat.getColor(view.context, R.color.color_accent))
        }

        @SuppressLint("SetTextI18n")
        fun bind(namirnica: Namirnica) {
            namirnicaNaziv.text = namirnica.naziv
            if (pomagacPrikaza.provjeriBroj(namirnica.kolicina_kupovina)) {
                namirnicaKolicina.text = pomagacPrikaza.dajBroj(namirnica.kolicina_kupovina).toString() + " " + namirnica.mjernaJedinica.naziv
            } else {
                namirnicaKolicina.text = namirnica.kolicina_kupovina.toString() + " " + namirnica.mjernaJedinica.naziv
            }
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

    fun dodajNamirnicu(namirnica: Namirnica) {
        var index = -1
        for (namirnicaList in shoppingList){
            if(namirnicaList.naziv == namirnica.naziv){
                index = shoppingList.indexOf(namirnicaList)
                shoppingList.removeAt(index)
                notifyItemRemoved(index)
                break
            }
        }
        if(index == -1){
            index = shoppingList.size
        }
        shoppingList.add(index,namirnica)
        notifyItemInserted(index)
    }
}
