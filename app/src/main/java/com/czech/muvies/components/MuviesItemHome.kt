package com.czech.muvies.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.czech.muvies.R
import com.czech.muvies.models.Movies
import com.czech.muvies.theme.MuviesShape
import com.czech.muvies.theme.MuviesTypography
import com.czech.muvies.utils.AppConstants

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MuviesItemHome(
    modifier: Modifier = Modifier,
    onItemClick: (Movies.MoviesResult) -> Unit,
    movie: Movies.MoviesResult
) {
    Surface(onClick = { onItemClick(movie) }, modifier = modifier.clip(MuviesShape.large)) {
        Column(modifier = Modifier
            .fillMaxSize()
            .clipToBounds()) {
            AsyncImage(
                model = "${AppConstants.BASE_IMAGE_PATH}${movie.posterPath}",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.8F)
                    .clip(MuviesShape.large),
                placeholder = painterResource(id = R.drawable.poster_placeholder),
                error = painterResource(id = R.drawable.poster_error)
            )
            Text(
                text = movie.title,
                modifier = Modifier
                    .padding(8.dp)
                    .wrapContentHeight(),
                style = MuviesTypography.subtitle2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun MuviesItemMainPreview() {
    MuviesItemHome(
        onItemClick = {}, movie = movie, modifier = Modifier
            .width(197.dp)
            .height(287.dp)
    )
}

@Preview
@Composable
fun MuviesItemPreview() {
    MuviesItemHome(
        onItemClick = {}, movie = movie, modifier = Modifier
            .width(129.dp)
            .height(194.dp)
    )
}

val movie = Movies.MoviesResult(
    popularity = 5.4,
    voteCount = 296,
    video = true,
    posterPath = "",
    id = 1,
    adult = false,
    backdropPath = "",
    originalLanguage = "English",
    originalTitle = "Clash of the Titans",
    genreIds = listOf(),
    title = "Clash of the Titans",
    voteAverage = 6.7,
    overview = "",
    releaseDate = "12-8-2021",
    movieCategory = null
)
