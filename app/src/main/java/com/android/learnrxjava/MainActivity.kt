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
    private lateinit var greetObservable: Observable<String>

    private lateinit var greetObserver: DisposableObserver<String>

    private lateinit var greetObserver2: DisposableObserver<String>

    private lateinit var textView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textview)

        greetObservable = Observable.just("hello from rxjava")


        greetObserver = object : DisposableObserver<String>() {

            override fun onNext(t: String?) {
                Logger.logd(tag, "greetobserver1 onnext ${t} ${Thread.currentThread().name}")
                textView.text = t
            }

            override fun onError(e: Throwable?) {
                Logger.logd(tag, "greetobserver1  onerror ${Thread.currentThread().name}")
            }

            override fun onComplete() {
                Logger.logd(tag, "greetobserver1 oncomplete ${Thread.currentThread().name}")
            }

        }

        greetObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(greetObserver)

        //add one more observer
        greetObserver2 = object : DisposableObserver<String>() {

            override fun onNext(t: String?) {
                Logger.logd(tag, "greetobserver2 onnext ${t} ${Thread.currentThread().name}")
                textView.text = t
            }

            override fun onError(e: Throwable?) {
                Logger.logd(tag, "greetobserver2  onerror ${Thread.currentThread().name}")
            }

            override fun onComplete() {
                Logger.logd(tag, "greetobserver2 oncomplete ${Thread.currentThread().name}")
            }

        }
     // lets subscribe the observer 2
        greetObservable
            .subscribe(greetObserver2)

    }


    override fun onDestroy() {
        super.onDestroy()
        greetObserver.dispose()
        greetObserver2.dispose()
    }
}