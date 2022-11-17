package hr.foi.rampu.fridgium.rest

import hr.foi.rampu.fridgium.entities.Recept

data class RestReceptResponse(
    var results: ArrayList<Recept>
)