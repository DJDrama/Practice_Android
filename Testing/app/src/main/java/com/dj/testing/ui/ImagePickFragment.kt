package com.dj.testing.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dj.testing.R

class ImagePickFragment: Fragment(R.layout.fragment_image_pick) {
    private val viewModel by activityViewModels<ShoppingViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}