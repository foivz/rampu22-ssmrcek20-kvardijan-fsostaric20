package hr.foi.rampu.fridgium.database

import hr.foi.rampu.fridgium.entities.Namirnica
import hr.foi.rampu.fridgium.entities.NamirnicaRecepta
import hr.foi.rampu.fridgium.entities.Recept
import java.sql.ResultSet

const val nam: String ="namirnica_recepta"
const val repidd: String ="recept_id"
const val namidd: String ="namirnica_id"
class namirnica_receptaDAO {

    fun getNamirnice(id: Int): MutableList<NamirnicaRecepta>{
        val sql: String = "SELECT * FROM heroku_d1561a1a0615483.$nam WHERE $repidd = $id"
        val vrijednosti : MutableList<NamirnicaRecepta> = pretvoriVrijednosti(Connector.executeSearchQuery(sql))
        return vrijednosti
    }

    fun getRecepti(id: Int): List<NamirnicaRecepta>{
        val sql: String = "SELECT * FROM heroku_d1561a1a0615483.$nam WHERE $namidd = $id"
        val vrijednosti : MutableList<NamirnicaRecepta> = pretvoriVrijednosti(Connector.executeSearchQuery(sql))
        return vrijednosti
    }

    fun insertNamirnicaRecepta(namirnica: Namirnica,recept: Recept,kolicina: Int){
        val repid= recept.id
        val namid= namirnica.id
        val sql: String = "INSERT INTO heroku_d1561a1a0615483.$nam VALUES ($repid, $namid, $kolicina)"
        Connector.executeQuery(sql)
    }

    fun deleteNamirnica(recept: Recept){
        val repid= recept.id
        val sql: String = "DELETE FROM heroku_d1561a1a0615483.$nam WHERE $repidd = $repid"
        Connector.executeQuery(sql)
    }

    fun pretvoriVrijednosti(resultSet: ResultSet) : MutableList<NamirnicaRecepta>{
        val popis : MutableList<NamirnicaRecepta> = arrayListOf()
        while(resultSet.next()){
            val repid: Int  = resultSet.getInt("recept_id")
            val namid: Int  = resultSet.getInt("namirnica_id")
            val kolicina : Int = resultSet.getInt("kolicina")
            val namirnicaRecepta = NamirnicaRecepta(repid, namid, kolicina);
            popis.add(namirnicaRecepta)
        }
        return popis
    }
}