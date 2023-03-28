package com.fbk.utils

import android.content.ContentValues.TAG
import android.util.Log
import okhttp3.Call
import okhttp3.Response
import java.io.IOException
import javax.security.auth.callback.Callback

class HttpCallback(val method:(data:String)->Unit):okhttp3.Callback {
    override fun onFailure(p0: Call, p1: IOException) {
        Log.e(TAG, "onFailure: ${p1.message}", )
    }

    override fun onResponse(p0: Call, p1: Response) {
        if(p1.isSuccessful){
            method(p1.body()!!.string())
        }
    }

}