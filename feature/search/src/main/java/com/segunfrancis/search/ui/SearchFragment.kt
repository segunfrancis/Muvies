package com.segunfrancis.search.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.segunfrancis.muvies.common.viewBinding
import com.segunfrancis.search.R
import com.segunfrancis.search.databinding.FragmentSearchBinding
import com.segunfrancis.search.di.SearchModule.provideSearchAPiService

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel by viewModels<SearchViewModel> { searchViewModelFactory(api = provideSearchAPiService()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setContent {
            SearchMoviesScreen(viewModel = viewModel)
        }
    }
}
