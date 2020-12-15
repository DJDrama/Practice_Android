package com.answer.univ.ui.launcher

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.answer.univ.R
import com.answer.univ.ui.showActionBar

class RegisterFragment: Fragment(R.layout.fragment_register) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().showActionBar()
    }
}