package com.czech.muvies.features.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.czech.muvies.R
import com.czech.muvies.databinding.FragmentSearchBinding
import com.czech.muvies.di.InjectorUtils.ViewModelFactory.provideSearchViewModelFactory
import com.segunfrancis.muvies.common.viewBinding

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel by viewModels<SearchViewModel> { provideSearchViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setContent {
            SearchMoviesScreen(viewModel = viewModel)
        }
    }
}
