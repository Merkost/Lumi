import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.merkost.lumi.R
import com.merkost.lumi.domain.models.MovieDetails
import com.merkost.lumi.presentation.components.IconedText
import com.merkost.lumi.presentation.components.MovieImage
import com.merkost.lumi.presentation.components.ScreenStateHandler
import com.merkost.lumi.presentation.components.TransparentBackButton
import com.merkost.lumi.presentation.viewmodels.MovieDetailsViewModel
import com.merkost.lumi.utils.formatMovieDuration
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.time.LocalDate

@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    movieId: Int,
    viewModel: MovieDetailsViewModel = koinViewModel(parameters = { parametersOf(movieId) }),
    onBackPress: () -> Unit
) {
    val screenState by viewModel.screenState.collectAsState()

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        contentWindowInsets = WindowInsets(0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            TransparentBackButton(
                onBackPress = onBackPress,
                modifier = Modifier
                    .zIndex(2f)
                    .align(Alignment.TopStart)
            )

            ScreenStateHandler(
                screenState = screenState,
                onRetry = { viewModel.retryLoadingMovieDetails() },
                successContent = { movie ->
                    MovieDetailsContent(
                        modifier = Modifier,
                        movie = movie,
                    )
                }
            )
        }
    }
}

@Composable
fun MovieDetailsContent(
    modifier: Modifier = Modifier,
    movie: MovieDetails
) {
    Box(modifier = modifier.fillMaxSize()) {

        MovieImage(
            imageUrl = movie.backdropImage?.medium,
            movieTitle = movie.title ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 350.dp)
                .height(100.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, MaterialTheme.colorScheme.background),
                        startY = 100f
                    )
                )
        )

        Surface(
            modifier = Modifier
                .padding(top = 450.dp)
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .clip(MaterialTheme.shapes.large)
                .verticalScroll(rememberScrollState()),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MovieTitle(title = movie.title)

                MovieDetailsRow(
                    runtime = movie.runtime,
                    releaseDate = movie.releaseDate
                )

                movie.overview?.let {
                    HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
                    MovieOverview(
                        modifier = Modifier,
                        overview = movie.overview
                    )
                }
            }
        }
    }
}

@Composable
private fun MovieTitle(title: String?) {
    Text(
        text = title ?: stringResource(id = R.string.unknown_title),
        style = MaterialTheme.typography.titleLarge.copy(
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        ),
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun MovieDetailsRow(runtime: Int?, releaseDate: LocalDate?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        runtime?.let {
            IconedText(
                icon = Icons.Default.AccessTime,
                text = formatMovieDuration(it)
            )
        }

        releaseDate?.let {
            IconedText(
                icon = Icons.Default.Event,
                text = it.year.toString()
            )
        }
    }
}

@Composable
private fun MovieOverview(modifier: Modifier = Modifier, overview: String) {
    var isExpanded by remember { mutableStateOf(false) }
    var showExpandButton by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
            text = overview,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface
            ),
            maxLines = if (isExpanded) Int.MAX_VALUE else 3,
            onTextLayout = { layoutResult ->
                if (layoutResult.hasVisualOverflow && !showExpandButton) {
                    showExpandButton = true
                }
            },
            overflow = TextOverflow.Ellipsis
        )

        if (showExpandButton) Text(
            text = if (isExpanded) stringResource(R.string.show_less) else stringResource(R.string.show_more),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp
            ),
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable { isExpanded = !isExpanded }

        )
    }
}
