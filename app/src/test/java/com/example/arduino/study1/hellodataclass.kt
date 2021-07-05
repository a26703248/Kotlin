package com.example.arduino.study1

import java.lang.Exception

// 錢包
data class Wallet(val name:String,var amount:Int){
    //如何檢查近來得參數是否正確
    init {
        // case 1
        //amount = if(amount >= 0) amount else 0
        // case 2
        require(amount >= 0) // 鑑別函式
    }
}

fun main(){
    val w1 = Wallet("John", 100)
    val w2 = Wallet("Mary", -100)
    println(w1)
    try{
        println(w2)
    }catch (e:Exception){
        println("錢包錯誤: ${e}")
        e.printStackTrace()
    }
}