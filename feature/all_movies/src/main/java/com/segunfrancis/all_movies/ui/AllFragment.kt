package com.segunfrancis.all_movies.ui

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
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
            var loading = rememberSaveable { true }
            val state by viewModel.uiState.collectAsState()
            val movies = state.moviesFlow?.collectAsLazyPagingItems()
            if (movies?.itemSnapshotList?.isNotEmpty() == true) {
                loading = false
            }
            MuviesTheme {
                AllMoviesScreen(
                    movies = movies,
                    loading = loading,
                    onItemClick = {

                    })
            }
        }
    }
}
