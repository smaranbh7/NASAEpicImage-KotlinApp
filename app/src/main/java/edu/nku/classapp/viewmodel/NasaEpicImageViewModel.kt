package edu.nku.classapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.nku.classapp.data.model.NasaEpicApiResponse
import edu.nku.classapp.data.repository.NasaEpicRepository
import edu.nku.classapp.model.NasaEpicImageResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NasaEpicImageViewModel @Inject constructor(
    private val nasaEpicRepository: NasaEpicRepository
) : ViewModel() {

    private val _images =
        MutableStateFlow<NasaEpicImageState>(NasaEpicImageState.Loading)
    val images: StateFlow<NasaEpicImageState> = _images.asStateFlow()


    fun loadImages(useEnhanced: Boolean = false) = viewModelScope.launch {
        when (val response = if (useEnhanced) {
            nasaEpicRepository.getEnhancedImages()
        } else {
            nasaEpicRepository.getImages()
        }) {
            NasaEpicApiResponse.Error -> _images.value = NasaEpicImageState.Failure
            is NasaEpicApiResponse.Success -> _images.value =
                NasaEpicImageState.Success(response.images)

            else -> _images.value = NasaEpicImageState.Failure
        }
    }


    sealed class NasaEpicImageState {
        data class Success(val images: List<NasaEpicImageResponse.EpicImageItem>) :
            NasaEpicImageState()

        data object Failure : NasaEpicImageState()
        data object Loading : NasaEpicImageState()
    }
}
