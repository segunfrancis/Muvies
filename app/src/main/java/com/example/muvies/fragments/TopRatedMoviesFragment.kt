package com.example.muvies.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.muvies.R
import com.example.muvies.databinding.TopRatedMoviesFragmentBinding
import com.example.muvies.pagedAdapters.TopRatedMoviesMainAdapter
import com.example.muvies.viewModels.TopRatedMoviesViewModel

class TopRatedMoviesFragment : Fragment() {

    private lateinit var viewModel: TopRatedMoviesViewModel
    private lateinit var binding: TopRatedMoviesFragmentBinding

    private val topRatedAdapter = TopRatedMoviesMainAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TopRatedMoviesFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(TopRatedMoviesViewModel::class.java)
        binding.topRatedMoviesVieModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topRatedMoviesMainList.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = topRatedAdapter
        }

        viewModel.getTopRatedList().observe(viewLifecycleOwner, Observer {
            topRatedAdapter.submitList(it)
        })
    }

}