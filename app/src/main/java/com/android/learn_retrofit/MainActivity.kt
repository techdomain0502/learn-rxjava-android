package com.android.learn_retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.android.learn_retrofit.models.Country
import com.android.learn_retrofit.models.CountryItem
import com.android.learn_retrofit.network.RetrofitInstance
import io.reactivex.FlowableSubscriber
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.AsyncSubject
import org.reactivestreams.Subscription
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val TAG:String = "MainActivity_log"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val retrofitInstance = RetrofitInstance()
        val progressBar:ProgressBar = findViewById(R.id.progressbar)
        retrofitInstance.getCountryServiceApi()
            ?.run {
             getAllCountries()
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
            }?.flatMap {
               Log.d(TAG,"flat map called")
                val countryArray = it.toTypedArray()
                 Observable.fromArray(*countryArray)
                }
                ?.subscribe(object:Observer<CountryItem>{
                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG,"onsubscribe called")
                        progressBar.visibility = View.VISIBLE
                    }

                    override fun onNext(t: CountryItem) {
                        Log.d(TAG,"onnext called ${t}")
                    }

                    override fun onError(e: Throwable) {
                        TODO("Not yet implemented")
                    }

                    override fun onComplete() {
                        Log.d(TAG,"oncomplete")
                        progressBar.visibility = View.INVISIBLE
                    }

                })




    }
}