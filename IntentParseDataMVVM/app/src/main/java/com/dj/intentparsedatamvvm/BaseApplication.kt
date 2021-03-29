package com.dj.intentparsedatamvvm

import android.app.Application
import com.dj.intentparsedatamvvm.di.AppComponent
import com.dj.intentparsedatamvvm.di.DaggerAppComponent

class BaseApplication: Application() {
    val appComponent: AppComponent by lazy{
        initializeComponent()
    }

    private fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }
}