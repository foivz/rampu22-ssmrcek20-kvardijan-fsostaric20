package hr.foi.rampu.fridgium.database


import hr.foi.rampu.fridgium.entities.Recept
import java.sql.ResultSet

const val ime: String = "recept"
const val rec2: String = "naziv"

class ReceptDAO {
    fun getRecepti(): MutableList<Recept>{
        val sql: String = "SELECT * FROM heroku_d1561a1a0615483.$ime"
        val vrijednosti : MutableList<Recept> = pretvoriVrijednosti(Connector.executeSearchQuery(sql))
        return vrijednosti
    }

    fun getRecept(naziv : String): Recept{
        val sql: String = "SELECT * FROM heroku_d1561a1a0615483.$ime WHERE $rec2 LIKE %$naziv%"
        val vrijednost = pretvoriVrijednost(Connector.executeSearchQuery(sql))
        return vrijednost
    }

    fun insertRecept(vararg recept: Recept){
        for(rep in recept){
            val id = rep.id
            val naziv = rep.naziv
            val opis = rep.opis
            val sql: String = "INSERT INTO heroku_d1561a1a0615483.$ime VALUES ($id,$naziv,$opis)"
            Connector.executeQuery(sql)
        }
    }

    fun deleteRecept(vararg recept: Recept){
        for(rep in recept){
            val id = rep.id
            val sql: String = "DELETE FROM heroku_d1561a1a0615483.$ime WHERE id = $id"
            Connector.executeQuery(sql)
        }
    }

    fun pretvoriVrijednosti(resultSet: ResultSet): MutableList<Recept>{
        val popis : MutableList<Recept> = arrayListOf()
        while(resultSet.next()){
            val id: Int  = resultSet.getInt("id")
            val naziv = resultSet.getString("naziv")
            val opis = resultSet.getString("opis")
            val recept = Recept(id, naziv, opis)
            popis.add(recept)
        }
        return popis
    }

    fun pretvoriVrijednost(resultSet: ResultSet): Recept {
        val id: Int  = resultSet.getInt("id")
        val naziv: String = resultSet.getString("naziv")
        val opis = resultSet.getString("opis")
        val recept = Recept(id, naziv, opis)
        return recept

    }
}