package com.android.learnrxjava

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.learnrxjava.utils.Logger
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    val tag = MainActivity::class.java.simpleName + "_log"

    //using Observable<T>.just to get observable from string data stream
    private lateinit var greetObservable: Observable<Int>

    private lateinit var greetObserver: DisposableObserver<Int>

    private lateinit var textView: TextView

    private lateinit var compositeDisposable: CompositeDisposable

    private val dataArray = arrayOf("1","2")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textview)

        greetObservable = Observable.range(0,10)

        compositeDisposable = CompositeDisposable()

        greetObserver = object:DisposableObserver<Int>(){


            override fun onError(e: Throwable?) {
                Logger.logd(tag,"onError")
            }

            override fun onComplete() {
                Logger.logd(tag,"onComplete")
            }

            override fun onNext(t: Int) {
                Logger.logd(tag,"${t}")
            }

        }
        greetObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(greetObserver)



    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}