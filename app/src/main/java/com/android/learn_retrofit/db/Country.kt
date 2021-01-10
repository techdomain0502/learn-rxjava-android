package com.android.learn_retrofit.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country",primaryKeys = ["country_code", "country_name"])
data class Country(
    @ColumnInfo(name = "country_code")
    val countryCode: String = "",
    @ColumnInfo(name = "country_name")
    val countryName: String = "",
    @ColumnInfo(name="_id")
    var id: Int = 0,
)


  class CountryList(): ArrayList<Country>()