package com.example.arduino.study1

abstract class Human(val sex:String){
    fun printMyName(){
        println("My Sex: $")
    }
    abstract fun eat()
}

class Man(sex:String): Human(sex){

    override fun eat(){
        println("$sex 大口吃飯")
    }
}

class Woman(sex:String): Human(sex){
    override fun eat(){
        println("$sex 小口吃飯")
    }
}
//fun main(){
//    val m = Man("男人")
//    val wm = Woman("女人")
//    val list = listOf<Human>(m,wm)
//    m.printMyName()
//    wm.printMyName()
//}

data class Student(val name:String, val score:Int, val human: Human)

fun main(){
    val m = Man("男人")
    val wm = Woman("女人")
    val s1  = Student("John",80, m)
    println(s1)
    println(s1.name)
    println(s1.score)
    s1.human.eat()
    println(s1.human.sex)
}