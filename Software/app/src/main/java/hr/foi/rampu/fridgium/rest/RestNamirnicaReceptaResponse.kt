package hr.foi.rampu.fridgium.rest

import hr.foi.rampu.fridgium.entities.NamirnicaRecepta

data class RestNamirnicaReceptaResponse(
    var results: ArrayList<NamirnicaRecepta>
)
