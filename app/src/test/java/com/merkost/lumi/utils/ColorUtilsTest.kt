package com.merkost.lumi.utils

import androidx.compose.ui.graphics.lerp
import org.junit.Assert.assertEquals
import org.junit.Test

class ColorUtilsTest {

    @Test
    fun `getColorByRating for low rating`() {
        val color = getColorByRating(2.0)
        val expectedColor = lerp(Brown, Red, 1/2f)
        assertEquals(expectedColor, color)
    }

    @Test
    fun `getColorByRating for mid rating`() {
        val color = getColorByRating(5.0)
        val expectedColor = lerp(Red, Orange, 0.5f)
        assertEquals(expectedColor, color)
    }

    @Test
    fun `getColorByRating for high rating`() {
        val color = getColorByRating(9.0)
        assertEquals(Green, color)
    }

    @Test
    fun `getColorByRating for exact 10 rating`() {
        val color = getColorByRating(10.0)
        assertEquals(Green, color)
    }
}