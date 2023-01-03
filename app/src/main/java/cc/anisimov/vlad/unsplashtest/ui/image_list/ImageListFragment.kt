package cc.anisimov.vlad.unsplashtest.ui.image_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import cc.anisimov.vlad.unsplashtest.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageListFragment : Fragment() {


    private val viewModel: ImageListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_image_list, container, false)
    }


}