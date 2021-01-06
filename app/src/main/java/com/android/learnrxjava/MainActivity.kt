package com.android.learnrxjava

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.learnrxjava.utils.Logger
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    val tag = MainActivity::class.java.simpleName + "_log"

    //using Observable<T>.just to get observable from string data stream
    private lateinit var greetObservable: Observable<String>

    private lateinit var greetObserver: Observer<String>

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textview)

        greetObservable = Observable.just("hello from rxjava")

        greetObserver = object : Observer<String> {
            override fun onSubscribe(d: Disposable?) {
                Logger.logd(tag, "onsubscribe ${Thread.currentThread().name}")
            }

            override fun onNext(t: String?) {
                Logger.logd(tag, "onnext ${t} ${Thread.currentThread().name}")
                textView.text = t
            }

            override fun onError(e: Throwable?) {
                Logger.logd(tag, " onerror ${Thread.currentThread().name}")
            }

            override fun onComplete() {
                Logger.logd(tag, "oncomplete ${Thread.currentThread().name}")
            }

        }

        greetObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(greetObserver)

    }
}