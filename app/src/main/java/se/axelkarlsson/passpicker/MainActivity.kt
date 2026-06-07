package se.axelkarlsson.passpicker

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import se.axelkarlsson.passpicker.ui.route.generator.GeneratorRoute
import se.axelkarlsson.passpicker.ui.route.generator.GeneratorScreen
import se.axelkarlsson.passpicker.ui.route.history.HistoryRoute
import se.axelkarlsson.passpicker.ui.route.history.HistoryScreen
import se.axelkarlsson.passpicker.ui.theme.PassPickerTheme
import se.axelkarlsson.passpicker.util.Tab

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val tabs = listOf(
        GeneratorRoute,
        HistoryRoute
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val orientation = resources.configuration.orientation

        // We don't draw any complex things behind the navigation bar.
        // So, we set this to improve UX where the colour of the tab bar is used.
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT
            )
        )

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            window.isNavigationBarContrastEnforced = false
        }

        setContent {
            ContentView(tabs)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentView(tabs: List<Tab>) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val destination = navBackStackEntry?.destination

    var title by remember { mutableStateOf("") }

    // The tab bar will be hidden when in Settings (or similar)
    var showTabBar by remember { mutableStateOf(true) }

    PassPickerTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(title)
                })
            },
            bottomBar = {
                AnimatedVisibility(
                    visible = showTabBar,
                    enter = fadeIn(),
                    exit = shrinkVertically()
                ) {
                    BottomAppBar {
                        NavigationBar {
                            tabs.forEach { tab ->
                                val selected = destination
                                    ?.hierarchy
                                    ?.any {
                                        it.hasRoute(tab::class)
                                    } == true

                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            painter = painterResource(tab.icon),
                                            contentDescription = null
                                        )
                                    },
                                    label = { Text(tab.label) },
                                    selected = selected,
                                    onClick = {
                                        if (selected) {
                                            return@NavigationBarItem
                                        }

                                        navController.navigate(tab) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                startDestination = tabs.first()
            ) {
                composable<GeneratorRoute> {
                    title = GeneratorRoute.label
                    showTabBar = true

                    GeneratorScreen()
                }

                composable<HistoryRoute> {
                    title = HistoryRoute.label
                    showTabBar = true

                    HistoryScreen()
                }
            }
        }
    }
}