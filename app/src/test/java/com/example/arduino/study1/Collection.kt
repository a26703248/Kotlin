package com.example.arduino.study1

fun main(){
    // 集合(高級陣列) : Set, Map, List
    // Set: 元素內容不會重複, ex[1, 3, 5, 7]
    // List: 元素內容可重複, ex[1, 3, 5, 7]
    // Map: 每個元素都是 key/value 的組合, ex :　["甲" : 100, "乙" : 90, "丙" : 90]
    //                                key["甲", "乙", "丙"], value[100, 90, 90]
    val set = setOf<Int>(1, 5, 2, 7, 6, 3)// setof可讀不可改刪
    //set.add(9)
    println(set) //[1, 5, 2, 7, 6, 3]
    val set2 = mutableSetOf<Int>(1, 5, 2, 7, 6, 3)// mutableSetOf可讀可改刪
    set2.add(9)
    println(set2)
    // 最大值
    val d = sortedSetOf<Int>(1, 5, 2, 7, 6, 3)
    println(d.last())
    println(set2.maxOrNull())
    // 有條件的最大值
    // 除以 3 餘數中的最大值
    // [10, 42, 5, 4] %3 [1, 0, 2, 1]
    val nums = setOf<Int>(10, 42, 5, 4)
    println(nums.maxByOrNull { n ->  n % 3})
    // 過濾資料
    val scores = setOf<Int>(80, 40, 50, 90)
    println(scores.filter { it >= 60 })
    println(scores.filter { it >= 60 }.maxOrNull())
    // 字串長度
    val name = "John"
    val len = name.length
    println(" $name 的長度 $len")
    println(" $name 的長度 ${name.length}")
    // Lab
    val names = setOf<String>("Helen", "John", "Jackson", "Anita")
    println(names.maxByOrNull {it.length})
    println(names.sortedBy { it.length }.last())
}