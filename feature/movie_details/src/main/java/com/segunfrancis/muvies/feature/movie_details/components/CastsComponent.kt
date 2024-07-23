package com.segunfrancis.muvies.feature.movie_details.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.segunfrancis.muvies.common.CommonConstants.BASE_IMAGE_PATH
import com.segunfrancis.muvies.common.R
import com.segunfrancis.muvies.common.theme.MuviesTheme
import com.segunfrancis.muvies.feature.movie_details.dto.Cast
import com.segunfrancis.muvies.feature.movie_details.utils.creditsResponse

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CastsItem(
    id: Long,
    name: String,
    character: String,
    photoUrl: String,
    onCastClick: (Long) -> Unit
) {
    val context = LocalContext.current
    Card(
        onClick = { onCastClick(id) },
        modifier = Modifier.width(120.dp),
        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        elevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
        ) {
            AsyncImage(
                modifier = Modifier.height(190.dp),
                model = ImageRequest.Builder(context)
                    .data("${BASE_IMAGE_PATH}${photoUrl}")
                    .placeholder(R.drawable.poster_placeholder)
                    .error(R.drawable.poster_placeholder).build(),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = character,
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.body1
            )
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.person),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 4.dp)
                )
                Text(
                    text = name,
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}

@Composable
fun CastsComponent(casts: List<Cast?>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "CAST",
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Normal)
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(casts) {
                CastsItem(
                    id = it?.id?.toLong() ?: 0L,
                    character = it?.character.orEmpty(),
                    name = it?.name.orEmpty(),
                    photoUrl = it?.profilePath.orEmpty(),
                    onCastClick = {}
                )
            }
        }
    }
}

@Preview
@Composable
fun CastsComponentPreview() {
    MuviesTheme {
        CastsComponent(casts = creditsResponse.cast.orEmpty())
    }
}
