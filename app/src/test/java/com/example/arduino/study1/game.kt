package com.example.arduino.study1

fun main(){
    print("請輸入數字:")
    val guess = readLine()!!.toInt()
    println("你猜得數字: $guess")
}