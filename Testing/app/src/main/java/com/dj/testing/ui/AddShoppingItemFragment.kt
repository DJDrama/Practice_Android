package com.dj.testing.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dj.testing.R

class AddShoppingItemFragment: Fragment(R.layout.fragment_add_shopping_item) {
    private val viewModel by activityViewModels<ShoppingViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}