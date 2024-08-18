package com.merkost.lumi.utils

fun formatMovieDuration(runtime: Int): String {
    val hours = runtime / 60
    val minutes = runtime % 60
    return if (hours > 0) {
        "$hours hr${if (minutes > 0) " $minutes min" else ""}"
    } else {
        "$minutes min"
    }
}