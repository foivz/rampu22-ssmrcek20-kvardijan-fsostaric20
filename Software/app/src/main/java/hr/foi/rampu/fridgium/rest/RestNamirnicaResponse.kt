package hr.foi.rampu.fridgium.rest

import hr.foi.rampu.fridgium.entities.Namirnica

data class RestNamirnicaResponse(
    var results: ArrayList<Namirnica>
)
