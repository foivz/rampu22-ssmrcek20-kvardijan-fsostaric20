package hr.foi.rampu.fridgium.adapters

import android.app.AlertDialog
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.entities.Namirnica
import hr.foi.rampu.fridgium.helpers.*


class NamirnicaAdapter(private val namirnicaList: MutableList<Namirnica>) :
    RecyclerView.Adapter<NamirnicaAdapter.NamirnicaViewHolder>() {

    inner class NamirnicaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val pogled = view
        private val imeNamirnice: TextView
        private val kolicinaNamirnice: TextView
        private val ikonicaNamirnice: ImageView
        private val mjernaJedinicaHladnjak: TextView
        private val gumbDodaj: Button
        private val gumbOduzmi: Button
        private val oznakaFavorita: SurfaceView
        private val pomagacFavorita: FavoritiHelper
        private val pomagacPrikaza: DisplayHelper

        init {
            ikonicaNamirnice = view.findViewById(R.id.img_ikona_namirnice_hladnjak)
            imeNamirnice = view.findViewById(R.id.tv_ime_namirnice)
            kolicinaNamirnice = view.findViewById(R.id.tv_kolicina_hladnjak)
            mjernaJedinicaHladnjak = view.findViewById(R.id.tv_mjernaJedinica_hladnjak)
            gumbDodaj = view.findViewById(R.id.button_dodaj)
            gumbOduzmi = view.findViewById(R.id.button_oduzimaj)
            oznakaFavorita = view.findViewById(R.id.sv_boja_favorit)
            pomagacFavorita = FavoritiHelper(view)
            pomagacPrikaza = DisplayHelper()

            gumbDodaj.setOnClickListener {
                prikaziDialogDodavanjaNamirnice(view)
            }

            gumbOduzmi.setOnClickListener {
                prikaziDialogOduzimanjaNamirnice(view)
            }

            view.setOnLongClickListener {
                prikaziDialogUredivanjaNamirnice(view)
                return@setOnLongClickListener true
            }
        }

        fun bind(namirnica: Namirnica) {
            imeNamirnice.text = namirnica.naziv
            if (pomagacPrikaza.provjeriBroj(namirnica.kolicina_hladnjak)) {
                kolicinaNamirnice.text =
                    pomagacPrikaza.dajBroj(namirnica.kolicina_hladnjak).toString()
            } else {
                kolicinaNamirnice.text = namirnica.kolicina_hladnjak.toString()
            }
            val draw = ContextCompat.getDrawable(pogled.context, odaberiIkonicu(namirnica.naziv))
            ikonicaNamirnice.setImageDrawable(draw)
            mjernaJedinicaHladnjak.text = namirnica.mjernaJedinica.naziv
            if (pomagacFavorita.provjeriFavorit(namirnica.naziv)) {
                oznakaFavorita.setBackgroundColor(Color.YELLOW)
            } else {
                oznakaFavorita.setBackgroundColor(Color.WHITE)
            }
        }

        private fun odaberiIkonicu(naziv: String): Int {
            return when (naziv.lowercase()) {
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
                "riba" -> R.drawable.fish_svgrepo_com
                "svinjetina" -> R.drawable.pork_svgrepo_com
                "piletina" -> R.drawable.chicken_svgrepo_com
                "rajčica" -> R.drawable.tomato_svgrepo_com
                else -> R.drawable.ic_baseline_dining_24
            }
        }

        private fun prikaziDialogDodavanjaNamirnice(view: View) {
            val dodajKolicinuNamirniceDialog = LayoutInflater
                .from(pogled.context)
                .inflate(R.layout.dodaj_namirnicu_dialog, null)

            val pomagacDodavanjaKolicine = DodavanjeKolicineNamirnicaDialogHelper(
                dodajKolicinuNamirniceDialog
            )

            val dialogDodaj = AlertDialog.Builder(pogled.context)
                .setView(dodajKolicinuNamirniceDialog)
                .setTitle(imeNamirnice.text)
                .setPositiveButton(R.string.dodaj_kolicinu_namirnice) { _, _ ->
                    val pozicija = this.adapterPosition
                    val odabranaNamirnica = namirnicaList[pozicija]
                    val azurnaNamirnica = pomagacDodavanjaKolicine.azurirajNamirnicu(odabranaNamirnica)
                    namirnicaList.removeAt(pozicija)
                    notifyItemRemoved(pozicija)
                    namirnicaList.add(pozicija, azurnaNamirnica)
                    notifyItemInserted(pozicija)
                }
                .show()

            dialogDodaj.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(ContextCompat.getColor(view.context, R.color.color_accent))
            dialogDodaj.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(ContextCompat.getColor(view.context, R.color.color_accent))
        }

        private fun prikaziDialogOduzimanjaNamirnice(view: View) {
            val oduzmiKolicinuNamirniceDialog = LayoutInflater
                .from(pogled.context)
                .inflate(R.layout.oduzmi_namirnicu_dialog, null)

            val pomagacOduzimanjaKolicine = OduzimanjeKolicineNamirnicaDialogHelper(
                oduzmiKolicinuNamirniceDialog
            )

            val dialogOduzmi = AlertDialog.Builder(pogled.context)
                .setView(oduzmiKolicinuNamirniceDialog)
                .setTitle(imeNamirnice.text)
                .setPositiveButton(R.string.oduzmi_kolicinu_namirnice) { _, _ ->
                    val pozicija = this.adapterPosition
                    val odabranaNamirnica = namirnicaList[pozicija]
                    val azurnaNamirnica = pomagacOduzimanjaKolicine.azurirajNamirnicu(odabranaNamirnica)
                    namirnicaList.removeAt(pozicija)
                    notifyItemRemoved(pozicija)
                    namirnicaList.add(pozicija, azurnaNamirnica)
                    notifyItemInserted(pozicija)
                }
                .show()

            dialogOduzmi.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(ContextCompat.getColor(view.context, R.color.color_accent))
            dialogOduzmi.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(ContextCompat.getColor(view.context, R.color.color_accent))
        }

        private fun prikaziDialogUredivanjaNamirnice(view: View) {
            val urediNamirnicuDialog = LayoutInflater
                .from(pogled.context)
                .inflate(R.layout.uredivanje_naziva_mj_namirnice_dialog, null)
            val pomagacUredivanjaNamirnice = UredivanjeNamirniceDialogHelper(urediNamirnicuDialog)
            val pozicija = this.adapterPosition
            val odabranaNamirnica = namirnicaList[pozicija]
            val nazivNamirnice = odabranaNamirnica.naziv

            var favBtn = "Dodaj Favorit"
            Log.d("FAV", "PROVJERAVAM " + imeNamirnice.text.toString())
            if (pomagacFavorita.provjeriFavorit(imeNamirnice.text.toString())) favBtn =
                "Makni Favorit"

            val dialogEdit = AlertDialog.Builder(view.context)
                .setView(urediNamirnicuDialog)
                .setTitle("Uredi namirnicu $nazivNamirnice")
                .setPositiveButton("Uredi") { _, _ ->
                    val azurnaNamirnica = pomagacUredivanjaNamirnice.azurirajPodatke(odabranaNamirnica)
                    namirnicaList.removeAt(pozicija)
                    notifyItemRemoved(pozicija)
                    namirnicaList.add(pozicija, azurnaNamirnica)
                    notifyItemInserted(pozicija)
                }
                .setNeutralButton(favBtn) { _, _ ->
                    Log.d("FAV", favBtn)
                    pomagacUredivanjaNamirnice.dodajiliMakniFavorit(nazivNamirnice)
                }
                .show()
            dialogEdit.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(ContextCompat.getColor(view.context, R.color.color_accent))
            dialogEdit.getButton(AlertDialog.BUTTON_NEUTRAL)
                .setTextColor(ContextCompat.getColor(view.context, R.color.color_accent))

            pomagacUredivanjaNamirnice.popuniNaziv(nazivNamirnice)
            pomagacUredivanjaNamirnice.dohvatiMJ(odabranaNamirnica)
            pomagacUredivanjaNamirnice.dodajiliMakniMinKolUpis(nazivNamirnice)
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

    fun dodajNamirnicu(namirnica: Namirnica){
        var index = -1
        for(namirnicaIzListe in namirnicaList){
            if (namirnicaIzListe.naziv == namirnica.naziv){
                index = namirnicaList.indexOf(namirnicaIzListe)
                namirnicaList.removeAt(index)
                notifyItemRemoved(index)
                break
            }
        }
        if(index == -1){
            index = namirnicaList.size
        }
        namirnicaList.add(index,namirnica)
        notifyItemInserted(index)
    }
}