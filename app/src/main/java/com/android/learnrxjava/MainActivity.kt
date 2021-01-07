package com.android.learnrxjava

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.learnrxjava.model.Student
import com.android.learnrxjava.utils.Logger
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    val tag = MainActivity::class.java.simpleName + "_log"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val observable:Observable<Int> = Observable.range(0,20)
        val observer:Observer<List<Int>> = object:Observer<List<Int>>{
            override fun onSubscribe(d: Disposable?) {
            Logger.logd(tag,"onsubscribe called")
            }

            override fun onNext(t: List<Int>) {
                t.forEach {
                    Logger.logd(tag," $it")
                }
                Logger.logd(tag,"-------")
            }

            override fun onError(e: Throwable?) {
            }

            override fun onComplete() {
                Logger.logd(tag,"oncomplete called")
            }

        }

        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter {
                it%2==0
            }
            .buffer(2)
            .subscribe(observer)


    }

}