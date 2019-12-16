package ru.skillbranch.kotlinexample.extensions

fun <T> List<T>.dropLastUntil(predicate: (T) -> Boolean): List<T> {
    val index = indexOf(findLast(predicate))
    return  if(index != -1)   subList(0, index) else this
}