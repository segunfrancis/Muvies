package com.segunfrancis.muvies.feature.movie_details.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.segunfrancis.muvies.common.theme.Grey400
import com.segunfrancis.muvies.common.theme.Grey700
import com.segunfrancis.muvies.common.theme.MuviesTheme

@Composable
fun AboutComponent(
    originalTitle: String?,
    runtime: String?,
    status: String?,
    releaseDate: String?,
    voteCount: String?,
    tagLine: String?,
    homePage: String?
) {
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "ABOUT",
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Normal)
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (!originalTitle.isNullOrBlank()) {
            AboutItem(label = "Original Title:", value = originalTitle)
        }
        if (!runtime.isNullOrEmpty()) {
            AboutItem(label = "Runtime:", value = runtime)
        }
        if (!status.isNullOrEmpty()) {
            AboutItem(label = "Status:", value = status)
        }
        if (!releaseDate.isNullOrEmpty()) {
            AboutItem(label = "Release Date:", value = releaseDate)
        }
        if (!voteCount.isNullOrEmpty()) {
            AboutItem(label = "Vote Count:", value = voteCount)
        }
        if (!tagLine.isNullOrEmpty()) {
            AboutItem(label = "Tag Line:", value = tagLine)
        }
        if (!homePage.isNullOrEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "Home Page",
                    style = MaterialTheme.typography.overline,
                    modifier = Modifier.weight(0.35F),
                    color = Grey700
                )
                ClickableText(
                    text = AnnotatedString(
                        text = homePage,
                        spanStyle = SpanStyle(
                            textDecoration = TextDecoration.Underline,
                            color = Grey700.copy(alpha = 0.8F)
                        )
                    ),
                    style = MaterialTheme.typography.overline,
                    modifier = Modifier.weight(0.65F),
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(homePage))
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}

@Composable
fun AboutItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.overline,
            modifier = Modifier.weight(0.35F),
            color = Grey700
        )
        Text(
            text = value,
            style = MaterialTheme.typography.overline,
            modifier = Modifier.weight(0.65F),
            color = Grey400
        )
    }
}

@Preview
@Composable
fun AboutComponentPreview() {
    MuviesTheme {
        AboutComponent(
            originalTitle = "Inside Out 2",
            runtime = "97 minutes",
            status = "Released",
            releaseDate = "11-06-2024",
            voteCount = "2021 votes",
            tagLine = "Make room for new emotions.",
            homePage = "https://movies.disney.com/inside-out-2"
        )
    }
}
