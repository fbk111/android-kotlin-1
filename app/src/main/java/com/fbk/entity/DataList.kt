package com.fbk.entity

data class DataList<T>(val total:Int, val row:List<T>, val code:Int, val msg:String)
