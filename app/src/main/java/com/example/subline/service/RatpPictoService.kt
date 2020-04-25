package com.example.subline.service

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RatpPictoService {
    @GET("/api/records/1.0/search/?dataset=pictogrammes-des-lignes-de-metro-rer-tramway-bus-et-noctilien")
    suspend fun getPictoInfo(@Query("q") line: String) : GetPictoInfoResult

    @GET("/explore/dataset/pictogrammes-des-lignes-de-metro-rer-tramway-bus-et-noctilien/files/{id}/download/")
    fun getImage(@Path("id") id: String) : Call<ResponseBody>
}

data class GetPictoInfoResult(val records: List<Record> = emptyList())
data class Record(val fields: Field = Field())
data class Field(val noms_des_fichiers: Fich = Fich())
data class Fich(val format: String = "", val filename: String = "", val width: Int = 0, val id: String = "", val height: Int = 0, val thumbnail: Boolean = true)
