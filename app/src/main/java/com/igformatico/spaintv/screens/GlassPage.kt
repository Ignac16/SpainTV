package com.igformatico.spaintv.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.igformatico.spaintv.ui.theme.LocalAppleColors
import com.igformatico.spaintv.ui.theme.LocalAppleTypography

@Composable
fun GlassPage(title: String, modifier: Modifier = Modifier) {
    val colors = LocalAppleColors.current
    val typography = LocalAppleTypography.current
    val cardShape = RoundedCornerShape(28.dp)

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 28.dp),
        contentAlignment = Alignment.Center
    ) {
        // Back layer to fake glass blur depth
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 10.dp)
                .clip(cardShape)
                .background(colors.tabBarHighlight.copy(alpha = 0.22f))
        )

        // Foreground frosted card
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(cardShape)
                .border(1.dp, colors.tabBarBorder, cardShape)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colors.tabBarHighlight.copy(alpha = 0.7f),
                            colors.tabBarBackground.copy(alpha = 0.55f)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            BasicText(
                text = title,
                style = typography.pageTitle.copy(color = colors.textPrimary),
                modifier = Modifier.padding(horizontal = 16.dp),
                maxLines = 1
            )
        }
    }
}
