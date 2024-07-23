package com.segunfrancis.muvies.feature.movie_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.segunfrancis.muvies.common.CommonConstants.BASE_IMAGE_PATH
import com.segunfrancis.muvies.common.R
import com.segunfrancis.muvies.common.components.DateComponent
import com.segunfrancis.muvies.common.components.GenreComponent
import com.segunfrancis.muvies.common.components.RatingBar
import com.segunfrancis.muvies.common.theme.MuviesTheme
import com.segunfrancis.muvies.common.theme.White

@Composable
fun MoviePosterSection(
    posterPath: String,
    title: String,
    releaseDate: String,
    language: String,
    rating: Double,
    genres: List<String>,
    synopsis: String
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (image, titleText, dateText, languageText, ratingBar, ratingText) = createRefs()
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data("${BASE_IMAGE_PATH}${posterPath}")
                    .placeholder(R.drawable.poster_placeholder)
                    .error(R.drawable.poster_placeholder).build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .constrainAs(image) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    },
                contentScale = ContentScale.Crop
            )

            Text(
                text = title,
                style = MaterialTheme.typography.h6.copy(color = White),
                modifier = Modifier.constrainAs(titleText) {
                    start.linkTo(dateText.start)
                    bottom.linkTo(dateText.top, 16.dp)
                })

            DateComponent(title = releaseDate, modifier = Modifier.constrainAs(dateText) {
                start.linkTo(parent.start, 24.dp)
                bottom.linkTo(ratingText.top, 16.dp)
            })

            DateComponent(title = language, modifier = Modifier.constrainAs(languageText) {
                start.linkTo(dateText.end, 12.dp)
                top.linkTo(dateText.top)
            })
            
            RatingBar(rating = rating, modifier = Modifier.constrainAs(ratingBar) {
                bottom.linkTo(parent.bottom, 16.dp)
                start.linkTo(parent.start, 24.dp)
            })

            Text(
                text = "$rating/10.0",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.constrainAs(ratingText) {
                    bottom.linkTo(ratingBar.bottom)
                    top.linkTo(ratingBar.top)
                    start.linkTo(ratingBar.end, 8.dp)
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "GENRES",
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Normal)
        )

        Spacer(Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(genres) {
                GenreComponent(title = it)
            }
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = "SYNOPSIS",
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Normal)
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = synopsis,
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.overline
        )
    }
}

@Preview
@Composable
fun MoviePosterSectionPreview() {
    MuviesTheme {
        MoviePosterSection(
            posterPath = "/aA25JrHXj8ZPTJYj2iSIueyb34C.jpg",
            title = "Match of the Day",
            releaseDate = "1964-08-22",
            language = "en",
            rating = 7.3,
            genres = listOf("Talk", "News", "Crime", "Suspense", "Sports", "Biography"),
            synopsis = "Following their explosive showdown, Godzilla and Kong must reunite against a colossal undiscovered threat hidden within"
        )
    }
}
