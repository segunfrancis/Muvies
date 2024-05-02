package com.czech.muvies.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.czech.muvies.R
import com.czech.muvies.models.Movies
import com.segunfrancis.muvies.common.theme.MuviesShape
import com.segunfrancis.muvies.common.theme.MuviesTypography
import com.czech.muvies.utils.AppConstants
import com.czech.muvies.utils.roundUp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MuviesItemAll(
    modifier: Modifier = Modifier,
    movie: Movies.MoviesResult,
    onItemClick: (Movies.MoviesResult) -> Unit = {}
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(MuviesShape.large),
        onClick = { onItemClick(movie) }
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = "${AppConstants.BASE_IMAGE_PATH}${movie.posterPath}",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(80.dp)
                    .height(120.dp)
                    .clip(MuviesShape.large),
                placeholder = painterResource(id = R.drawable.poster_placeholder),
                error = painterResource(id = R.drawable.poster_error)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                ItemText(text = movie.title)

                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_calendar_today_24),
                        contentDescription = null
                    )
                    ItemText(text = movie.releaseDate, style = MuviesTypography.subtitle2)
                }

                Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.star),
                        contentDescription = null
                    )
                    ItemText(
                        text = movie.voteAverage.roundUp().toString(),
                        style = MuviesTypography.subtitle2
                    )
                }
            }
        }
    }
}

@Composable
fun ItemText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MuviesTypography.subtitle1
) {
    Text(
        text = text,
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .wrapContentHeight(),
        style = style,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
@Preview
fun MuviesItemAllPreview() {
    MuviesItemAll(movie = movie)
}
