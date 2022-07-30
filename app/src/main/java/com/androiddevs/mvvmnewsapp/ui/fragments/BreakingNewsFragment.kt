package com.androiddevs.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.adapters.NewsAdapter
import com.androiddevs.mvvmnewsapp.base.BaseFragment
import com.androiddevs.mvvmnewsapp.utils.ExtFunctions.hideProgressBar
import com.androiddevs.mvvmnewsapp.utils.ExtFunctions.showProgressBar
import com.androiddevs.mvvmnewsapp.utils.Resource
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job

class BreakingNewsFragment : BaseFragment(R.layout.fragment_breaking_news) {

    lateinit var newsAdapter: NewsAdapter

    private val TAG = "Breaking News Adapter"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        viewModel.getBreakingNews("us")
        setupRecyclerView()
        setObservers()
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun setObservers() {
        viewModel.breakingNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar(paginationProgressBar)
                    response.data.let { newsResponse ->
                        if (newsResponse != null) {
                            newsAdapter.differ.submitList(newsResponse.articles)
                        }
                    }
                }

                is Resource.Error -> {
                    hideProgressBar(paginationProgressBar)
                    response.message?.let { error ->
                        Log.e(TAG, error)
                    }
                }

                is Resource.Loading -> {
                    showProgressBar(paginationProgressBar)
                }
            }
        }
    }

}