package hr.foi.rampu.fridgium.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import hr.foi.rampu.fridgium.entities.Namirnica
import hr.foi.rampu.fridgium.entities.NamirnicaRecepta

const val nam: String ="namirnica_recepta"
const val repid: String ="recept_id"
const val namid: String ="namirnica_id"
interface namirnica_receptaDAO {
    @Query("SELECT * FROM heroku_d1561a1a0615483.$nam WHERE $repid = :id")
    fun getNamirnice(id: Int): List<NamirnicaRecepta>

    @Query("SELECT * FROM heroku_d1561a1a0615483.$nam WHERE $namid = :id")
    fun getRecepti(id: Int): List<NamirnicaRecepta>


    @Insert
    fun insertNamirnica(vararg namirnica: Namirnica): List<Long>

    @Delete
    fun deleteNamirnica(vararg namirnica: Namirnica)
}