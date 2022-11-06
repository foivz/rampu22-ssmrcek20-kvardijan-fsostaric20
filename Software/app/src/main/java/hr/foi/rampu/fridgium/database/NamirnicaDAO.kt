package hr.foi.rampu.fridgium.database


import hr.foi.rampu.fridgium.entities.Namirnica
import java.sql.ResultSet

const val rec: String ="namirnica"
class NamirnicaDAO {

    fun getNamirnice(): MutableList<Namirnica>{
        val sql: String = "SELECT * FROM heroku_d1561a1a0615483.$rec"
        val vrijednosti : MutableList<Namirnica> = pretvoriVrijednosti(Connector.executeSearchQuery(sql))
        return vrijednosti
    }


    fun insertNamirnica(vararg namirnica: Namirnica){
        for(nam in namirnica){
            val id = nam.id
            val naziv = nam.naziv
            val sql: String = "INSERT INTO heroku_d1561a1a0615483.$rec VALUES ($id,$naziv)"
            Connector.executeQuery(sql)
        }

    }

    fun deleteNamirnica(vararg namirnica: Namirnica){
        for(nam in namirnica){
            val id = nam.id
            val sql: String = "DELETE FROM heroku_d1561a1a0615483.$rec WHERE id = $id"
            Connector.executeQuery(sql)
        }
    }

    fun pretvoriVrijednosti(resultSet: ResultSet) : MutableList<Namirnica>{
        val popis : MutableList<Namirnica> = arrayListOf()
        val MjernaJedinicaDAO : MjernaJedinicaDAO = MjernaJedinicaDAO()
        while(resultSet.next()){
            val id: Int  = resultSet.getInt("id")
            val naziv: String  = resultSet.getString("naziv")
            val kolicina_hladnjak : Int = resultSet.getInt("kolicina_hladnjak")
            val mjernaJedinica_id : Int = resultSet.getInt("mjerna_jedinica_id")
            val MjernaJedinica = MjernaJedinicaDAO.getMjernaJedinica(mjernaJedinica_id)
            val kolicina_kupovina : Int = resultSet.getInt("kolicina_kupovina")
            val namirnica = Namirnica(id,naziv,kolicina_hladnjak,MjernaJedinica,kolicina_kupovina)
            popis.add(namirnica)
        }
        return popis
    }

}