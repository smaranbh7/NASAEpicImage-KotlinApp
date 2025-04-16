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
class NasaEpicImageDetailViewModel @Inject constructor(
    private val nasaEpicRepository: NasaEpicRepository
) : ViewModel() {

    private val _image =
        MutableStateFlow<NasaEpicImageDetailState>(NasaEpicImageDetailState.Loading)
    val image: StateFlow<NasaEpicImageDetailState> = _image.asStateFlow()

    // Fetch image details by identifier
    fun fetchImage(identifier: String, isEnhanced: Boolean = false) = viewModelScope.launch {
        _image.value = NasaEpicImageDetailState.Loading

        // Get either normal or enhanced images based on the flag
        when (val response = if (isEnhanced) {
            nasaEpicRepository.getEnhancedImages()
        } else {
            nasaEpicRepository.getImages()
        }) {
            NasaEpicApiResponse.Error -> _image.value = NasaEpicImageDetailState.Failure
            is NasaEpicApiResponse.Success -> {
                // Find the image by its identifier
                val foundImage = response.images.find { it.identifier == identifier }
                _image.value = if (foundImage != null) {
                    NasaEpicImageDetailState.Success(foundImage)
                } else {
                    NasaEpicImageDetailState.Failure
                }
            }
            else -> _image.value = NasaEpicImageDetailState.Failure
        }
    }

    // Sealed class to represent different states of the image fetching operation
    sealed class NasaEpicImageDetailState {
        data class Success(val image: NasaEpicImageResponse.EpicImageItem) : NasaEpicImageDetailState()
        object Failure : NasaEpicImageDetailState()  // Represents failure state
        object Loading : NasaEpicImageDetailState()   // Represents loading state
    }
}
