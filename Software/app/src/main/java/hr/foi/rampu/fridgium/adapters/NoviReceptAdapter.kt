package hr.foi.rampu.fridgium.adapters

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.fridgium.R
import hr.foi.rampu.fridgium.entities.NamirnicaPrikaz


class NoviReceptAdapter(private val ListNamirnica: List<NamirnicaPrikaz>) :
    RecyclerView.Adapter<NoviReceptAdapter.NamirniceViewHolder>() {
    var namirnice : MutableList<NamirnicaPrikaz> = arrayListOf()
    inner class NamirniceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var namirnica : NamirnicaPrikaz
        private val editTextKolicina : EditText
        private var nazivNamirnice : TextView
        private var mjernaJedinicaNamirnice : TextView
        init {
            editTextKolicina = view.findViewById(R.id.editText_spinner_kolicina)
            nazivNamirnice = view.findViewById(R.id.tv_spinner_prikaz_namirnice)
            mjernaJedinicaNamirnice = view.findViewById(R.id.tv_spinner_mjernajedinica)
            val checkbox = view.findViewById<CheckBox>(R.id.iv_spinner_odabrana_namirnica)
            checkbox.isChecked = false
            editTextKolicina.isEnabled = false
            checkbox.isClickable = false
            view.setOnClickListener {
                var postoji = false
                namirnica = ListNamirnica[this.adapterPosition]
                val copy = namirnice.listIterator()
                if(copy.hasNext()==false){
                    copy.add(namirnica)
                    checkbox.isChecked = true
                    editTextKolicina.isEnabled = true
                }

                    while (copy.hasNext()) {
                        val n = copy.next()
                        if (n.id == namirnica.id) {
                            copy.remove()
                            checkbox.isChecked = false
                            editTextKolicina.isEnabled = false
                            postoji = true
                            break
                        }
                    }
                if(postoji==false){
                        copy.add(namirnica)
                        checkbox.isChecked = true
                        editTextKolicina.isEnabled = true
                }

                Log.d("lista","$copy")
                Log.d("lista","$namirnice")
            }
            editTextKolicina.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    namirnica.kolicina = s.toString().trim().toFloat()
                }


            })
        }

        fun bind(namirnica: NamirnicaPrikaz) {
            nazivNamirnice.text = namirnica.naziv
            mjernaJedinicaNamirnice.text = namirnica.mjernaJedinica.naziv
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NamirniceViewHolder {
        val namirnicaView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.dodaj_novi_recept_recyclerview_item, parent, false)
        return NamirniceViewHolder(namirnicaView)
    }

    override fun onBindViewHolder(holder: NamirniceViewHolder, position: Int) {
        holder.bind(ListNamirnica[position])
    }

    override fun getItemCount(): Int {
        return ListNamirnica.size
    }

    fun getItemsList(): List<NamirnicaPrikaz>{
        return namirnice
    }

}