package com.igformatico.spaintv.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class AppleColors(
    val background: Color,
    val backgroundTop: Color,
    val backgroundBottom: Color,
    val tabBarBackground: Color,
    val tabBarHighlight: Color,
    val tabBarBorder: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val accent: Color
)

data class AppleTypography(
    val pageTitle: TextStyle,
    val tabLabel: TextStyle
)

private val SfPro = FontFamily.SansSerif

val LocalAppleColors = staticCompositionLocalOf {
    AppleColors(
        background = Color(0xFFF4F6FF),
        backgroundTop = Color(0xFFEAF1FF),
        backgroundBottom = Color(0xFFF9FAFF),
        tabBarBackground = Color(0x99FFFFFF),
        tabBarHighlight = Color(0x66FFFFFF),
        tabBarBorder = Color(0x40A9B8FF),
        textPrimary = Color(0xFF0E1020),
        textSecondary = Color(0xFF7C83A1),
        accent = Color(0xFF3E6BFF)
    )
}

val LocalAppleTypography = staticCompositionLocalOf {
    AppleTypography(
        pageTitle = TextStyle(
            fontFamily = SfPro,
            fontWeight = FontWeight.SemiBold,
            fontSize = 34.sp,
            lineHeight = 40.sp,
            letterSpacing = (-0.5f).sp
        ),
        tabLabel = TextStyle(
            fontFamily = SfPro,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 14.sp,
            letterSpacing = 0.2.sp
        )
    )
}

@Composable
fun AppleTheme(content: @Composable () -> Unit) {
    val colors = LocalAppleColors.current
    val typography = LocalAppleTypography.current
    CompositionLocalProvider(
        LocalAppleColors provides colors,
        LocalAppleTypography provides typography
    ) {
        content()
    }
}

