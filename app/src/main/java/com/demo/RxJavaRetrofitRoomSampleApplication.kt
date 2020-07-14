package com.demo
import android.app.Application
import androidx.room.Room
import com.demo.room.BrewaryDatabase

class RxJavaRetrofitRoomSampleApplication : Application() {

    companion object {
        var database: BrewaryDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        database =  Room.databaseBuilder(applicationContext, BrewaryDatabase::class.java, "brewary_db")
            .fallbackToDestructiveMigration().build()
    }
}