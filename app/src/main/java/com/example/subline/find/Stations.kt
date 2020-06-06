package com.example.subline.find

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoris")
data class Station (@PrimaryKey(autoGenerate = true) val id: Int,
                    val name: String,
                    val type: String,
                    val ligne_name: String,
                    val direction_name: String,
                    val way: String,
                    val picto_ligne: Int,
                    val latitude : Double,
                    val longitude : Double) {

}
