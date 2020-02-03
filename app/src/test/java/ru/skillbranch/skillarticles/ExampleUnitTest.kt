package ru.skillbranch.skillarticles

import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.skillarticles.extensions.indexesOf

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun check_indexesOf() {
        val content = "abcd222abcd222222abcdabcd"
        val expected = listOf<Int>(0,7,17,21)
        val actual = content.indexesOf("abcd")
        assertEquals(expected, actual)
    }
}
