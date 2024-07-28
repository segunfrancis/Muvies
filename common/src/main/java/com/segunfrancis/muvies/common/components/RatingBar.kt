package com.segunfrancis.muvies.common.components

import androidx.annotation.FloatRange
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.segunfrancis.muvies.common.R
import com.segunfrancis.muvies.common.theme.MuviesTheme
import kotlin.math.round

@Composable
fun RatingBar(@FloatRange(from = 0.0, to = 10.0) rating: Double, modifier: Modifier = Modifier) {
    Row(modifier = modifier.wrapContentWidth()) {
        val firstDiv = rating.div(2)
        val firstReminder = firstDiv.getDecimalReminder()
        val firstDivOther = 5 - firstDiv
        val numbersToColor = firstDiv.toInt()
        repeat(numbersToColor) {
            Image(
                painter = painterResource(R.drawable.ic_full_star),
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        }
        if (firstReminder > 0.0) {
            when (firstReminder) {
                in 0.1..0.3 -> Image(
                    painter = painterResource(R.drawable.ic_30_star),
                    contentDescription = null, modifier = Modifier.size(18.dp)
                )

                in 0.4..0.6 -> Image(
                    painter = painterResource(R.drawable.ic_50_star),
                    contentDescription = null, modifier = Modifier.size(18.dp)
                )

                else -> Image(
                    painter = painterResource(R.drawable.ic_70_star),
                    contentDescription = null, modifier = Modifier.size(18.dp)
                )
            }
        }
        if (firstDivOther >= 1) {
            repeat(firstDivOther.toInt()) {
                Image(
                    painter = painterResource(R.drawable.ic_empty_star),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun RatingBarPreview() {
    MuviesTheme {
        RatingBar(rating = 8.114)
    }
}

private fun Double.getDecimalReminder(): Double {
    val fractionalPart = this - this.toInt()
    return round(fractionalPart * 10) / 10
}
