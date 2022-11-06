package hr.foi.rampu.fridgium.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import hr.foi.rampu.fridgium.entities.Namirnica
const val rec: String ="namirnica"
interface NamirnicaDAO {

    @Query("SELECT * FROM heroku_d1561a1a0615483.$rec")
    fun getNamirnice(): List<Namirnica>

    @Insert
    fun insertNamirnica(vararg namirnica: Namirnica): List<Long>

    @Delete
    fun deleteNamirnica(vararg namirnica: Namirnica)
}