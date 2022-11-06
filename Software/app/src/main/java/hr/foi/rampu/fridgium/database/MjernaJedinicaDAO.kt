package hr.foi.rampu.fridgium.database

import hr.foi.rampu.fridgium.entities.MjernaJedinica
import hr.foi.rampu.fridgium.entities.Namirnica
import hr.foi.rampu.fridgium.entities.NamirnicaRecepta
import java.sql.ResultSet
const val mj : String ="mjerna_jedinica"

class MjernaJedinicaDAO {

    fun getMjerneJedinice(): MutableList<MjernaJedinica> {
        val sql: String = "SELECT * FROM heroku_d1561a1a0615483.$mj"
        val vrijednosti : MutableList<MjernaJedinica> = pretvoriVrijednosti(Connector.executeSearchQuery(sql))
        return vrijednosti
    }

    fun getMjernaJedinica(id: Int): MjernaJedinica {
        val sql: String = "SELECT * FROM heroku_d1561a1a0615483.$mj WHERE id=$id"
        val vrijednost : MjernaJedinica = pretvoriVrijednost(Connector.executeSearchQuery(sql))
        return vrijednost
    }

    fun pretvoriVrijednosti(resultSet: ResultSet): MutableList<MjernaJedinica>{
        val popis : MutableList<MjernaJedinica> = arrayListOf()
        while(resultSet.next()){
            val id: Int  = resultSet.getInt("id")
            val naziv: String = resultSet.getString("naziv")
            val MjernaJedinica = MjernaJedinica(id,naziv)
            popis.add(MjernaJedinica)
        }
        return popis

    }

    fun pretvoriVrijednost(resultSet: ResultSet): MjernaJedinica{
            val id: Int  = resultSet.getInt("id")
            val naziv: String = resultSet.getString("naziv")
            val MjernaJedinica = MjernaJedinica(id,naziv)
            return MjernaJedinica

    }
}