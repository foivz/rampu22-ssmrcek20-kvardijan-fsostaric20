const Baza = require("../baza.js");

class namirnicareceptaDAO{
    
    constructor() {
		this.baza = new Baza();
	}

    dajSveNamirnice = async function (id) {
        this.baza.spojiSeNaBazu();
        let sql = "SELECT * FROM heroku_d1561a1a0615483.namirnica_recepta WHERE recept_id=?"
        var podaci = await this.baza.izvrsiUpit(sql, [id])
        this.baza.zatvoriVezu();
        return podaci;
    }

    dajSveRecepte = async function (id) {
        this.baza.spojiSeNaBazu();
        let sql = "SELECT * FROM heroku_d1561a1a0615483.namirnica_recepta WHERE namirnica_id=?"
        var podaci = await this.baza.izvrsiUpit(sql, [id])
        this.baza.zatvoriVezu();
        return podaci;
    }
    
    dodaj = async function (Rid,Nid,kolicina) {
        let sql = "INSERT INTO heroku_d1561a1a0615483.namirnica_recepta (recept_id,namirnica_id,kolicina) VALUES (?,?,?)";
        let podaci = [Rid,Nid,kolicina];
        await this.baza.izvrsiUpit(sql,podaci);
        return true;
    }
    
    obrisi = async function (id) {
        let sql = "DELETE FROM heroku_d1561a1a0615483.namirnica_recepta WHERE recept_id=?";
        await this.baza.izvrsiUpit(sql,[id]);
        return true;
    }    

}

module.exports = namirnicareceptaDAO;