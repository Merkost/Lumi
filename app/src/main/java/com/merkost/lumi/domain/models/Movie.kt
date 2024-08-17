package com.merkost.lumi.domain.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import com.merkost.lumi.ui.theme.Brown
import com.merkost.lumi.ui.theme.Green
import com.merkost.lumi.ui.theme.Orange
import com.merkost.lumi.ui.theme.Red
import kotlin.math.roundToInt

data class Movie(
    val id: Int,
    val title: String,
    val averageRating: Double,
    val image: Image?
) {
    val ratingColor: Color
        get() {
            val maxRating = 10.0

            val normalizedRating = (averageRating / maxRating).toFloat()

            return when {
                averageRating <= 4 -> lerp(Brown, Red, normalizedRating / 4f)
                averageRating <= 6 -> lerp(Red, Orange, (normalizedRating - 0.4f) / 0.2f)
                averageRating <= 8 -> lerp(Orange, Green, (normalizedRating - 0.6f) / 0.2f)
                else -> Green
            }
        }

    val ratingText: String
        get() = ((averageRating * 10.0).roundToInt() / 10.0).toString()
}