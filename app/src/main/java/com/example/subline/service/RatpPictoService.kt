package com.example.subline.service

import android.widget.ImageView
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RatpPictoService {
    @GET("/api/records/1.0/search/?dataset=pictogrammes-des-lignes-de-metro-rer-tramway-bus-et-noctilien")
    suspend fun getPictoInfo(@Query("q") line: String) : GetPictoInfoResult

    @GET("/explore/dataset/pictogrammes-des-lignes-de-metro-rer-tramway-bus-et-noctilien/files/ca0cfc318e6cbddf2ce693ea2f82fb54/download/")
    //suspend fun getImage(@Path("id") type: String) : String //GetPictoResult
    suspend fun getImage() : ImageView //GetPictoResult
}

data class GetPictoInfoResult(val records: List<Record> = emptyList())
data class Record(val fields: Field = Field())
data class Field(val noms_des_fichiers: Fich = Fich())
data class Fich(val format: String = "", val filename: String = "", val width: Int = 0, val id: String = "", val height: Int = 0, val thumbnail: Boolean = true)

//data class GetPictoResult()