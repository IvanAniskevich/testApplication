package com.example.figmatest.ui

import android.content.Context
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ProgressBar
import android.widget.TextView

class ProgressBarAnimation(
    val progressBar: ProgressBar,
    val textView: TextView,
    val from: Float,
    val to: Float,
) : Animation(){

    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        super.applyTransformation(interpolatedTime, t)
        val value = from + (to - from) * interpolatedTime
        progressBar.progress = value.toInt()
        textView.text = "${value.toInt()} %"
    }


}