package com.lifelens.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lifelens.data.PhotoEntity
import com.lifelens.ui.ACTIVITY_LABELS
import com.lifelens.ui.PhotoViewModel

@Composable
fun GalleryScreen(viewModel: PhotoViewModel) {
    val photos by viewModel.photos.collectAsState()
    val context = LocalContext.current
    var selectedPhoto by remember { mutableStateOf<PhotoEntity?>(null) }
    var pendingImportUris by remember { mutableStateOf<List<Uri>>(emptyList()) }

    val importLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(),
    ) { uris ->
        if (uris.isNotEmpty()) pendingImportUris = uris
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (photos.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "No photos yet.\nUse the Camera tab or import from your gallery.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(32.dp),
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(start = 2.dp, end = 2.dp, top = 2.dp, bottom = 80.dp),
                modifier = Modifier.fillMaxSize(),
            ) {
                items(photos, key = { it.id }) { photo ->
                    PhotoGridItem(photo = photo, onTap = { selectedPhoto = photo })
                }
            }
        }

        FloatingActionButton(
            onClick = {
                importLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
                )
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
        ) {
            Icon(Icons.Default.Add, contentDescription = "Import photos")
        }
    }

    if (pendingImportUris.isNotEmpty()) {
        ImportLabelDialog(
            count = pendingImportUris.size,
            onImport = { label ->
                viewModel.importPhotos(context, pendingImportUris, label)
                pendingImportUris = emptyList()
            },
            onDismiss = { pendingImportUris = emptyList() },
        )
    }

    selectedPhoto?.let { photo ->
        LabelDialog(
            photo = photo,
            onDismiss = { selectedPhoto = null },
            onLabelSelected = { label ->
                viewModel.updateLabel(photo, label)
                selectedPhoto = null
            },
            onDelete = {
                viewModel.deletePhoto(photo)
                selectedPhoto = null
            },
        )
    }
}

@Composable
private fun PhotoGridItem(photo: PhotoEntity, onTap: () -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(1.dp)
            .clickable(onClick = onTap),
    ) {
        AsyncImage(
            model = photo.filePath,
            contentDescription = photo.label ?: "Unlabeled",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        photo.label?.let { label ->
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.85f),
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ImportLabelDialog(
    count: Int,
    onImport: (String?) -> Unit,
    onDismiss: () -> Unit,
) {
    var selectedLabel by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Import $count ${if (count == 1) "photo" else "photos"}") },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text(
                    text = "Apply a label to all imported photos, or leave blank and label them individually from the gallery.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(Modifier.height(12.dp))
                FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    ACTIVITY_LABELS.forEach { label ->
                        FilterChip(
                            selected = selectedLabel == label,
                            onClick = { selectedLabel = if (selectedLabel == label) null else label },
                            label = { Text(label) },
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = { onImport(selectedLabel) }) {
                Text(if (selectedLabel != null) "Import as $selectedLabel" else "Import")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        },
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun LabelDialog(
    photo: PhotoEntity,
    onDismiss: () -> Unit,
    onLabelSelected: (String?) -> Unit,
    onDelete: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Label this photo") },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                AsyncImage(
                    model = photo.filePath,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Current: ${photo.label ?: "Unlabeled"}",
                    style = MaterialTheme.typography.bodySmall,
                )
                Spacer(Modifier.height(8.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    ACTIVITY_LABELS.forEach { label ->
                        FilterChip(
                            selected = photo.label == label,
                            onClick = { onLabelSelected(label) },
                            label = { Text(label) },
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onLabelSelected(null) }) { Text("Clear Label") }
        },
        dismissButton = {
            Row {
                TextButton(onClick = onDelete) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
                TextButton(onClick = onDismiss) { Text("Cancel") }
            }
        },
    )
}
