package com.merkost.lumi.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class UtilsTest {

    @Test
    fun `formatMovieDuration returns hours and minutes`() {
        val result = formatMovieDuration(125)
        assertEquals("2 hr 5 min", result)
    }

    @Test
    fun `formatMovieDuration returns exactly 1 hour`() {
        val result = formatMovieDuration(60)
        assertEquals("1 hr", result)
    }

    @Test
    fun `formatMovieDuration returns exactly 2 hours`() {
        val result = formatMovieDuration(120)
        assertEquals("2 hr", result)
    }

    @Test
    fun `formatMovieDuration returns just minutes`() {
        val result = formatMovieDuration(45)
        assertEquals("45 min", result)
    }

    @Test
    fun `formatMovieDuration returns zero minutes`() {
        val result = formatMovieDuration(0)
        assertEquals("0 min", result)
    }
}