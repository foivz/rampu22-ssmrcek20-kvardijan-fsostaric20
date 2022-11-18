
const mysql = require("mysql2");
const ds = require("fs");

class Baza {

    constructor() {
        this.vezaDB = mysql.createConnection({
            host: "eu-cdbr-west-03.cleardb.net",
            user: "bde233f063851b",
            password: "93a9ac41",
            database: "heroku_d1561a1a0615483"
        });
    }

    spojiSeNaBazu(){
         this.vezaDB.connect((err) => {
            if (err) {
                console.log("Greška: ", err);
                this.vezaDB.end();
            }
        });
    }

    izvrsiUpit(sql, podaciZaSQL, povratnaFunkcija) {
        this.vezaDB.query(sql, podaciZaSQL, povratnaFunkcija);
    }

    izvrsiUpit(sql, podaciZaSQL){
        return new Promise((uspjeh,neuspjeh) => {
            this.vezaDB.query(sql, podaciZaSQL, (greska, rezultat) => {
                if(greska)
                    neuspjeh(greska);
                else
                    uspjeh(rezultat);
            });
        });
    }

    zatvoriVezu() {
        this.vezaDB.close();
    }
}

module.exports = Baza;