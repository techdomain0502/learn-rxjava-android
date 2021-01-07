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

    //using Observable<T>.just to get observable from string data stream
    private lateinit var greetObservable: Observable<Student>

    private lateinit var greetObserver: DisposableObserver<Student>

    private lateinit var textView: TextView

    private lateinit var compositeDisposable: CompositeDisposable

    private lateinit var studentArrayList: ArrayList<Student>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textview)

        addStudents()

        greetObservable = Observable.create {
            emitter-> studentArrayList.forEach { student->
            emitter.onNext(student)
            }

            //finish notify after stream is empty
            emitter.onComplete()

          // will cause fatal exception, so throw it wisely
        //    emitter.onError(throw Exception("dummy error"))
        }

        compositeDisposable = CompositeDisposable()

        greetObserver = object : DisposableObserver<Student>() {


            override fun onError(e: Throwable?) {
                Logger.logd(tag, "onError")
            }

            override fun onComplete() {
                Logger.logd(tag, "onComplete")
            }

            override fun onNext(t: Student) {
                Logger.logd(tag, "${t}")
            }

        }
        greetObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .concatMap {

                student -> student.name = student.name.toUpperCase()
                val student1 = Student("another student",22)
                Observable.just(student,student1)

            }

            .subscribeWith(greetObserver)


    }

    private fun addStudents() {
        studentArrayList = ArrayList()
        studentArrayList.add(Student("sachin", 34))
        studentArrayList.add(Student("erik", 32))
        studentArrayList.add(Student("ferado", 22))
    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}