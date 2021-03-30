package com.dj.sampleapp.ui

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View

@SuppressLint("ClickableViewAccessibility")
val onTouchListener = View.OnTouchListener { v, event ->
    v?.let{
        when(event?.action){
            MotionEvent.ACTION_DOWN->{
                v.animate().scaleXBy(0.5f).setDuration(10).start()
                v.animate().scaleYBy(0.5f).setDuration(10).start()
            }
            MotionEvent.ACTION_UP->{
                v.animate().cancel()
                v.animate().scaleX(1f).start()
                v.animate().scaleY(1f).start()
            }
        }
        false
    } ?: false
}