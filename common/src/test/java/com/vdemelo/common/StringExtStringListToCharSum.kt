package com.vdemelo.common

import com.vdemelo.common.extensions.toCharSum
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class StringExtStringListToCharSum {

    @Test
    fun sameCharInputMustYieldSameOutput() {
        val input1 = listOf("a", "b", "c")
        val input2 = listOf("abc")
        val charSum1 = input1.toCharSum()
        val charSum2 = input2.toCharSum()
        assertEquals(charSum1, charSum2)
    }

    @Test
    fun differentCharInputMustYieldDifferentOutput() {
        val input1 = listOf("a", "c")
        val input2 = listOf("abc")
        val charSum1 = input1.toCharSum()
        val charSum2 = input2.toCharSum()
        assertNotEquals(charSum1, charSum2)
    }

    @Test
    fun checkCharIsSummedCorrectly() {
        val input = listOf("a", "b", "cd")
        val charSum = input.toCharSum()
        val expected = ('a'.code + 'b'.code + 'c'.code + 'd'.code).toLong()
        assertEquals(charSum, expected)
    }

    @Test
    fun emptyListMustYieldZero() {
        val input = listOf<String>()
        val charSum = input.toCharSum()
        val expected = 0L
        assertEquals(charSum, expected)
    }
}
