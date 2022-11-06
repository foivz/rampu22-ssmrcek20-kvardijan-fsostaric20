package hr.foi.rampu.fridgium.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import hr.foi.rampu.fridgium.entities.Recept
const val ime: String = "recept"
const val rec2: String = "naziv"
@Dao
interface ReceptDAO {
    @Query ("SELECT * FROM heroku_d1561a1a0615483.$ime")
    fun getRecepti(): List<Recept>

    @Query ("SELECT * FROM heroku_d1561a1a0615483.$ime WHERE $rec2 LIKE '%:naziv%'")
    fun getRecept(naziv : String): Recept

    @Insert
    fun insertRecept(vararg recept: Recept): List<Long>

    @Delete
    fun deleteRecept(vararg recept: Recept)
}