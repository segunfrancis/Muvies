package com.segunfrancis.muvies.feature.movie_details.components

import androidx.compose.foundation.layout.Arrangement
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
import com.segunfrancis.muvies.common.components.GenreItem

@Composable
fun GenreComponent(genres: List<String>) {
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
            GenreItem(title = it)
        }
    }
}

@Preview
@Composable
fun GenreComponentPreview() {
    GenreComponent(listOf("Talk", "News", "Crime", "Suspense", "Sports", "Biography"))
}
