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
import io.reactivex.rxjava3.subjects.AsyncSubject

class MainActivity : AppCompatActivity() {

    val tag = MainActivity::class.java.simpleName + "_log"

    fun getObserver_1():Observer<String>{
         return object:Observer<String>{
             override fun onSubscribe(d: Disposable?) {
                 Logger.logd(tag,"observer 1 onsubscribe")
             }

             override fun onNext(t: String?) {
                 Logger.logd(tag,"observer 1 onnext ${t}")
             }

             override fun onError(e: Throwable?) {
                 Logger.logd(tag,"observer 1 onerror")
             }

             override fun onComplete() {
                 Logger.logd(tag,"observer 1 oncomplete")
             }

         }
    }

    fun getObserver_2():Observer<String>{
      return object:Observer<String>{
          override fun onSubscribe(d: Disposable?) {
              Logger.logd(tag,"observer 2 onsubscribe")
          }

          override fun onNext(t: String?) {
              Logger.logd(tag,"observer 2 onnext ${t}")
          }

          override fun onError(e: Throwable?) {
              Logger.logd(tag,"observer 2 onerror")
          }

          override fun onComplete() {
              Logger.logd(tag,"observer 2 oncomplete")
          }

      }
    }

    fun getObservable():Observable<String>{
        var arr = arrayOf("a","b","c")
        return Observable.fromArray(*arr)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val observer1 = getObserver_1()
        val observer2 = getObserver_2()

        val asyncSubject:AsyncSubject<String> = AsyncSubject.create()
        getObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(asyncSubject)
        asyncSubject.subscribe(observer1)
        asyncSubject.subscribe(observer2)

        }

    }

