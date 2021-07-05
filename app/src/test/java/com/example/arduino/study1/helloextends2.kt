package com.example.arduino.study1

// 抽象類別
// 紅茶 10, 奶茶 25


abstract class Tea(val name:String,val price:Int){
    fun printTeaInfo(){
        println("$name, $price")
    }
    abstract fun printElement()
}

class RedTea(name:String, price:Int): Tea(name,price){
    override fun printElement() {
        println("紅茶 + 水")
    }
}

class MilkTea(name:String, price:Int): Tea(name,price){
    override fun printElement() {
        println("紅茶 + 牛奶 + 水")
    }
}

fun main(){
    val redTea = RedTea("錫蘭紅茶", 10)
    val milkTea= MilkTea("厚奶茶", 25)
    val milkGreenTea= MilkTea("奶綠茶", 30)
    val tea = listOf<Tea>(redTea,milkTea)
    tea.forEach{it.printElement()}
    tea.forEach{it.printTeaInfo()}
    tea.forEach{it.name}
    val stat = tea.stream().mapToInt{it.price}.summaryStatistics()
    println("均價: ${stat.average}, 總價: ${stat.sum}")
}