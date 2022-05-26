package com.example.evidenta_masina
/**
 * Clasa corespunzatoare obiectului masina din baza de date
 */
data class Data_ma(
    val serie: String,
    val idProprietar: Int,
    val marca: String,
    val model: String,
    val an_fabricatie : Int,
    val culoare: String,
    val motor : Int,
    val combustibil: String,

) {
    /**
     * construirea Stringului ce contine toate datele unei masini
     */
    override fun toString(): String {
        return "serie='$serie', id=$idProprietar, marca='$marca', model='$model', an=$an_fabricatie, culoare='$culoare', motor=$motor, combustibil='$combustibil'\n"
    }
}