package edu.nku.classapp.data

import edu.nku.classapp.model.NasaEpicImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NasaEpicApi {
    // Fetch all natural images (list of images)
    @GET("api/natural")
    suspend fun getImages(@Query("api_key") apiKey: String = "rdEYVPEOSdbTdk1zi3PQn0FqCxBGFbaNTR7SUAGF"): Response<List<NasaEpicImageResponse.EpicImageItem>>

    // Fetch all enhanced images (list of images)
    @GET("api/enhanced")
    suspend fun getEnhancedImages(@Query("api_key") apiKey: String = "rdEYVPEOSdbTdk1zi3PQn0FqCxBGFbaNTR7SUAGF"): Response<List<NasaEpicImageResponse.EpicImageItem>>

    // Fetch a specific image by identifier (single image response)
    @GET("api/natural/{identifier}")
    suspend fun getImageByIdentifier(@Path("identifier") identifier: String, @Query("api_key") apiKey: String = "rdEYVPEOSdbTdk1zi3PQn0FqCxBGFbaNTR7SUAGF"): Response<NasaEpicImageResponse.EpicImageItem>

    // Fetch an enhanced image by identifier (single image response)
    @GET("api/enhanced/{identifier}")
    suspend fun getEnhancedImageByIdentifier(@Path("identifier") identifier: String, @Query("api_key") apiKey: String = "rdEYVPEOSdbTdk1zi3PQn0FqCxBGFbaNTR7SUAGF"): Response<NasaEpicImageResponse.EpicImageItem>

    companion object {
        const val IMAGE_BASE_URL = "https://epic.gsfc.nasa.gov/archive/natural/"
        const val IMAGE_ENHANCED_BASE_URL = "https://epic.gsfc.nasa.gov/archive/enhanced/"
    }
}
