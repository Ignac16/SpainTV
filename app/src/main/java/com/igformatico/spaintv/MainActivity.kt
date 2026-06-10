package com.igformatico.spaintv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.igformatico.spaintv.navigation.BottomNavBar
import com.igformatico.spaintv.navigation.NavigationDestination
import com.igformatico.spaintv.screens.ChannelsScreen
import com.igformatico.spaintv.screens.FavouritesScreen
import com.igformatico.spaintv.screens.GuideScreen
import com.igformatico.spaintv.screens.HomeScreen
import com.igformatico.spaintv.screens.SettingsScreen
import com.igformatico.spaintv.ui.theme.AppleTheme
import com.igformatico.spaintv.ui.theme.LocalAppleColors

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppleTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var currentDestination by remember { mutableStateOf<NavigationDestination>(NavigationDestination.Home) }
    val colors = LocalAppleColors.current
    val tabBarBottomPadding = 108.dp
    val hazeState = rememberHazeState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(colors.backgroundTop, colors.backgroundBottom)
                )
            )
            .hazeSource(state = hazeState)
    ) {
        // Content area with bottom padding so it doesn't overlap the tab bar.
        Box(modifier = Modifier.fillMaxSize().padding(bottom = tabBarBottomPadding)) {
            AnimatedContent(
                targetState = currentDestination,
                transitionSpec = {
                    (slideInHorizontally(
                        animationSpec = spring(dampingRatio = 0.8f, stiffness = 360f),
                        initialOffsetX = { it / 6 }
                    ) + fadeIn(animationSpec = tween(180))) togetherWith
                        (slideOutHorizontally(
                            animationSpec = spring(dampingRatio = 0.9f, stiffness = 420f),
                            targetOffsetX = { -it / 8 }
                        ) + fadeOut(animationSpec = tween(150)))
                },
                label = "screenTransition"
            ) { destination ->
                when (destination) {
                    is NavigationDestination.Home -> HomeScreen(modifier = Modifier.fillMaxSize())
                    is NavigationDestination.Channels -> ChannelsScreen(modifier = Modifier.fillMaxSize())
                    is NavigationDestination.Guide -> GuideScreen(modifier = Modifier.fillMaxSize())
                    is NavigationDestination.Favourites -> FavouritesScreen(modifier = Modifier.fillMaxSize())
                    is NavigationDestination.Settings -> SettingsScreen(modifier = Modifier.fillMaxSize())
                }
            }
        }

        BottomNavBar(
            currentDestination = currentDestination,
            onDestinationSelected = { destination ->
                currentDestination = destination
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(fraction = 0.94f)
                .padding(horizontal = 12.dp, vertical = 20.dp)
                .hazeEffect(
                    state = hazeState,
                    style = HazeMaterials.ultraThin()
                )
        )
    }
}

@OptIn(ExperimentalHazeMaterialsApi::class)
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AppleTheme {
        MainScreen()
    }
}