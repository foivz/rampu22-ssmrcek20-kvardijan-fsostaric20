package hr.foi.rampu.fridgium.adapters

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.entities.Namirnica
import hr.foi.rampu.fridgium.helpers.DodavanjeKolicineNamirnicaDialogHelper

class NamirnicaAdapter(private val namirnicaList: List<Namirnica>) :
    RecyclerView.Adapter<NamirnicaAdapter.NamirnicaViewHolder>() {

    inner class NamirnicaViewHolder(view: View): RecyclerView.ViewHolder(view){
        protected val pogled = view
        private val imeNamirnice: TextView
        private val kolicinaNamirnice: TextView
        private val ikonicaNamirnice: ImageView
        private val mjernaJedinicaHladnjak: TextView

        private val gumbDodaj : Button
        private val gumbOduzmi : Button

        init {
            ikonicaNamirnice = view.findViewById(R.id.img_ikona_namirnice_hladnjak)
            imeNamirnice = view.findViewById(R.id.tv_ime_namirnice)
            kolicinaNamirnice = view.findViewById(R.id.tv_kolicina_hladnjak)
            mjernaJedinicaHladnjak = view.findViewById(R.id.tv_mjernaJedinica_hladnjak)
            gumbDodaj = view.findViewById(R.id.button_dodaj)
            gumbOduzmi = view.findViewById(R.id.button_oduzimaj)

            gumbDodaj.setOnClickListener{
                val dodajKolicinuNamirniceDialog = LayoutInflater
                    .from(pogled.context)
                    .inflate(R.layout.dodaj_namirnicu_dialog, null)

                AlertDialog.Builder(pogled.context)
                    .setView(dodajKolicinuNamirniceDialog)
                    .setTitle(imeNamirnice.text)
                    .setPositiveButton(R.string.dodaj_kolicinu_namirnice){_, _->

                    }
                    .show()

                val pomagacDodavanjaKolicine = DodavanjeKolicineNamirnicaDialogHelper(dodajKolicinuNamirniceDialog)
                pomagacDodavanjaKolicine.popuniKolicinu(kolicinaNamirnice.text.toString().toInt())
            }
        }

        fun bind(namirnica: Namirnica){
            imeNamirnice.text = namirnica.naziv
            kolicinaNamirnice.text = namirnica.kolicina_hladnjak.toString()
            val draw = ContextCompat.getDrawable(pogled.context,OdaberiIkonicu(namirnica.naziv))
            ikonicaNamirnice.setImageDrawable(draw)
            mjernaJedinicaHladnjak.text = namirnica.mjernaJedinica.naziv
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