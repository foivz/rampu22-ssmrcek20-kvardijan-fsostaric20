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
import hr.foi.rampu.fridgium.helpers.OduzimanjeKolicineNamirnicaDialogHelper

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

                val pomagacDodavanjaKolicine = DodavanjeKolicineNamirnicaDialogHelper(
                    dodajKolicinuNamirniceDialog)

                AlertDialog.Builder(pogled.context)
                    .setView(dodajKolicinuNamirniceDialog)
                    .setTitle(imeNamirnice.text)
                    .setPositiveButton(R.string.dodaj_kolicinu_namirnice){_, _->
                        val pozicija = this.adapterPosition
                        val odabranaNamirnica = namirnicaList[pozicija]
                        pomagacDodavanjaKolicine.azurirajNamirnicu(odabranaNamirnica)
                    }
                    .show()

                //pomagacDodavanjaKolicine.popuniKolicinu(kolicinaNamirnice.text.toString().toInt())
            }

            gumbOduzmi.setOnClickListener{
                val oduzmiKolicinuNamirniceDialog = LayoutInflater
                    .from(pogled.context)
                    .inflate(R.layout.oduzmi_namirnicu_dialog,null)

                val pomagacOduzimanjaKolicine = OduzimanjeKolicineNamirnicaDialogHelper(
                    oduzmiKolicinuNamirniceDialog)

                AlertDialog.Builder(pogled.context)
                    .setView(oduzmiKolicinuNamirniceDialog)
                    .setTitle(imeNamirnice.text)
                    .setPositiveButton(R.string.oduzmi_kolicinu_namirnice){_, _->
                        val pozicija = this.adapterPosition
                        val odabranaNamirnica = namirnicaList[pozicija]
                        pomagacOduzimanjaKolicine.azurirajNamirnicu(odabranaNamirnica)
                    }
                    .show()

                //pomagacOduzimanjaKolicine.popuniKolicinu(kolicinaNamirnice.text.toString().toInt())
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
            return when(naziv.lowercase()){
                "jaje" -> R.drawable.egg_svgrepo_com
                "maslac" -> R.drawable.butter_svgrepo_com
                "mlijeko" -> R.drawable.milk_svgrepo_com
                "jabuka" -> R.drawable.apple_svgrepo_com
                "banana" -> R.drawable.banana_svgrepo_com
                "kruh" -> R.drawable.bread_svgrepo_com
                "kukuruz" -> R.drawable.corn_svgrepo_com
                "luk" -> R.drawable.onion_svgrepo_com
                "naranča" -> R.drawable.orange_svgrepo_com
                "krumpir" -> R.drawable.potato_svgrepo_com
                "kobasica" -> R.drawable.sausage_svgrepo_com
                "kečap" -> R.drawable.sauce_ketchup_svgrepo_com
                "senf" -> R.drawable.mustard_svgrepo_com
                "majoneza" -> R.drawable.mayonnaise_condiment_svgrepo_com
                "brašno" -> R.drawable.flour_svgrepo_com
                "šećer" -> R.drawable.sugar_svgrepo_com
                "limun" -> R.drawable.lemon_svgrepo_com
                "paprika" -> R.drawable.paprika_svgrepo_com
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