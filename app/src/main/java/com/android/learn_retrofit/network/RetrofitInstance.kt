package com.android.learn_retrofit.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    var retrofit:Retrofit?=null
     var getCountryService: GetCountryService?=null

    fun getCountryServiceApi():GetCountryService?{
        if(retrofit == null){
            retrofit = Retrofit.Builder().baseUrl("https://restcountries.eu/")
                .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        getCountryService = retrofit?.create(GetCountryService::class.java)
        return getCountryService
    }
}