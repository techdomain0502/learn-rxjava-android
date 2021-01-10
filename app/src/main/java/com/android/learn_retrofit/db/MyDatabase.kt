package com.android.learn_retrofit.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Country::class],version=1)
abstract class MyDatabase : RoomDatabase(){
    abstract fun countryDao():CountryDao
}