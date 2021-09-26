package com.franco.mytestapplication.presentation.imagelibrary

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.franco.mytestapplication.R

class ImageLibraryFragment : Fragment() {

    companion object {
        fun newInstance() = ImageLibraryFragment()
    }

    private lateinit var viewModel: ImageLibraryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.image_library_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ImageLibraryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}