package com.lifelens.ui

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.lifelens.data.AppDatabase
import com.lifelens.data.PhotoEntity
import com.lifelens.data.PhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID

class PhotoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PhotoRepository(
        AppDatabase.getDatabase(application).photoDao(),
    )

    val photos: StateFlow<List<PhotoEntity>> = repository.getAllPhotos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList(),
        )

    fun addPhoto(filePath: String, label: String? = null) = viewModelScope.launch {
        repository.insertPhoto(filePath, label)
    }

    fun importPhotos(context: Context, uris: List<Uri>, label: String?) =
        viewModelScope.launch(Dispatchers.IO) {
            val photoDir = File(context.filesDir, "photos").apply { mkdirs() }
            uris.forEach { uri ->
                try {
                    val dest = File(photoDir, "IMP_${UUID.randomUUID()}.jpg")
                    context.contentResolver.openInputStream(uri)?.use { input ->
                        dest.outputStream().use { output -> input.copyTo(output) }
                    }
                    repository.insertPhoto(dest.absolutePath, label)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    fun updateLabel(photo: PhotoEntity, label: String?) = viewModelScope.launch {
        repository.updateLabel(photo, label)
    }

    fun deletePhoto(photo: PhotoEntity) = viewModelScope.launch {
        repository.deletePhoto(photo)
    }
}
