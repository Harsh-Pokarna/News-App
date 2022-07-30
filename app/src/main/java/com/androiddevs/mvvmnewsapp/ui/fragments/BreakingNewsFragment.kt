package com.androiddevs.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.adapters.NewsAdapter
import com.androiddevs.mvvmnewsapp.base.BaseFragment
import com.androiddevs.mvvmnewsapp.utils.ExtFunctions.hideProgressBar
import com.androiddevs.mvvmnewsapp.utils.ExtFunctions.showProgressBar
import com.androiddevs.mvvmnewsapp.utils.Resource
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_breaking_news.*


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
        setListeners()
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

    private fun setListeners() {
        newsAdapter.setOnItemClickedListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
                Log.e(TAG, "Article link is: ${it.url}")
            }
            Navigation.findNavController(requireView()).navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }

        rvBreakingNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.getBreakingNews("us")
                }
            }
        })
    }
}