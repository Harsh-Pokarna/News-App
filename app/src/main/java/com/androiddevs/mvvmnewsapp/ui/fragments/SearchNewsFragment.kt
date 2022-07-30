package com.androiddevs.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.adapters.NewsAdapter
import com.androiddevs.mvvmnewsapp.base.BaseFragment
import com.androiddevs.mvvmnewsapp.utils.Constants.SEARCH_NEWS_DELAY_TIME
import com.androiddevs.mvvmnewsapp.utils.ExtFunctions.hideProgressBar
import com.androiddevs.mvvmnewsapp.utils.ExtFunctions.showProgressBar
import com.androiddevs.mvvmnewsapp.utils.Resource
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : BaseFragment(R.layout.fragment_search_news) {

    private val TAG = "Search News Fragment"
    private lateinit var newsAdapter: NewsAdapter
    private var job: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        setRecyclerView()
        setObservers()
        setListeners()
    }

    private fun setRecyclerView() {
        newsAdapter = NewsAdapter()
        rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun setObservers() {
        viewModel.searchNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar(searchNewsPaginationProgressBar)
                    response.data.let { newsResponse ->
                        if (newsResponse != null) {
                            newsAdapter.differ.submitList(newsResponse.articles)
                        }
                    }
                }

                is Resource.Error -> {
                    hideProgressBar(searchNewsPaginationProgressBar)
                    response.message?.let { error ->
                        Log.e(TAG, error)
                    }
                }

                is Resource.Loading -> {
                    showProgressBar(searchNewsPaginationProgressBar)
                }
            }
        }
    }

    private fun setListeners() {
        etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = lifecycleScope.launch {
                delay(SEARCH_NEWS_DELAY_TIME)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                       viewModel.searchNews(editable.toString())
                    }
                }
            }
        }
    }

}