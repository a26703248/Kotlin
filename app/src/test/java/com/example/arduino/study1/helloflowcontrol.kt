package com.example.arduino.study1
import kotlin.random.Random as r
fun main(){
    // if
    val scores = r.nextInt(101) // 0 ~ 100
    val pass = if(scores >= 60) "Pass" else "Fail"
    println("$scores  $pass")
    // 100~90 "A", 90~80 "B", 89~70 "C", 69~60 "D", 59~0 "E"
    // when (類似 Java, C/C++ 的 switch-case)
    when(scores){
        in 90..100 -> println("A")
        in 80..89 -> println("B")
        in 70..79 -> println("C")
        in 60..69 -> println("D")
        else -> println("E")
    }
    // when 給值
    val level = when(scores){
        in 90..100 -> "A"
        in 80..89 -> "B"
        in 70..79 -> "C"
        in 60..69 -> "D"
        else -> "E"
    }
    println(level)

    val level2 = when(scores/10){
        10, 9 -> "A"
        8 -> "B"
        7 -> "C"
        6 -> "D"
        else -> "E"
    }

    println(level2)
}