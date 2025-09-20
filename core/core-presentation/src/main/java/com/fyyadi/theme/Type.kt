package com.fyyadi.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.fyyadi.core_presentation.R

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val Amarant = FontFamily(
    Font(R.font.amaranth_regular, FontWeight.Normal),
    Font(R.font.amaranth_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.amaranth_bold, FontWeight.Bold),
    Font(R.font.amaranth_bolditalic, FontWeight.Bold, FontStyle.Italic)
)

val RethinkSans = FontFamily(
    Font(R.font.rethinksans_regular, FontWeight.Normal),
    Font(R.font.rethinksans_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.rethinksans_bold, FontWeight.Bold),
    Font(R.font.rethinksans_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.rethinksans_medium, FontWeight.Medium),
    Font(R.font.rethinksans_mediumitalic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.rethinksans_semibold, FontWeight.SemiBold),
    Font(R.font.rethinksans_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
)