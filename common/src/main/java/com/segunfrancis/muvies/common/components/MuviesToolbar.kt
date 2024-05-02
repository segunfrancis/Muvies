package com.segunfrancis.muvies.common.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun MuviesToolbar(
    title: String,
    showBackButton: Boolean = true,
    onBackClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showBackButton) {
            IconButton(onClick = { onBackClick?.invoke() }) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back button")
            }
        }
        val noBackButtonStartPadding =
            if (showBackButton) Modifier.padding(start = 0.dp) else Modifier.padding(start = 24.dp)
        ConstraintLayout(modifier = Modifier.fillMaxWidth().then(noBackButtonStartPadding)) {
            val (titleText, divider) = createRefs()
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(horizontal = 8.dp)
                    .constrainAs(titleText) {
                        start.linkTo(parent.start)
                        width = Dimension.fillToConstraints
                    },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Divider(
                modifier = Modifier
                    .width(70.dp)
                    .clip(RoundedCornerShape(50))
                    .constrainAs(divider) {
                        top.linkTo(titleText.bottom, 4.dp)
                        start.linkTo(titleText.start)
                        end.linkTo(titleText.end)
                    },
                color = MaterialTheme.colors.error,
                thickness = 3.dp
            )
        }
    }
}

@Preview
@Composable
fun MuviesToolbarPreview() {
    MuviesToolbar(title = "Rebel Moon - Part Two. The Scargiver", showBackButton = true, onBackClick = {})
}
