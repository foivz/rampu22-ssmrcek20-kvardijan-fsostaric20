const Baza = require("../baza.js");

class mjernajedinicaDAO{

    constructor(){
        this.baza = new Baza();
    }

    dajSve = async function () {
        this.baza.spojiSeNaBazu();
        let sql = "SELECT * FROM heroku_d1561a1a0615483.mjerna_jedinica"
        var podaci = await this.baza.izvrsiUpit(sql, [])
        this.baza.zatvoriVezu();
        return podaci;
    }

    daj = async function (id) {
        this.baza.spojiSeNaBazu();
        try{
            let sql = "SELECT * FROM heroku_d1561a1a0615483.mjerna_jedinica WHERE id=?"
            var podaci = await this.baza.izvrsiUpit(sql, [id])
        }finally{
            this.baza.zatvoriVezu();
        }
        if(podaci.length == 1)
            
			return podaci[0];
		else 
			return null;
    }

}

module.exports = mjernajedinicaDAO;