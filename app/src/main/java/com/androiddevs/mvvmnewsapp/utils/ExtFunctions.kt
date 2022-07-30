package com.androiddevs.mvvmnewsapp.utils

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import com.google.android.material.snackbar.Snackbar

object ExtFunctions {

    fun hideProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.INVISIBLE
    }

     fun showProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
    }

    fun showSnackBar(view: View, text: String) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
    }
}