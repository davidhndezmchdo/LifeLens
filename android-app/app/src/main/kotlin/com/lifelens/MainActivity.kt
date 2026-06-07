package com.lifelens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.lifelens.navigation.AppNavigation
import com.lifelens.ui.PhotoViewModel
import com.lifelens.ui.theme.LifeLensTheme

class MainActivity : ComponentActivity() {
    private val viewModel: PhotoViewModel by viewModels()
    private var cameraGranted by mutableStateOf(false)

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { granted ->
        cameraGranted = granted
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cameraGranted = ContextCompat.checkSelfPermission(
            this, Manifest.permission.CAMERA,
        ) == PackageManager.PERMISSION_GRANTED

        if (!cameraGranted) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }

        setContent {
            LifeLensTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    if (cameraGranted) {
                        AppNavigation(viewModel = viewModel)
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize().padding(32.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "Camera permission is required to use LifeLens.",
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }
                }
            }
        }
    }
}
