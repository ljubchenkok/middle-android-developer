package ru.skillbranch.skillarticles.extensions

fun String?.indexesOf(query: String): List<Int> {
    if (this.isNullOrEmpty() || query.isBlank()) return emptyList()
    val results = ArrayList<Int>()
    var curIndex = 0
    while (curIndex < this.length){
        val index = this.toLowerCase().indexOf(query.toLowerCase(), curIndex)
        if (index < 0 ) break
        results.add(index)
        curIndex = index + query.length
    }
    return results

}
