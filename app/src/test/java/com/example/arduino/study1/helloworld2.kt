package com.example.arduino.study1

fun main(){
    val s = "100"
    val i = 100
    //print(s == i) 不同型別不能直接比較
    print(s == i.toString())
    //"100" == 100 經過 toString() 變成 "100" == "100"
    print(s.equals(i.toString()))
    print(s.toInt() == i)
}