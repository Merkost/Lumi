package com.merkost.lumi.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import kotlin.math.roundToInt

val Brown = Color(0xFF795548)
val Red = Color(0xFFFF0000)
val Orange = Color(0xFFFF9800)
val Green = Color(0xFF4CAF50)

fun getColorByRating(rating: Double): Color {
    val normalizedRating = rating.coerceIn(0.0, 10.0).toFloat() / 10f
    return when {
        rating <= 4 -> lerp(Brown, Red, normalizedRating / 0.4f)
        rating <= 6 -> lerp(Red, Orange, (normalizedRating - 0.4f) / 0.2f)
        rating <= 8 -> lerp(Orange, Green, (normalizedRating - 0.6f) / 0.2f)
        else -> Green
    }
}

fun formatRating(rating: Double): String {
    return if (rating == rating.roundToInt().toDouble()) {
        rating.roundToInt().toString()
    } else {
        ((rating * 10.0).roundToInt() / 10.0).toString()
    }
}