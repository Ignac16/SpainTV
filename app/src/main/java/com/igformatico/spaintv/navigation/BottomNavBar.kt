package com.igformatico.spaintv.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Icon
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.igformatico.spaintv.ui.theme.LocalAppleColors
import com.igformatico.spaintv.ui.theme.LocalAppleTypography
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Brush

@Composable
fun BottomNavBar(
    currentDestination: NavigationDestination,
    onDestinationSelected: (NavigationDestination) -> Unit,
    modifier: Modifier = Modifier,
    barHeight: Dp = 84.dp
) {
    val destinations = listOf(
        NavigationDestination.Home,
        NavigationDestination.Channels,
        NavigationDestination.Guide,
        NavigationDestination.Favourites,
        NavigationDestination.Settings
    )

    val colors = LocalAppleColors.current
    val typography = LocalAppleTypography.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(barHeight)
            .shadow(
                elevation = 18.dp,
                shape = RoundedCornerShape(30.dp),
                clip = false
            )
            .clip(RoundedCornerShape(28.dp))
            .border(width = 1.dp, color = colors.tabBarBorder, shape = RoundedCornerShape(28.dp))
            .background(colors.tabBarBackground)
    ) {
        // Rear glow layer for richer "liquid glass" depth.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(barHeight)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colors.tabBarHighlight.copy(alpha = 0.35f),
                            colors.tabBarBackground.copy(alpha = 0.08f)
                        )
                    )
                )
        )

        // Top highlight to simulate liquid glass reflection
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(colors.tabBarHighlight)
                .align(Alignment.TopCenter)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            destinations.forEach { destination ->
                val selected = currentDestination.route == destination.route
                val tintColor = animateColorAsState(
                    targetValue = if (selected) colors.accent else colors.textSecondary,
                    animationSpec = tween(durationMillis = 220),
                    label = "tabTint"
                ).value
                val iconScale = animateFloatAsState(
                    targetValue = if (selected) 1.06f else 1f,
                    animationSpec = spring(dampingRatio = 0.65f, stiffness = 420f),
                    label = "iconScale"
                ).value
                val glowSize = animateDpAsState(
                    targetValue = if (selected) 34.dp else 0.dp,
                    animationSpec = tween(durationMillis = 220),
                    label = "glowSize"
                ).value
                val iconContainerColor = animateColorAsState(
                    targetValue = if (selected) colors.accent else colors.tabBarHighlight.copy(alpha = 0.28f),
                    animationSpec = tween(durationMillis = 220),
                    label = "iconContainerColor"
                ).value
                val iconContainerWidth = animateDpAsState(
                    targetValue = if (selected) 44.dp else 34.dp,
                    animationSpec = spring(dampingRatio = 0.72f, stiffness = 460f),
                    label = "iconContainerWidth"
                ).value

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable { onDestinationSelected(destination) }
                        .padding(vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (glowSize > 0.dp) {
                        Box(
                            modifier = Modifier
                                .offset(y = 20.dp)
                                .size(glowSize)
                                .clip(RoundedCornerShape(100))
                                .background(colors.accent.copy(alpha = 0.18f))
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(iconContainerWidth)
                            .height(34.dp)
                            .clip(RoundedCornerShape(100))
                            .background(iconContainerColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = when (destination) {
                                NavigationDestination.Home -> Icons.Default.Home
                                NavigationDestination.Channels -> Icons.Default.Tv
                                NavigationDestination.Guide -> Icons.Default.Menu
                                NavigationDestination.Favourites -> Icons.Default.Favorite
                                NavigationDestination.Settings -> Icons.Default.Settings
                            },
                            contentDescription = destination.label,
                            tint = if (selected) colors.tabBarBackground else tintColor,
                            modifier = Modifier.size((22.dp.value * iconScale).dp)
                        )
                    }

                    BasicText(
                        text = destination.label,
                        style = typography.tabLabel.copy(
                            color = tintColor,
                            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
}
