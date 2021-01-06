package com.android.learnrxjava.utils

import android.util.Log

class Logger  {

    companion object{
        fun logd(TAG:String, message:String){
            Log.d(TAG,message)
        }
        fun loge(TAG:String, message:String){
            Log.e(TAG,message)
        }
        fun logv(TAG:String, message:String){
            Log.v(TAG,message)
        }
    }
}