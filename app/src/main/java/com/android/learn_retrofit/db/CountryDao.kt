package com.android.learn_retrofit.db

import androidx.room.*
import io.reactivex.Observable

@Dao
interface CountryDao {
  @Query("Select * from Country")
  fun getAll():Observable<List<Country>>

  @Query("Select * from Country where country_name like :name")
  fun getCountryDetails(name:String):Country

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertCountryAll(countrylist:CountryList)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertCountry(country:Country)

  @Delete
  fun deleteCountry(country:Country)

}