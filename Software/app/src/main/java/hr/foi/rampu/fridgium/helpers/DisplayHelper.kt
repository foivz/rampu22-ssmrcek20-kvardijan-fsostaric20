package hr.foi.rampu.fridgium.helpers

import kotlin.math.ceil
import kotlin.math.floor

class DisplayHelper {

    fun provjeriBroj(brojFloat: Float): Boolean{
        if (ceil(brojFloat) == floor(brojFloat)) return true //true cijeli
        return false //false nije cijeli
    }

    fun dajBroj(brojFloat: Float): Int{
        return brojFloat.toInt() //daje int
    }
}