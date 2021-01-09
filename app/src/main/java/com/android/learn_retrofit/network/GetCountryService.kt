package com.android.learn_retrofit.network

import com.android.learn_retrofit.models.Country
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface GetCountryService {

    @GET("rest/v2/all")
    fun getAllCountries(): Observable<Country>
}