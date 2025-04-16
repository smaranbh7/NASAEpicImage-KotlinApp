package edu.nku.classapp.data.model

import edu.nku.classapp.model.NasaEpicImageResponse

sealed class NasaEpicApiResponse {
    data class Success(val images: List<NasaEpicImageResponse.EpicImageItem>) :
        NasaEpicApiResponse()

    data class SingleImageSuccess(val image: NasaEpicImageResponse.EpicImageItem) :
        NasaEpicApiResponse()

    data object Error : NasaEpicApiResponse()
}