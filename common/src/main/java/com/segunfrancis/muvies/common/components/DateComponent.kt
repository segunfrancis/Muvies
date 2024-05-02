package com.segunfrancis.muvies.common.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DateComponent(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title.uppercase(),
        style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.SemiBold),
        modifier = modifier
            .border(
                width = 2.5.dp,
                color = MaterialTheme.colors.onSecondary.copy(alpha = 0.6F),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(6.dp)
    )
}

@Preview
@Composable
fun DateComponentPreview() {
    DateComponent("2024-04-19")
}

@Preview
@Composable
fun LanguageComponentPreview() {
    DateComponent("EN")
}
