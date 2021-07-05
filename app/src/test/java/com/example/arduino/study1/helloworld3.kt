package com.example.arduino.study1

import java.lang.StrictMath.round

fun main(){
    val r = 10.5
    var area = 0.0
    //圓面積
    area = r*r*Math.PI
    println(area)
    area = r*r*Math.PI
    println("%f".format(area))
    println("%.2f".format(area))
    println("半徑: %f, 圓面積: %.1f".format(r,area))
    println("半徑: $r, 圓面積: $area")
    //球體面積
    var volum = 0.0
    volum = (4.0/3.0)*(Math.PI*Math.pow(r,3.0))
    print("半逕: %.1f, 球體積: %.2f".format(r,volum))
}