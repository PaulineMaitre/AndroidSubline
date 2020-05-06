package com.example.subline.find.metros

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.subline.R

@Entity(tableName = "favoris")
data class Station (@PrimaryKey(autoGenerate = true) val id: Int,
                    val name: String,
                    val type: String,
                    val ligne_name: String,
                    val direction_name: String,
                    val way: String,
                    val picto_ligne: Int) {


    companion object { /*  all: même chose que :ListClient<>*/
        val all = (1..5).map{
            Station(it,"name$it", "type$it", "ligne_name$it","direct$it","A",  R.drawable.m1)
        }.toMutableList()

    }
}
