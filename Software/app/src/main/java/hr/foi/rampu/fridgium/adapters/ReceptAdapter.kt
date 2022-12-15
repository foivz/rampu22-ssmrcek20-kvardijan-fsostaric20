package hr.foi.rampu.fridgium.adapters

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
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
        private lateinit var imgbutton : ImageButton
        private lateinit var recyclerView: RecyclerView
        private lateinit var dialog: AlertDialog
        private lateinit var noviView : View

        init {
            nazivRecept = view.findViewById(R.id.tv_naziv_recept)
            opisRecept = view.findViewById(R.id.tv_opis_recept)
            namirniceRecept = view.findViewById(R.id.tv_namirnice_recepta)
            bojaRecept = view.findViewById(R.id.SV_bojadostupno)
            btnPrikaziVise = view.findViewById(R.id.btn_prikazi_vise)
            view.setOnLongClickListener{
                val recept : Recept = ReceptList[this.adapterPosition]
                Log.d("errorimidolaze", "Tu samerr")
                dialogObrisi(view)
                val naslov = dialog.findViewById<TextView>(R.id.tvIme_Recepta)
                val tekst = dialog.findViewById<TextView>(R.id.tv_opis_recepta_prikazi_vise)
                naslov.text = recept.naziv
                tekst.text = "Ovo će trajno obrisati recept iz aplikacije"
                imgbutton = dialog.findViewById(R.id.imgbtn)
                imgbutton.setOnClickListener{
                    dialog.cancel()
                }
                return@setOnLongClickListener true
            }
            btnPrikaziVise.setOnClickListener{
                val recept : Recept = ReceptList[this.adapterPosition]
                otvoriDialog(recept, view)
                Log.d("errorimidolaze","${ReceptPrikaziViseAdapter(recept.namirnice).itemCount}")
                recyclerView = dialog.findViewById(R.id.PV_recyclerview_namirnice)
                recyclerView.layoutManager = LinearLayoutManager(dialog.context)
                recyclerView.adapter = ReceptPrikaziViseAdapter(recept.namirnice)
                imgbutton = dialog.findViewById(R.id.imgbtn)
                Log.d("errorimidolaze", "${recyclerView.adapter}")
                imgbutton.setOnClickListener{
                    dialog.cancel()
                }

            }
        }

        private fun dialogObrisi(view: View){
            val prikaziVise = LayoutInflater.from(view.context)
                .inflate(R.layout.fragment_prikazi_vise_recept, null)
            dialog = AlertDialog.Builder(view.context).setView(prikaziVise).setPositiveButton("Obriši",obrisi()).show()
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                ContextCompat.getColor(view.context, R.color.color_accent))
        }

        private fun obrisi(): DialogInterface.OnClickListener? {
            return null
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

        private fun otvoriDialog(recept: Recept, view: View){


                dialog = inflateDialog(view)
                val opisReceptPV = dialog.findViewById<TextView>(R.id.tv_opis_recepta_prikazi_vise)
                val nazivReceptPV = dialog.findViewById<TextView>(R.id.tvIme_Recepta)
                nazivReceptPV.text = recept.naziv
                opisReceptPV.text = recept.opis

                Log.d("errorimidolaze","${recept.namirnice}")

        }

        private fun inflateDialog(view: View): AlertDialog {
            val prikaziVise = LayoutInflater.from(view.context)
                .inflate(R.layout.fragment_prikazi_vise_recept, null)
            noviView = prikaziVise
            return AlertDialog.Builder(view.context).setView(prikaziVise).show()


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