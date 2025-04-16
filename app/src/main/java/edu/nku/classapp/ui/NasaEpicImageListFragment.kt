package edu.nku.classapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import edu.nku.classapp.R
import edu.nku.classapp.databinding.FragmentImageListBinding
import edu.nku.classapp.ui.adapter.NasaEpicImageAdapter
import edu.nku.classapp.viewmodel.NasaEpicImageViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NasaEpicImageListFragment : Fragment() {

    private var _binding: FragmentImageListBinding? = null
    private val binding
        get() = _binding!!

    private val nasaEpicImageViewModel: NasaEpicImageViewModel by activityViewModels()

    private val nasaEpicImageAdapter = NasaEpicImageAdapter { image, _ ->
        requireActivity().supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(
                R.id.fragment_container_view,
                NasaEpicImageDetailFragment.newInstance(identifier = image.identifier)
            )
            addToBackStack(null)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageListBinding.inflate(inflater, container, false)

        setUpObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = nasaEpicImageAdapter
        }
        nasaEpicImageViewModel.loadImages()
    }

    private fun setUpObservers() {
        lifecycleScope.launch {
            nasaEpicImageViewModel.images.collect { event ->
                when (event) {
                    NasaEpicImageViewModel.NasaEpicImageState.Failure -> {
                        binding.errorMessage.isVisible = true
                        binding.progressBar.isVisible = false
                        binding.recyclerView.isVisible = false
                    }

                    NasaEpicImageViewModel.NasaEpicImageState.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.errorMessage.isVisible = false
                        binding.recyclerView.isVisible = false
                    }

                    is NasaEpicImageViewModel.NasaEpicImageState.Success -> {
                        nasaEpicImageAdapter.refreshData(event.images)
                        binding.recyclerView.isVisible = true
                        binding.errorMessage.isVisible = false
                        binding.progressBar.isVisible = false
                    }
                }
            }
        }
    }
}