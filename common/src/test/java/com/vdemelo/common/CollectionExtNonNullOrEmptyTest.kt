package com.vdemelo.common

import com.vdemelo.common.extensions.nonNullOrEmpty
import org.junit.Assert.assertEquals
import org.junit.Test

class CollectionExtNonNullOrEmptyTest {

    @Test
    fun nullListMustBecomeEmptyList() {
        val list: List<String?>? = null
        val expected = listOf<String>()
        val result = list.nonNullOrEmpty()
        assertEquals(expected, result)
    }

    @Test
    fun listWithNullsMustBecomeNullClean() {
        val list: List<String?> = listOf(
            null,
            "abc",
            null,
            null
        )
        val expected = listOf("abc")
        val result = list.nonNullOrEmpty()
        assertEquals(expected, result)
    }

    @Test
    fun listWithOnlyNullsMustBecomeEmptyList() {
        val list: List<String?> = listOf(
            null,
            null
        )
        val expected = listOf<String>()
        val result = list.nonNullOrEmpty()
        assertEquals(expected, result)
    }
}
