package com.example.arduino.study1

fun main(){
    val chinese: Int = 100
    val math: Int? = 90 //math 有可能是 null
    var sum = 0
    //計算總分 1 -------------------
    if(math != null){
        sum = chinese + math
        println("總分 ${sum}")
    }
    //計算總分 2 -------------------
    sum = chinese + (math?:0) // 若 math = null 計算時用 0 來表示
    println("總分 ${sum}")
    //計算總分 3 -------------------
    try{
        sum = chinese + math!!  // !! 表示 kotlin 不管 null 問題
        // 不管 math 如何我都要加上
        // 風險由工程師承擔
        // 通常會搭配 try-catch
        println("總分 ${sum}")
    }catch(e:Exception){
        print("錯誤訊息: ${e}")
    }

}