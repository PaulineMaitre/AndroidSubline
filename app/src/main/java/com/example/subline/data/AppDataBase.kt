package com.example.tripin.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.subline.data.FavorisDao
import com.example.subline.find.metros.Station


@Database(entities = arrayOf(Station::class), version = 1)

abstract class AppDatabase : RoomDatabase() {

    abstract fun getFavorisDao() : FavorisDao

}
