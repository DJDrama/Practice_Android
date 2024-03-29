package com.dj.dotainfo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.dj.dotainfo.R
import com.dj.dotainfo.ui.theme.DotaInfoTheme
import com.dj.ui_herolist.HeroList
import com.dj.ui_herolist.ui.HeroListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            DotaInfoTheme {
                val viewModel: HeroListViewModel = hiltViewModel()
                HeroList(state = viewModel.state.value,
                    imageLoader = imageLoader)
            }
        }
    }
}















