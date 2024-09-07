package com.mundocode.dragonballapp.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

// Set of Material typography styles to start with
val Typography = Typography(
//    bodyLarge = TextStyle(
//        fontFamily = FontFamily.SansSerif,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
//    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)


@Preview(showBackground = true)
@Composable
private fun TypographyPreview() {
    DragonBallAppTheme {
        Column {
            Text(text = "Display Large", style = Typography.displayLarge)
            Text(text = "Display Medium", style = Typography.displayMedium)
            Text(text = "Display Small", style = Typography.displaySmall)
            Text(text = "Headline Large", style = Typography.headlineLarge)
            Text(text = "Headline Medium", style = Typography.headlineMedium)
            Text(text = "Headline Small", style = Typography.headlineSmall)
            Text(text = "Title Large", style = Typography.titleLarge)
            Text(text = "Title Medium", style = Typography.titleMedium)
            Text(text = "Title Small", style = Typography.titleSmall)
            Text(text = "Body Large", style = Typography.bodyLarge)
            Text(text = "Body Medium", style = Typography.bodyMedium)
            Text(text = "Body Small", style = Typography.bodySmall)
            Text(text = "Label Large", style = Typography.labelLarge)
            Text(text = "Label Medium", style = Typography.labelMedium)
            Text(text = "Label Small", style = Typography.labelSmall)
        }
    }
}