package com.segunfrancis.all_movies.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun ItemsPlaceholder(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.Asset("loader_placeholder.json"))
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(all = 8.dp)
    ) {
        LottieAnimation(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
    }
}

@Composable
@Preview
fun ItemsPlaceholderPreview() {
    ItemsPlaceholder()
}