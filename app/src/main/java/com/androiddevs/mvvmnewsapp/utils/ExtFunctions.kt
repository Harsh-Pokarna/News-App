package com.androiddevs.mvvmnewsapp.utils

import android.view.View
import android.widget.ProgressBar

object ExtFunctions {

    fun hideProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.INVISIBLE
    }

     fun showProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
    }
}