package com.fbk.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import okhttp3.OkHttpClient
import android.os.Handler
import android.util.Log
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

class Request:Application() {
    override fun onCreate() {
        super.onCreate()
        handler=Handler(mainLooper)
        config=getSharedPreferences("data",Context.MODE_PRIVATE)

    }

    companion object{
        lateinit var handler:Handler
        lateinit var config:SharedPreferences
        val gson:Gson by lazy { Gson() }
        private const val baseUrl=""
        private  val client:OkHttpClient=OkHttpClient().newBuilder().build()

        @SuppressLint("CheckResult")
        fun mGlide(address:String, imageView: ImageView, corners:Int=8){
            Glide.with(imageView)
                .load(address)
                .apply(RequestOptions().transform(CenterCrop(),RoundedCorners(corners)))
        }

        fun <T> getData(address:String,clazz:Class<T>,method:(data:T)->Unit){
             val request=okhttp3.Request.Builder()
                 .url(baseUrl+address)
                 .addHeader("Authorization", config.getString("token","").toString())
                 .build();
            client.newCall(request).enqueue(HttpCallback {
                val data = gson.fromJson(it, clazz)
                data?.let{
                    handler.post{
                        method(it)
                    }
                }
            })
        }


        class HttpCallback(val method:(data:String)->Unit):okhttp3.Callback {
            override fun onFailure(p0: Call, p1: IOException) {
                Log.e(ContentValues.TAG, "onFailure: ${p1.message}", )
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.isSuccessful){
                    method(response.body()!!.string())
                }
            }

        }}
}