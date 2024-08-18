package com.merkost.lumi.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.merkost.lumi.R


@Composable
fun TransparentIconButton(
    imageVector: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier,
    tint: Color = Color.White,
    backgroundColor: Color = Color.Black.copy(alpha = 0.5f),
    onClick: () -> Unit
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = tint,
        modifier = modifier
            .padding(16.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(8.dp)
    )
}

@Composable
fun TransparentBackButton(
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = Color.White,
    backgroundColor: Color = Color.Black.copy(alpha = 0.5f)
) {
    TransparentIconButton(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = stringResource(R.string.back),
        tint = tint,
        backgroundColor = backgroundColor,
        onClick = onBackPress,
        modifier = modifier.windowInsetsPadding(WindowInsets.statusBars)
    )
}

@Preview
@Composable
fun TransparentIconButtonPreview() {
    TransparentIconButton(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = stringResource(R.string.back),
        onClick = {}
    )
}