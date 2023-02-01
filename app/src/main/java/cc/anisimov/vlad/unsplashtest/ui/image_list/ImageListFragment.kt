package cc.anisimov.vlad.unsplashtest.ui.image_list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import cc.anisimov.vlad.unsplashtest.databinding.FragmentImageListBinding
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import cc.anisimov.vlad.unsplashtest.ui.image_list.adapter.PhotosAdapter
import cc.anisimov.vlad.unsplashtest.util.extension.launchAndCollect
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageListFragment : Fragment() {

    private val viewModel: ImageListViewModel by viewModels()

    private var _binding: FragmentImageListBinding? = null
    private val binding get() = _binding!!

    private lateinit var photosAdapter: PhotosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvPhotos.itemAnimator = null
        photosAdapter = PhotosAdapter(::onPhotoBookmarkClick)
        with(binding.rvPhotos) {
            adapter = photosAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.latestPhotosFlow.launchAndCollect(this) {
            photosAdapter.submitList(it)
        }

        viewModel.isLoadingFlow.launchAndCollect(this) {
            onLoading(it)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onPhotoBookmarkClick(photo: Photo) {
        viewModel.onBookmarkClick(photo)
    }

    private fun onLoading(isLoading: Boolean) {
        with(binding) {
            rvPhotos.isVisible = !isLoading
            pbLoading.isVisible = isLoading
        }
    }

}