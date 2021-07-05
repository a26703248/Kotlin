package com.example.arduino.study1

data class Person(val name:String, val age:Int)


fun main(){
    val p1 = Person("John", 18)
    val p2 = Person("Mary", 19)
    val p3 = Person("Helen", 20)
//    println(p1)
//    println(p2)
//    println(p3)
    // 請問年齡最大
    var list = mutableListOf<Person>()
    list.add(p1)
    list.add(p2)
    list.add(p3)
    val person = list.maxByOrNull {it.age}
    if(person != null){
        println(person.name)
    }
    println(person?.name)
    println(person!!.name)
    var avgAge = list.map { it.age }.average()
    println(avgAge)
    avgAge = list.stream().mapToInt{it.age}.average().asDouble
    println(avgAge)
    println("-----------------------------------")
    val summary = list.stream().mapToInt { it.age }.summaryStatistics()
    println("統計資訊: $summary")
    println("平均: ${summary.average}")
}