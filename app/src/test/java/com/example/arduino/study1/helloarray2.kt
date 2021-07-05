package com.example.arduino.study1

import kotlin.random.Random

fun main(){
    // 利用 lambda 來進行四星彩電腦選號
    // 四星彩 = 四個數字，每個數字是0~9，且可以重複
    // 例如: 1332
    // Random.nextInt(10) 表示 亂數產生 0~9
    val stars = Array(5) { _ -> Random.nextInt(10)}
    println(stars.contentToString())
    println(stars[0])
    println(stars.get(0))
    // 改變元素內容
    stars.set(0, 10)
    println(stars.contentToString())
}