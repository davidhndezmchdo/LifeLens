package com.lifelens.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.layout.padding
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lifelens.ui.PhotoViewModel
import com.lifelens.ui.screen.CameraScreen
import com.lifelens.ui.screen.GalleryScreen

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    data object Camera : Screen("camera", "Camera", Icons.Default.CameraAlt)
    data object Gallery : Screen("gallery", "Gallery", Icons.Default.PhotoLibrary)
}

private val screens = listOf(Screen.Camera, Screen.Gallery)

@Composable
fun AppNavigation(viewModel: PhotoViewModel) {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                screens.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    )
                }
            }
        },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Camera.route,
            modifier = Modifier.padding(paddingValues),
        ) {
            composable(Screen.Camera.route) { CameraScreen(viewModel) }
            composable(Screen.Gallery.route) { GalleryScreen(viewModel) }
        }
    }
}
