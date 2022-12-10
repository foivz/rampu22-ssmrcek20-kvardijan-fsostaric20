package hr.foi.rampu.fridgium.rest

import hr.foi.rampu.fridgium.entities.MjernaJedinica

data class RestMJedinicaResponse(
    var results: ArrayList<MjernaJedinica>
)