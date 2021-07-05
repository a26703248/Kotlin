package com.example.arduino.study1

fun main(){
    //數組陣列
    val num1 = arrayOf(1, 5, 7, 3) // 隱式(無規定型別)陣列
    val num2 = arrayOf<Int>(1, 5, 7, 3) // 顯式(有規定型別)陣列
    // for-loop
    for(i in 0..num1.size-1){
        println(num1[i])
    }
    for(i in num1.indices){ // index = size - 1
        println(num1[i])
    }
    for(i in num2){
        println(i)
    }
    // 直接印出陣列內容
    println(num1.contentToString())
    println(num2.contentToString())
    // lambda 語法創建陣列
    val num3 = Array(5, {i -> i * 2})// 從零開始等差陣列
    println(num3.contentToString())
    val num4 = Array(5) { i -> i * 2 }// 從零開始等差陣列
    println(num4.contentToString())
    // lambda 語法印出陣列
    num4.forEach { println(it) }
    num4.forEach {n -> println(n) }
}