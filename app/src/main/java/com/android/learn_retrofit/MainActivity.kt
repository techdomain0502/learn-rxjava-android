package com.android.learn_retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.learn_retrofit.db.CountryDao
import com.android.learn_retrofit.db.CountryList
import com.android.learn_retrofit.db.MyDatabase
import com.android.learn_retrofit.models.Country
import com.android.learn_retrofit.models.CountryItem
import com.android.learn_retrofit.network.RetrofitInstance
import io.reactivex.FlowableSubscriber
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.AsyncSubject
import kotlinx.coroutines.*
import org.reactivestreams.Subscription
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val TAG: String = "MainActivity_log"
    lateinit var db:RoomDatabase
    lateinit var countryDao: CountryDao
    lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        compositeDisposable = CompositeDisposable()
        db = Room.databaseBuilder(this,MyDatabase::class.java,"countrydB").build()
        countryDao = (db as MyDatabase).countryDao()

        val retrofitInstance = RetrofitInstance()
        val progressBar: ProgressBar = findViewById(R.id.progressbar)

        retrofitInstance.getCountryServiceApi()
            ?.run {
                getAllCountries()
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .map {
                            country->
                        Log.d(TAG, "flat map called")
                        //val countryArray = it.toTypedArray()
                       // Observable.fromArray(*countryArray)
                        //// com.android.learn_retrofit.db.Country(it.alpha2Code,it.name)
                        val list:com.android.learn_retrofit.db.CountryList = com.android.learn_retrofit.db.CountryList()
                         country.forEach {
                            list.add(com.android.learn_retrofit.db.Country(it.alpha2Code,it.name))
                         }
                         list
                    }
                               //.skip(249)
//                               .filter {
//                                   it.name.startsWith("U")
//                               }

                    .subscribe(object : Observer<com.android.learn_retrofit.db.CountryList> {
                        override fun onNext(t: com.android.learn_retrofit.db.CountryList) {
                            Log.d(TAG, "onnext called ${t}")
                            //val country = com.android.learn_retrofit.db.Country(t.alpha2Code,t.name)
                           // countryDao.insertCountry(country)
                            countryDao.insertCountryAll(t)
                        }

                        override fun onError(e: Throwable) {
                            TODO("Not yet implemented")
                        }

                        override fun onComplete() {
                            Log.d(TAG, "oncomplete")
                            progressBar.visibility = View.INVISIBLE
                        }

                        override fun onSubscribe(d: Disposable) {
                            compositeDisposable.add(d)
                            progressBar.visibility = View.VISIBLE
                        }

                    })
                }


        GlobalScope.launch {
          val ob = getCountries()

          ob.subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(object: Observer<List<com.android.learn_retrofit.db.Country>> {


                  override fun onNext(t: List<com.android.learn_retrofit.db.Country>) {
                      Log.d(TAG,"size = ${t.size}")
                  }

                  override fun onError(e: Throwable) {
                      Log.d(TAG,"onerror in coroutine")
                  }

                  override fun onComplete() {
                      Log.d(TAG,"oncomplete in coroutine")
                  }

                  override fun onSubscribe(d: Disposable) {
                      compositeDisposable.add(d)
                  }

              })

        }


    }

    suspend fun getCountries():Observable<List<com.android.learn_retrofit.db.Country>>{
            return withContext(Dispatchers.IO){
                return@withContext countryDao.getAll()
            }
    }

    override fun onStop() {
        super.onStop()

        compositeDisposable.clear()

    }

}