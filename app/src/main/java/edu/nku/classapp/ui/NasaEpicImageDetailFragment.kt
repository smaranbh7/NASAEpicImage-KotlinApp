package edu.nku.classapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import edu.nku.classapp.R
import edu.nku.classapp.data.NasaEpicApi
import edu.nku.classapp.databinding.FragmentImageDetailBinding
import edu.nku.classapp.model.NasaEpicImageResponse
import edu.nku.classapp.viewmodel.NasaEpicImageDetailViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class NasaEpicImageDetailFragment : Fragment() {
    private var _binding: FragmentImageDetailBinding? = null
    private val binding
        get() = _binding!!

    private val nasaEpicImageDetailViewModel: NasaEpicImageDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageDetailBinding.inflate(inflater, container, false)
        setUpObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageIdentifier = arguments?.getString(BUNDLE_IDENTIFIER) ?: return
        nasaEpicImageDetailViewModel.fetchImage(imageIdentifier)
    }

    private fun setUpObservers() {
        lifecycleScope.launch {
            nasaEpicImageDetailViewModel.image.collect { event ->
                when (event) {
                    is NasaEpicImageDetailViewModel.NasaEpicImageDetailState.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.errorMessage.isVisible = false
                        binding.root.isVisible = false
                    }

                    is NasaEpicImageDetailViewModel.NasaEpicImageDetailState.Success -> {
                        binding.progressBar.isVisible = false
                        binding.errorMessage.isVisible = false
                        binding.root.isVisible = true
                        bindImageDetails(event.image)
                    }

                    is NasaEpicImageDetailViewModel.NasaEpicImageDetailState.Failure -> {
                        binding.progressBar.isVisible = false
                        binding.errorMessage.isVisible = true
                        binding.root.isVisible = false
                    }
                }
            }
        }
    }

    private fun bindImageDetails(epicImage: NasaEpicImageResponse.EpicImageItem) {
        binding.apply {
            imageIdentifierDetail.text = getString(R.string.image_identifier, epicImage.identifier)
            imageDateDetail.text = getString(R.string.image_date, formatDate(epicImage.date))
            imageCaptionDetail.text = getString(R.string.image_caption, epicImage.caption)
            imageVersionDetail.text = getString(R.string.image_version, epicImage.version)
            coordinatesDetail.text = getString(
                R.string.coordinates,
                "Lat: ${epicImage.centroidCoordinates.lat}, Lon: ${epicImage.centroidCoordinates.lon}"
            )
            dscovrPositionDetail.text = getString(
                R.string.dscovr_position,
                "X: ${epicImage.dscovrPosition.x}, Y: ${epicImage.dscovrPosition.y}, Z: ${epicImage.dscovrPosition.z}"
            )
            lunarPositionDetail.text = getString(
                R.string.lunar_position,
                "X: ${epicImage.lunarPosition.x}, Y: ${epicImage.lunarPosition.y}, Z: ${epicImage.lunarPosition.z}"
            )
            sunPositionDetail.text = getString(
                R.string.sun_position,
                "X: ${epicImage.sunPosition.x}, Y: ${epicImage.sunPosition.y}, Z: ${epicImage.sunPosition.z}"
            )

            val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
            val date = dateFormatter.parse(epicImage.date)
            val urlDateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.US)
            val formattedDate = urlDateFormat.format(date!!)

            val imageUrl = "${NasaEpicApi.IMAGE_BASE_URL}${formattedDate}/png/${epicImage.image}.png?api_key=rdEYVPEOSdbTdk1zi3PQn0FqCxBGFbaNTR7SUAGF"

            Glide.with(requireContext())
                .load(imageUrl)
                .into(imageDetailImage)
        }
    }

    private fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        val outputFormat = SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.US)
        return try {
            val date = inputFormat.parse(dateString)
            outputFormat.format(date!!)
        } catch (e: Exception) {
            dateString
        }
    }

    companion object {
        private const val BUNDLE_IDENTIFIER = "image_identifier"

        fun newInstance(identifier: String) = NasaEpicImageDetailFragment().apply {
            arguments = bundleOf(BUNDLE_IDENTIFIER to identifier)
        }
    }
}
