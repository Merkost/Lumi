package com.merkost.lumi.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.merkost.lumi.R

@Composable
fun LoadingAnimation(modifier: Modifier) {
    LottieLoading(
        modifier = modifier.size(226.dp),
        resId = R.raw.popcorn_loading
    )
}