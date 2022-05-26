package com.example.evidenta_masina

import retrofit2.Call
import retrofit2.http.*

/**
 * interfata pentru apelurile catre baza de date
 */
interface ApiInterface {
    /**
     * metoda corespunzatoare apelului de returnare a tuturor proprietarilor
     */
    @GET("proprietar")
    fun getData_pr(): Call<List<Data_pr>>
    /**
     * metoda corespunzatoare apelului de inserare a unui act proprietar
     */
    @POST("proprietar")
    fun createPr(
        @Body post: Data_pr
    ): Call<Data_pr>
    /**
     * metoda corespunzatoare apelului de stergere a unui proprietar dupa id
     */
    @DELETE("proprietar/{id}")
    fun deletePr(
        @Path("id") idProprietar: Int
    ): Call<Unit>
    /**
     * metoda corespunzatoare apelului de inserare a unei masini
     */
    @POST("masina")
    fun createMa(
        @Body post: Data_ma
    ): Call<Data_ma>
    /**
     * metoda corespunzatoare apelului de stergere a unei masini dupa serie
     * */
    @DELETE("masina/{serie}")
    fun deleteMa(
        @Path("serie") serie: String
    ): Call<Unit>
    /**
     * metoda corespunzatoare apelului de inserare a unui act
     */
    @POST("act")
    fun createAct(
        @Body post: Data_act
    ): Call<Data_act>
    /**
     * metoda corespunzatoare apelului de stergere a unui act dupa id
     */
    @DELETE("act/{id}")
    fun deleteAct(
        @Path("id") idAct: Int
    ): Call<Unit>
    /**
     * metoda corespunzatoare apelului de returnare a tuturor masinilor
     */
    @GET("masina")
    fun getData_ma(): Call<List<Data_ma>>
    /**
     * metoda corespunzatoare apelului de returnare a tuturor actelor
     */
    @GET("act")
    fun getData_act(): Call<List<Data_act>>

}