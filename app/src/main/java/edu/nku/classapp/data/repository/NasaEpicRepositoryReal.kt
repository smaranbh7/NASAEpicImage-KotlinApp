package edu.nku.classapp.data.repository

import edu.nku.classapp.data.NasaEpicApi
import edu.nku.classapp.data.model.NasaEpicApiResponse
import javax.inject.Inject

class NasaEpicRepositoryReal @Inject constructor(
    private val nasaEpicApi: NasaEpicApi
) : NasaEpicRepository {
    override suspend fun getImages(): NasaEpicApiResponse {
        val result = nasaEpicApi.getImages()
        return if (result.isSuccessful) {
            result.body()?.let {
                NasaEpicApiResponse.Success(it)
            } ?: NasaEpicApiResponse.Error
        } else {
            NasaEpicApiResponse.Error
        }
    }

    override suspend fun getEnhancedImages(): NasaEpicApiResponse {
        val result = nasaEpicApi.getEnhancedImages()
        return if (result.isSuccessful) {
            result.body()?.let {
                NasaEpicApiResponse.Success(it)
            } ?: NasaEpicApiResponse.Error
        } else {
            NasaEpicApiResponse.Error
        }
    }
}