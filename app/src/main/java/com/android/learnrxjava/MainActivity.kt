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
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

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

    fun getObserver_3():Observer<String>{
        return object:Observer<String>{
            override fun onSubscribe(d: Disposable?) {
                Logger.logd(tag,"observer 3 onsubscribe")
            }

            override fun onNext(t: String?) {
                Logger.logd(tag,"observer 3 onnext ${t}")
            }

            override fun onError(e: Throwable?) {
                Logger.logd(tag,"observer 3 onerror")
            }

            override fun onComplete() {
                Logger.logd(tag,"observer 3 oncomplete")
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

        /*val asyncSubject:AsyncSubject<String> = AsyncSubject.create()
        getObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(asyncSubject)

        asyncSubject.subscribe(observer1)
        asyncSubject.onNext("a")
        asyncSubject.onNext("b")
        asyncSubject.onComplete()
//        asyncSubject.onNext("c")
        // it will receive b even complete is invoked above
        asyncSubject.subscribe(observer2)
*/
/*
        val behaviorSubject = BehaviorSubject.create<String>()
        getObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(behaviorSubject)

        //observer 1 gets most recent ie. a and thereafter b, c
        behaviorSubject.onNext("a")
        behaviorSubject.subscribe(getObserver_1())
        behaviorSubject.onNext("b")
        behaviorSubject.onNext("c")

        //observer 2 gets most recent ie. c and thereafter if any
        behaviorSubject.subscribe(getObserver_2())

        //subject completed emitting
        behaviorSubject.onComplete()

        //observer 3 couldnt get any as subject is completed above
        behaviorSubject.subscribe(getObserver_3())

*/

        val publishSubject = PublishSubject.create<String>()
        getObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(publishSubject)

        //observer 1 gets  b, c , d
        publishSubject.onNext("a")
        publishSubject.subscribe(getObserver_1())
        publishSubject.onNext("b")
        publishSubject.onNext("c")

        //observer 2 gets d
        publishSubject.subscribe(getObserver_2())
        publishSubject.onNext("d")
        //subject completed emitting
        publishSubject.onComplete()

        //observer 3 couldnt get any as subject is completed above
        publishSubject.subscribe(getObserver_3())

    }

    }

