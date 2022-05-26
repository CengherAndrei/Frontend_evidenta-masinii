package com.example.evidenta_masina
/**
 * Clasa corespunzatoare obiectului act din baza de date
 */
data class Data_act(
    val tipAct: String,
    val serie: String,
    val valabil_de_la: String,
    val valabil_pana_la: String,
    val pret: Double,


) {
    /**
     * construirea Stringului ce contine toate datele unui act
     */
    override fun toString(): String {
        return "tipAct='$tipAct', serie='$serie', valabil_de_la='$valabil_de_la', valabil_pana_la='$valabil_pana_la', pret=$pret\n"
    }
}