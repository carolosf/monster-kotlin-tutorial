package com.example.monstertutorial.createmonsterusecase

import org.junit.Test
import kotlin.test.assertEquals

class DemonstrationTest {
    @Test
    fun `demonstrate repeat`() {
        val forList = mutableListOf<Int>()
        for (i in 0..2) {
            forList.add(i)
        }

        val repeatList = mutableListOf<Int>()
        repeat(3) {
            repeatList.add(it)
        }

        assertEquals(forList, repeatList)
    }

    @Test
    fun `demonstrate map with strings`() {
        val list = listOf("a", "b", "c")

        val result = list.map { it.toUpperCase() }

        assertEquals(listOf("A", "B", "C"), result)
    }

    @Test
    fun `demonstrate map with int`() {
        val list = listOf(1, 2, 3)

        val result = list.map { it * 2 }

        assertEquals(listOf(2, 4, 6), result)
    }

    @Test
    fun `demonstrate forEachIndex`() {
        val list = listOf("a", "b", "c")

        val forEachList = mutableListOf<String>()
        list.forEachIndexed { index, s ->
            forEachList.add("$index : $s")
        }

        val mapList = list.mapIndexed { index, s ->
            "$index : $s"
        }

        assertEquals(listOf("0 : a", "1 : b", "2 : c"), forEachList)
        assertEquals(forEachList, mapList)
    }

    @Test
    fun `demonstrate let`() {
        // instead of if (x != null) {
        val x:String? = null

        x?.let {
            println(it)
        }
    }

    @Test
    fun `demonstrate apply`() {
        data class MutableMonster(var name: String, var age: Int)

        val mutableMonster : MutableMonster? = MutableMonster("Fred", 12)
        mutableMonster?.apply {
            age = 13
        }

        assertEquals(13, mutableMonster?.age)
    }
}