import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.toSize

@Composable
fun Modifier.shimmerEffect(
    enabled: Boolean = true,
    angled: Boolean = false,
    iterationDuration: Int = 1500,
): Modifier {
    if (!enabled) return this

    val shimmerColors = if (isSystemInDarkTheme()) {
        listOf(
            Color.DarkGray.copy(alpha = 0.5f),
            Color.Gray.copy(alpha = 0.2f),
            Color.DarkGray.copy(alpha = 0.5f)
        )
    } else {
        listOf(
            Color.LightGray.copy(alpha = 0.4f),
            Color.White.copy(alpha = 0.15f),
            Color.LightGray.copy(alpha = 0.4f)
        )
    }

    return composed {
        val transition = rememberInfiniteTransition()

        val translateAnim = transition.animateFloat(
            initialValue = -300f,
            targetValue = 1800f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = iterationDuration, easing = LinearEasing),
                repeatMode = RepeatMode.Restart,
            )
        )

        var size by remember { mutableStateOf(IntSize.Zero) }

        this
            .onGloballyPositioned { coordinates ->
                size = coordinates.size
            }
            .drawWithContent {
                drawContent()

                if (size.width > 0) {
                    val brush = if (angled) {
                        Brush.linearGradient(
                            colors = shimmerColors,
                            start = Offset(translateAnim.value - 200f, translateAnim.value / 4),
                            end = Offset(
                                translateAnim.value + 300f,
                                translateAnim.value + size.height.toFloat()
                            )
                        )
                    } else {
                        Brush.linearGradient(
                            colors = shimmerColors,
                            start = Offset(translateAnim.value - 200f, 0f),
                            end = Offset(translateAnim.value + 300f, 0f)
                        )
                    }

                    drawRect(
                        brush = brush,
                        size = size.toSize()
                    )
                }
            }
    }
}

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}