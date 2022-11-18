const Baza = require("../baza.js");

class namirnicaDAO{
    
    constructor() {
		this.baza = new Baza();
	}

    dajSve = async function () {
        this.baza.spojiSeNaBazu();
        let sql = "SELECT * FROM heroku_d1561a1a0615483.namirnica"
        var podaci = await this.baza.izvrsiUpit(sql, [])
        this.baza.zatvoriVezu();
        return podaci;
    }
    
    dodaj = async function (naziv,idMJ) {
        let sql = "INSERT INTO heroku_d1561a1a0615483.namirnica (naziv,kolicina_hladnjak,mjerna_jedinica_id,kolicina_kupovina) VALUES (?,0,?,0)";
        let podaci = [naziv,idMJ];
        await this.baza.izvrsiUpit(sql,podaci);
        return true;
    }
    
    obrisi = async function (id) {
        let sql = "DELETE FROM heroku_d1561a1a0615483.namirnica WHERE id=?";
        await this.baza.izvrsiUpit(sql,[id]);
        return true;
    }    

}

module.exports = namirnicaDAO;