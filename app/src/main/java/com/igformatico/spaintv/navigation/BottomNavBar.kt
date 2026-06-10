package com.igformatico.spaintv.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Tv
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.igformatico.spaintv.ui.theme.LocalAppleColors
import com.igformatico.spaintv.ui.theme.LocalAppleTypography

@Composable
fun BottomNavBar(
    currentDestination: NavigationDestination,
    onDestinationSelected: (NavigationDestination) -> Unit,
    modifier: Modifier = Modifier
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
    val pillShape = RoundedCornerShape(percent = 50)

    Box(
        modifier = modifier
            .shadow(
                elevation = 24.dp,
                shape = pillShape,
                ambientColor = Color.Black.copy(alpha = 0.12f),
                spotColor = Color.Black.copy(alpha = 0.18f),
                clip = false
            )
            .clip(pillShape)
            .border(
                width = 1.dp,
                color = colors.tabBarBorder,
                shape = pillShape
            )
            .background(colors.tabBarBackground.copy(alpha = 0.55f))
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            destinations.forEach { destination ->
                val selected = currentDestination.route == destination.route

                val iconTileColor = animateColorAsState(
                    targetValue = if (selected) colors.accent else colors.tabIconTile,
                    animationSpec = tween(220),
                    label = "iconTileColor"
                ).value

                val glowSize = animateDpAsState(
                    targetValue = if (selected) 52.dp else 0.dp,
                    animationSpec = spring(dampingRatio = 0.7f, stiffness = 380f),
                    label = "glowSize"
                ).value

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onDestinationSelected(destination) }
                        .padding(horizontal = 2.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier.height(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (glowSize > 0.dp) {
                            Box(
                                modifier = Modifier
                                    .size(glowSize)
                                    .clip(CircleShape)
                                    .background(colors.tabSelectionGlow)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(9.dp))
                                .background(iconTileColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = when (destination) {
                                    NavigationDestination.Home -> Icons.Rounded.Home
                                    NavigationDestination.Channels -> Icons.Rounded.Tv
                                    NavigationDestination.Guide -> Icons.Rounded.Menu
                                    NavigationDestination.Favourites -> Icons.Rounded.Favorite
                                    NavigationDestination.Settings -> Icons.Rounded.Settings
                                },
                                contentDescription = destination.label,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    BasicText(
                        text = destination.label,
                        style = typography.tabLabel.copy(
                            color = colors.textPrimary,
                            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
}
