package com.demo.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.demo.BrewaryModel

@Database(entities = [(BrewaryModel::class)], version = 1)
abstract class BrewaryDatabase : RoomDatabase() {

    abstract fun getBrewaryDao() : BrewaryDao
}