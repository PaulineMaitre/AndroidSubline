package com.example.subline.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.subline.find.Station


@Database(entities = arrayOf(Station::class), version = 1)

abstract class AppDatabase : RoomDatabase() {

    abstract fun getFavorisDao() : FavorisDao
}
