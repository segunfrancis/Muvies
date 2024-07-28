package com.segunfrancis.muvies.feature.movie_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.segunfrancis.muvies.feature.movie_details.dto.Season
import com.segunfrancis.muvies.feature.movie_details.utils.seasons

@Composable
fun SeasonsComponent(seasons: List<Season>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "SEASONS",
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Normal)
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(seasons) {
                SimilarMoviesItem(
                    imageUrl = it.posterPath.orEmpty(),
                    movieTitle = it.name,
                    onClick = {})
            }
        }
    }
}

@Preview
@Composable
fun SeasonComponentPreview() {
    SeasonsComponent(seasons)
}
