package com.example.arduino.study1
import kotlin.random.Random as r

fun main(){
    val computer = r.nextInt(1,11)
    var max = 10
    var min = 0
    var user = 0
    var round = 1
    println(computer)
//    while(true){
//        print("第 $round /5次請輸入$min ~ $max 的數字: ")
//        user = readLine()!!.toInt()
//        round++
//        if(round >= 5){
//            println("失敗")
//            break
//        }else if(user == computer){
//            println("Bingo")
//            break
//        }
//        if(user < computer && user < max){
//            min = user
//            println("請猜大一點")
//        }else if(user > computer && user > min){
//            max = user
//            println("請猜小一點")
//        }
//    }
    for(i in 1 until 5){
        print("第 $i /5次請輸入$min ~ $max 的數字: ")
        user = readLine()!!.toInt()
        if(user == computer){
            println("Bingo")
            break
        }
        if(user < computer && user < max){
            min = user
            println("請猜大一點")
        }else if(user > computer && user > min){
            max = user
            println("請猜小一點")
        }
    }
}
