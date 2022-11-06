package hr.foi.rampu.fridgium.database


import java.sql.*
import java.util.Properties

object Connector {

        internal var conn: Connection? = null
        internal var username = "bde233f063851b"
        internal var password = "93a9ac41"
        @JvmStatic
        fun main(args: Array<String>) {
            //getConnection()

            //executeInsertQuery()

           //executeMySQLQuery(sql = )


        }

        fun executeSearchQuery(sql : String) : ResultSet{
            getConnection()
            var stmt: Statement? = null
            var resultset: ResultSet? = null

            try {
                stmt = conn!!.createStatement()
                resultset = stmt!!.executeQuery(sql)

                if (stmt.execute(sql)) {
                    resultset = stmt.resultSet
                }
                while (resultset!!.next()) {
                    println(resultset.getString(2))
                }
            } catch (ex: SQLException) {
                ex.printStackTrace()
            } finally {
                if (resultset != null) {
                    try {
                        resultset.close()
                    } catch (sqlEx: SQLException) {
                    }

                    resultset = null
                }

                if (stmt != null) {
                    try {
                        stmt.close()
                    } catch (sqlEx: SQLException) {
                    }

                    stmt = null
                }

                if (conn != null) {
                    try {
                        conn!!.close()
                    } catch (sqlEx: SQLException) {
                    }

                    conn = null

                }
            }
            return resultset!!
        }

        fun executeQuery(sql: String){
            getConnection()
            var statement : PreparedStatement? = null
            try {
                statement = conn!!.prepareStatement(sql)

                if(statement.execute(sql)){
                    println("Uspjeh")
                }
            } catch (ex: SQLException) {

                ex.printStackTrace()
            }

            if (conn != null) {
                try {
                    conn!!.close()
                } catch (sqlEx: SQLException) {
                }

                conn = null

            }

        }


        fun getConnection() {
            val connectionProps = Properties()
            connectionProps.put("username", username)
            connectionProps.put("password", password)
            try {
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance()
                conn = DriverManager.getConnection(
                    "jdbc:mysql://bde233f063851b:93a9ac41@eu-cdbr-west-03.cleardb.net/heroku_d1561a1a0615483?reconnect=true",
                    connectionProps
                )
            } catch (ex: SQLException) {

                ex.printStackTrace()
            } catch (ex: Exception) {

                ex.printStackTrace()
            }
        }
}