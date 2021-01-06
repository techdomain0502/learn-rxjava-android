package com.android.learnrxjava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.learnrxjava.utils.Logger
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.SchedulerSupport.IO
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

        val tag = MainActivity::class.java.simpleName

    //using Observable<T>.just to get observable from string data stream
    private lateinit var greetObservable:Observable<String>

    private lateinit var greetObserver:Observer<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        greetObservable  = Observable.just("hello rxjava")

        greetObserver = object:Observer<String>{
            override fun onSubscribe(d: Disposable?) {
                Logger.logd(tag,"onsubscribe ")
            }

            override fun onNext(t: String?) {
                Logger.logd(tag,"onnext ${t} ")
            }

            override fun onError(e: Throwable?) {
                Logger.logd(tag," onerror")
            }

            override fun onComplete() {
                Logger.logd(tag,"oncomplete")
            }

        }

        greetObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(greetObserver)

    }
}