package hr.foi.rampu.fridgium.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import hr.foi.rampu.fridgium.entities.Namirnica
import hr.foi.rampu.fridgium.entities.Recept
const val rec: String = "recept"
const val rec2: String = "namirnica_recepta"
const val rec3: String = "naziv"
@Dao
interface ReceptDAO {
    @Query ("SELECT * FROM heroku_d1561a1a0615483.$rec")
    fun getRecepti(): List<Recept>

    @Query ("SELECT * FROM heroku_d1561a1a0615483.$rec WHERE $rec3 LIKE '%:naziv%'")
    fun getRecept(naziv : String): Recept

    @Insert
    fun insertRecept(vararg recept: Recept): List<Long>

    @Delete
    fun deleteRecept(vararg recept: Recept)
}