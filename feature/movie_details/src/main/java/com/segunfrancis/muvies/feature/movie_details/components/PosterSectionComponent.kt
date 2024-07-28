package com.segunfrancis.muvies.feature.movie_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.segunfrancis.muvies.common.CommonConstants.BASE_IMAGE_PATH
import com.segunfrancis.muvies.common.R
import com.segunfrancis.muvies.common.components.DateComponent
import com.segunfrancis.muvies.common.components.RatingBar
import com.segunfrancis.muvies.common.formatNumber
import com.segunfrancis.muvies.common.roundUp
import com.segunfrancis.muvies.common.theme.Grey400
import com.segunfrancis.muvies.common.theme.Grey900
import com.segunfrancis.muvies.common.theme.MuviesTheme
import com.segunfrancis.muvies.common.theme.White

@Composable
fun MoviePosterSection(
    posterPath: String,
    title: String,
    releaseDate: String,
    language: String,
    rating: Double,
    voteCount: Long,
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
            val (image, imageOverlay, titleText, dateText, languageText, ratingBar, ratingText, totalVotesText) = createRefs()
            val verticalGuideline = createGuidelineFromStart(0.7F)
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data("${BASE_IMAGE_PATH}${posterPath}")
                    .placeholder(R.drawable.poster_placeholder)
                    .error(R.drawable.poster_error).build(),
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

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Transparent,
                            Grey900
                        ),
                        start = Offset(0F, 0F),
                        end = Offset(0F, 600.dp.value),
                    )
                )
                .constrainAs(imageOverlay) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                })

            Text(
                text = title,
                style = MaterialTheme.typography.h6.copy(color = White),
                modifier = Modifier.constrainAs(titleText) {
                    start.linkTo(dateText.start)
                    bottom.linkTo(dateText.top, 8.dp)
                    end.linkTo(verticalGuideline, 4.dp)
                    width = Dimension.fillToConstraints
                })

            DateComponent(title = releaseDate, modifier = Modifier.constrainAs(dateText) {
                start.linkTo(parent.start, 24.dp)
                bottom.linkTo(parent.bottom, 16.dp)
            })

            DateComponent(title = language, modifier = Modifier.constrainAs(languageText) {
                start.linkTo(dateText.end, 12.dp)
                bottom.linkTo(dateText.bottom)
            })

            RatingBar(rating = rating, modifier = Modifier.constrainAs(ratingBar) {
                bottom.linkTo(ratingText.top, 2.dp)
                start.linkTo(verticalGuideline)
                end.linkTo(parent.end)
            })

            Text(
                text = "${rating.roundUp()}/10.0",
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.ExtraLight),
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.constrainAs(ratingText) {
                    bottom.linkTo(totalVotesText.top, 2.dp)
                    end.linkTo(ratingBar.end)
                    start.linkTo(ratingBar.start)
                }
            )

            Text(
                text = String.format("${voteCount.formatNumber()} %s", "votes"),
                style = MaterialTheme.typography.body2.copy(
                    color = Grey400,
                    fontWeight = FontWeight.ExtraLight,
                    fontSize = 12.sp
                ),
                modifier = Modifier.constrainAs(totalVotesText) {
                    start.linkTo(ratingText.start)
                    end.linkTo(ratingText.end)
                    bottom.linkTo(parent.bottom, 16.dp)
                })
        }

        if (genres.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))

            GenreComponent(genres)
        }

        if (synopsis.isNotEmpty()) {

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
            voteCount = 2300,
            genres = listOf("Talk", "News", "Crime", "Suspense", "Sports", "Biography"),
            synopsis = "Following their explosive showdown, Godzilla and Kong must reunite against a colossal undiscovered threat hidden within"
        )
    }
}
