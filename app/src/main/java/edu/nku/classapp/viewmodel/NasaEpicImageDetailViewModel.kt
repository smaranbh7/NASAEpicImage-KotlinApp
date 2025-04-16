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

    // Function to fetch image details by identifier
    fun fetchImage(identifier: String, isEnhanced: Boolean = false) = viewModelScope.launch {
        _image.value = NasaEpicImageDetailState.Loading

        when (val response = if (isEnhanced) {
            nasaEpicRepository.getEnhancedImageByIdentifier(identifier)
        } else {
            nasaEpicRepository.getImageByIdentifier(identifier)
        }) {
            NasaEpicApiResponse.Error -> _image.value = NasaEpicImageDetailState.Failure
            is NasaEpicApiResponse.SingleImageSuccess -> _image.value =
                NasaEpicImageDetailState.Success(response.image)

            else -> _image.value = NasaEpicImageDetailState.Failure
        }
    }


    sealed class NasaEpicImageDetailState {
        data class Success(val image: NasaEpicImageResponse.EpicImageItem) : NasaEpicImageDetailState()
        object Failure : NasaEpicImageDetailState()
        object Loading : NasaEpicImageDetailState()
    }
}
