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
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    val tag = MainActivity::class.java.simpleName + "_log"

    //using Observable<T>.just to get observable from string data stream
    private lateinit var greetObservable: Observable<String>

    private lateinit var greetObserver: Observer<String>

    private lateinit var greetObserver2: Observer<String>

    private lateinit var textView: TextView

    private var disposable: Disposable? = null

    private lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textview)

        greetObservable = Observable.just("hello from rxjava")

        compositeDisposable = CompositeDisposable()

        greetObserver = object : Observer<String> {
            override fun onSubscribe(d: Disposable?) {
                Logger.logd(tag, "greetobservable1 onsubscribe ${Thread.currentThread().name}")
                disposable = d
                compositeDisposable.add(d)
            }

            override fun onNext(t: String?) {
                Logger.logd(tag, "greetobservable1 onnext ${t} ${Thread.currentThread().name}")
                textView.text = t
            }

            override fun onError(e: Throwable?) {
                Logger.logd(tag, "greetobservable1  onerror ${Thread.currentThread().name}")
            }

            override fun onComplete() {
                Logger.logd(tag, "greetobservable1 oncomplete ${Thread.currentThread().name}")
            }

        }

        greetObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(greetObserver)

        //add one more observer
        greetObserver2 = object : Observer<String> {
            override fun onSubscribe(d: Disposable?) {
                Logger.logd(tag, "greetobservable2 onsubscribe ${Thread.currentThread().name}")
                disposable = d
                compositeDisposable.add(d)
            }

            override fun onNext(t: String?) {
                Logger.logd(tag, "greetobservable2 onnext ${t} ${Thread.currentThread().name}")
                textView.text = t
            }

            override fun onError(e: Throwable?) {
                Logger.logd(tag, "greetobservable2  onerror ${Thread.currentThread().name}")
            }

            override fun onComplete() {
                Logger.logd(tag, "greetobservable2 oncomplete ${Thread.currentThread().name}")
            }

        }
     // lets subscribe the observer 2
        greetObservable
            .subscribe(greetObserver2)

        Toast.makeText(this,"current observers registered = ${compositeDisposable.size()}",Toast.LENGTH_SHORT).show()
    }


    override fun onDestroy() {
        super.onDestroy()
        //lets clean up the subscription made above by observer1
        disposable?.run {
            dispose()
        }

        Logger.logd(tag,"composite disposable size before disposition= ${compositeDisposable.size()}")

        //collective disposition for all observers
        compositeDisposable.run {
            dispose()
        }
        Toast.makeText(this,"current observers registered = ${compositeDisposable.size()}",Toast.LENGTH_SHORT).show()
        Logger.logd(tag,"composite disposable size after disposition= ${compositeDisposable.size()}")
    }
}