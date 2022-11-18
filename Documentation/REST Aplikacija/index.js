
const { json } = require("body-parser");
const express = require("express");
const mjernajedinicaDAO = require("./DAO/mjernajedinicaDAO");
const namirnicaDAO = require("./DAO/namirnicaDAO");
const namirnicareceptaDAO = require("./DAO/namirnicareceptaDAO.js");
const receptDAO = require("./DAO/receptDAO");
const server = express();
const data = require("./data.json")
let port = process.env.PORT || 3000;
pokreniServer();

function pokreniServer() {
    server.use(express.urlencoded({ extended: true }));
    server.use(express.json());
    pripremiPutanje(); 
    server.use((zahtjev, odgovor) => {
        odgovor.status(404);
        let poruka = { greska: "Nema resursa!" }
        odgovor.json(poruka);
    });

    server.listen(port, () => {
        console.log(`Server pokrenut na portu: ${port}`);
    });
}



function pripremiPutanje(){
    server.get("/",(zahtjev,odgovor)=>{
        odgovor.send("bokić")
    })
   server.get("/recepti",getRecepti)
   server.get("/namirnice",getNamirnice)
   server.get("/mjernejedinice",getMjerneJedinice)
   server.get("/mjernejedinice/:id",getMjernaJedinica)
   server.get("/namirnicerecepta/recepti/:id",getRecepteNamirnice)
   server.get("/namirnicerecepta/namirnice/:id",getNamirniceRecepta)
}

function getRecepti(zahtjev,odgovor){
    odgovor.type("application/json")
    let rDAO = new receptDAO();
    rDAO.dajSve().then((poruka) => {
        odgovor.send(JSON.stringify({"results" : poruka}));
    }).catch((error) => {
        console.error(error);
    });
}

function getNamirnice(zahtjev,odgovor){
    odgovor.type("application/json")
    let nDAO = new namirnicaDAO();
    
    nDAO.dajSve().then(async (poruka) => {
        let mjDAO = new mjernajedinicaDAO();
        let mjernajedinica = await mjDAO.dajSve();
        for(namirnica in poruka){
            console.log(namirnica +". NAMIRNICA:" +JSON.stringify(poruka[namirnica])+ "\n")
                for(mj in mjernajedinica){
                    console.log(namirnica+ " . NAMIRNICA: "+ " PROVJERA ZA MJERNU JEDINICU: "+JSON.stringify(mjernajedinica[mj]))
                if(mjernajedinica[mj].id == poruka[namirnica].mjerna_jedinica_id){

                    poruka[namirnica]['mjerna_jedinica_id'] = mjernajedinica[mj];
                }
                
                console.log(poruka[namirnica])
                }
                
            
        }
        odgovor.send(JSON.stringify({"results" : poruka}));
    }).catch((error) => {
        console.error(error);
    });
}

function getMjerneJedinice(zahtjev,odgovor){
    odgovor.type("application/json")
    let mjDAO = new mjernajedinicaDAO();
    mjDAO.dajSve().then((poruka) => {
        odgovor.send(JSON.stringify({"results" : poruka}));
    }).catch((error) => {
        console.error(error);
    });
}

function getMjernaJedinica(zahtjev,odgovor){
    odgovor.type("application/json")
    let id = zahtjev.params.id
    let mjDAO = new mjernajedinicaDAO();
    mjDAO.daj(id).then((poruka) => {
        odgovor.send(JSON.stringify({"results" : poruka}));
    }).catch((error) => {
        console.error(error);
    });
}

function getNamirniceRecepta(zahtjev,odgovor){
    odgovor.type("application/json")
    let id = zahtjev.params.id
    let nrDAO = new namirnicareceptaDAO();
    nrDAO.dajSveNamirnice(id).then((poruka) => {
        odgovor.send(JSON.stringify({"results" : poruka}));
    }).catch((error) => {
        console.error(error);
    });
}

function getRecepteNamirnice(zahtjev,odgovor){
    odgovor.type("application/json")
    let id = zahtjev.params.id
    let nrDAO = new namirnicareceptaDAO();
    nrDAO.dajSveRecepte(id).then((poruka) => {
        odgovor.send(JSON.stringify({"results" : poruka}));
    }).catch((error) => {
        console.error(error);
    });
}
