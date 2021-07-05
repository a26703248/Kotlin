package com.example.arduino.study1

fun main(){
    val h = 170.0
    val w = 60.0
    var bmi = w/(Math.pow((h/100),2.0))
    println("%.2f".format(bmi))
}