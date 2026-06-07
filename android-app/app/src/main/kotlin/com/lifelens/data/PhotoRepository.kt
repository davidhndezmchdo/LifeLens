package com.lifelens.data

import kotlinx.coroutines.flow.Flow

class PhotoRepository(private val dao: PhotoDao) {
    fun getAllPhotos(): Flow<List<PhotoEntity>> = dao.getAllPhotos()

    suspend fun insertPhoto(filePath: String, label: String? = null): Long =
        dao.insertPhoto(PhotoEntity(filePath = filePath, label = label))

    suspend fun updateLabel(photo: PhotoEntity, label: String?) =
        dao.updatePhoto(photo.copy(label = label))

    suspend fun deletePhoto(photo: PhotoEntity) =
        dao.deletePhoto(photo)
}
