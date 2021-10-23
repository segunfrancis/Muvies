package com.czech.muvies.features.all

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.czech.muvies.R
import com.czech.muvies.databinding.AllFragmentBinding
import com.czech.muvies.pagedAdapters.AllMoviesMainAdapter
import com.czech.muvies.utils.NavigationDeepLinks
import com.czech.muvies.viewModels.AllMoviesViewModel
import com.czech.muvies.viewModels.AllMoviesViewModelFactory
import com.segunfrancis.muvies.common.viewBinding

class AllFragment : Fragment(R.layout.all_fragment) {

    private val binding: AllFragmentBinding by viewBinding(AllFragmentBinding::bind)
    private val args: AllFragmentArgs by navArgs()
    private val viewModel: AllMoviesViewModel by viewModels { AllMoviesViewModelFactory(args.category) }
    private val allMoviesAdapter: AllMoviesMainAdapter by lazy {
        AllMoviesMainAdapter { id, title ->
            NavigationDeepLinks.toTvShowDetails(id, title)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.airingTodayMainList.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = allMoviesAdapter
        }

        viewModel.getAiringTodayList().observe(viewLifecycleOwner) {
            allMoviesAdapter.submitList(it)
        }
    }
}
