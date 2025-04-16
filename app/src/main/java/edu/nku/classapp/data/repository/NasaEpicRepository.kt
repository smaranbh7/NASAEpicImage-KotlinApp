package edu.nku.classapp.data.repository

import edu.nku.classapp.data.model.NasaEpicApiResponse

interface NasaEpicRepository {
    suspend fun getImages(): NasaEpicApiResponse
    
    suspend fun getEnhancedImages(): NasaEpicApiResponse
}