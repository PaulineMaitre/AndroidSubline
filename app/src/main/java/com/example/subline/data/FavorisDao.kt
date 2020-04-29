package com.example.subline.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.subline.find.metros.Station

@Dao
interface FavorisDao {

    @Query("select * from favoris")
    suspend fun getStation() : List<Station>

    @Insert
    suspend fun addStation(station: Station)

    @Delete
    suspend fun deleteStation(station: Station)

    @Query("select * from favoris where name = :station_name AND direction_name =:direct AND type=:type")
    suspend fun getStation(station_name: String,direct : String,type : String) :Station

}