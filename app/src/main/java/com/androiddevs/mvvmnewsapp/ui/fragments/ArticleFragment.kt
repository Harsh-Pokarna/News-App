package com.androiddevs.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.base.BaseFragment
import com.androiddevs.mvvmnewsapp.models.Article
import com.androiddevs.mvvmnewsapp.ui.NewsActivity
import com.androiddevs.mvvmnewsapp.ui.NewsViewModel
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : BaseFragment(R.layout.fragment_article) {

    val args: ArticleFragmentArgs by navArgs()
    lateinit var article: Article

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        initialiseVariables()
    }

    private fun initialiseVariables() {
        article = args.article
        Log.e("Article", article.url)
        setWebView()
    }

    private fun setWebView() {
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }
    }

}