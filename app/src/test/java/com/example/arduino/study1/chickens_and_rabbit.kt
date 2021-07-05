package com.example.arduino.study1

fun main(){
    val foot = 240
    val total= 83
    printresult1(foot,total)
    printresult2(foot,total)
}
//方法一
fun printresult1(foot:Int, total:Int){
    var chickens = 0
    if(foot < total * 2){
        print("資料錯誤")
        return
    }
    if(foot > total * 4){
        print("資料錯誤")
        return
    }
    while( 2 *chickens+ 4 *(total-chickens)>foot){
        chickens+= 1
    }
    var rabbit= total - chickens
    println("雞:%d, 兔:%d".format(chickens,rabbit))
}
//方法二(假設所有都是雞)
fun printresult2(foot:Int, total:Int){
    if(foot < total * 2){
        print("資料錯誤")
        return
    }
    if(foot > total * 4){
        print("資料錯誤")
        return
    }
    var chickens = foot / 2
    var rabbit = chickens - total
    chickens = total - rabbit
    println("雞:%d, 兔:%d".format(chickens,rabbit))
}