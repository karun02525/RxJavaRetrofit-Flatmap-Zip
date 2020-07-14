package com.demo.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demo.BrewaryModel
import io.reactivex.Flowable

@Dao
interface BrewaryDao {

    @Query("SELECT * FROM Brewary")
    fun getAllBrewaries() : Flowable<List<BrewaryModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(brewaryList: List<BrewaryModel>)

    @Query("DELETE FROM Brewary")
    fun deleteAll()
}