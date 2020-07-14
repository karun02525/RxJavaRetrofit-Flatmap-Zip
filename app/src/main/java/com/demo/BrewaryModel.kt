package com.demo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Brewary")
data class BrewaryModel (

    @PrimaryKey
    @SerializedName("id") @ColumnInfo(name = "id") var id:Int,
    @SerializedName("name") @ColumnInfo(name = "name") var name:String,
    @SerializedName("brewery_type") @ColumnInfo(name = "brewery_type") var brewery_type:String

)