package com.example.evidenta_masina

/**
 * Clasa corespunzatoare obiectului proprietar din baza de date
 */
data class Data_pr(
    val adresa: String,
    val cnp: String,
    val email: String,
    val idProprietar: Int,
    val nrTelefon: String,
    val nume: String){
    /**
     * construirea Stringului ce contine toate datele unui proprietar
     */
    override fun toString(): String {
        return "adr='$adresa', cnp='$cnp', email='$email', id=$idProprietar, nrTel='$nrTelefon', nume='$nume'\n"
    }
}