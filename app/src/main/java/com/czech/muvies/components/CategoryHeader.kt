package com.czech.muvies.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.czech.muvies.R
import com.czech.muvies.models.Movies.MoviesResult.MovieCategory
import com.czech.muvies.theme.Grey700
import com.czech.muvies.theme.MuviesTheme
import com.czech.muvies.theme.MuviesTypography
import com.czech.muvies.theme.Red600

@Composable
fun CategoryHeader(
    modifier: Modifier = Modifier,
    category: MovieCategory,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Text(
            text = category.formattedName.toUpperCase(Locale.current),
            modifier = Modifier.fillMaxWidth(.8F),
            style = MuviesTypography.h6.copy(fontWeight = FontWeight.ExtraBold),
            color = Grey700
        )
        Text(
            text = stringResource(id = R.string.text_see_all).toUpperCase(Locale.current),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
                .clickable { onClick() },
            style = MuviesTypography.subtitle2,
            color = Red600,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview()
fun CategoryHeaderPreview() {
    MuviesTheme {
        CategoryHeader(category = MovieCategory.UPCOMING)
    }
}

@Composable
@Preview()
fun CategoryHeaderDarkPreview() {
    MuviesTheme {
        CategoryHeader(category = MovieCategory.IN_THEATER)
    }
}
