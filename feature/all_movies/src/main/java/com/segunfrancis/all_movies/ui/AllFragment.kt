package com.segunfrancis.all_movies.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.compose.collectAsLazyPagingItems
import com.segunfrancis.all_movies.R
import com.segunfrancis.all_movies.databinding.AllFragmentBinding
import com.segunfrancis.all_movies.di.AllMoviesModule
import com.segunfrancis.muvies.common.theme.MuviesTheme
import com.segunfrancis.muvies.common.viewBinding

class AllFragment : Fragment(R.layout.all_fragment) {

    private val binding: AllFragmentBinding by viewBinding(AllFragmentBinding::bind)

    private val viewModel: AllMoviesViewModel by viewModels {
        allMoviesViewModelFactory(apiService = AllMoviesModule.provideApiService())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeView.setContent {
            val pagedList = viewModel.moviesFlow?.collectAsLazyPagingItems()
            pagedList?.let {
                MuviesTheme {
                    AllMoviesScreen(movies = pagedList, onItemClick = {

                    })
                }
            }
        }
    }
}
