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

        val observable:Observable<Int> = Observable.just(1,2,3,4,5)
        val observer:Observer<Int> = object:Observer<Int>{
            override fun onSubscribe(d: Disposable?) {
            Logger.logd(tag,"onsubscribe called")
            }

            override fun onNext(t: Int) {
                    Logger.logd(tag," $t")
                }

            override fun onError(e: Throwable?) {
            }

            override fun onComplete() {
            }
        }

        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .skip(2)
            .subscribe(observer)

        }




    }

