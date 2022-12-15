package hr.foi.rampu.fridgium.helpers

import android.content.Context
import android.view.View
import android.widget.Toast

class FavoritiHelper(view: View) {
    val pogled = view

    fun DodajUFavorite(nazivNamirnice: String, minimalnaKolicina: Float){
        pogled.context?.getSharedPreferences("favoriti_preferences", Context.MODE_PRIVATE)?.apply {
            edit().putFloat(nazivNamirnice, minimalnaKolicina).apply()
            Toast.makeText(
                pogled.context,
                "Namirnica $nazivNamirnice je dodana u favorite s kolicinom $minimalnaKolicina",
                Toast.LENGTH_LONG)
                .show()
        }
    }

    fun MakniIzFavorita(nazivNamirnice: String){
        pogled.context?.getSharedPreferences("favoriti_preferences", Context.MODE_PRIVATE)?.apply {
                edit().remove(nazivNamirnice).apply()
                Toast.makeText(
                    pogled.context, "Namirnica $nazivNamirnice je maknuta iz favorita", Toast.LENGTH_LONG)
                    .show()
        }
    }

    fun ProvjeriFavorit(nazivNamirnice: String): Boolean{
        var provjeriPostojanost = -1f
        pogled.context?.getSharedPreferences("favoriti_preferences", Context.MODE_PRIVATE)?.apply {
            provjeriPostojanost = getFloat(nazivNamirnice, -1f)
        }
/*        Toast.makeText(
            pogled.context, "Namirnica $nazivNamirnice ime zadan $provjeriPostojanost", Toast.LENGTH_SHORT)
            .show()*/
        return provjeriPostojanost != -1f
    }
}