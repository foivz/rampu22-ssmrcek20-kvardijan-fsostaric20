package hr.foi.rampu.fridgium.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import hr.foi.rampu.fridgium.entities.Namirnica
import hr.foi.rampu.fridgium.entities.NamirnicaRecepta
import java.sql.ResultSet

const val rec: String ="namirnica"
interface NamirnicaDAO {

    fun getNamirnice(): List<Namirnica>{
        val sql: String = "SELECT * FROM heroku_d1561a1a0615483.$rec"
        val vrijednosti : MutableList<Namirnica> = pretvoriVrijednosti(Connector.executeSearchQuery(sql))
        return vrijednosti
    }

    @Insert
    fun insertNamirnica(vararg namirnica: Namirnica): List<Long>

    @Delete
    fun deleteNamirnica(vararg namirnica: Namirnica)

    fun pretvoriVrijednosti(resultSet: ResultSet) : MutableList<Namirnica>{
        val popis : MutableList<Namirnica> = arrayListOf()
        while(resultSet.next()){
            val id: Int  = resultSet.getInt("id")
            val naziv: String  = resultSet.getString("naziv")
            val kolicina_hladnjak : Int = resultSet.getInt("kolicina_hladnjak")
            val mjernaJedinica_id : Int = resultSet.getInt("mjerna_jedinica_id")
            val kolicina_kupovina : Int = resultSet.getInt("kolicina_kupovina")
            val namirnica = Namirnica(id,naziv,kolicina_hladnjak,mjernaJedinica_id,kolicina_kupovina)
            popis.add(namirnica)
        }
        return popis
    }

}