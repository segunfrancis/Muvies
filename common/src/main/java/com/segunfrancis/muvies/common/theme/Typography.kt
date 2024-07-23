package com.segunfrancis.muvies.common.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.segunfrancis.muvies.common.R

private val Alice = FontFamily(
    Font(R.font.alice)
)

private val Convergence = FontFamily(
    Font(R.font.convergence)
)

private val Capriola = FontFamily(
    Font(R.font.capriola)
)

val MuviesTypography = Typography(
    h4 = TextStyle(
        fontFamily = Convergence,
        fontWeight = FontWeight.W600,
        fontSize = 30.sp
    ),
    h5 = TextStyle(
        fontFamily = Convergence,
        fontWeight = FontWeight.W600,
        fontSize = 18.sp
    ),
    h6 = TextStyle(
        fontFamily = Convergence,
        fontWeight = FontWeight.W900,
        fontSize = 20.sp,
        color = Grey400
    ),
    subtitle1 = TextStyle(
        fontFamily = Capriola,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = Capriola,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    body1 = TextStyle(
        fontFamily = Capriola,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = White
    ),
    body2 = TextStyle(
        fontFamily = Capriola,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        color = Grey500
    ),
    button = TextStyle(
        fontFamily = Convergence,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = Capriola,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = Grey700
    ),
    overline = TextStyle(
        fontFamily = Convergence,
        fontWeight = FontWeight.ExtraLight,
        fontSize = 14.sp,
        color = Grey500
    )
)
