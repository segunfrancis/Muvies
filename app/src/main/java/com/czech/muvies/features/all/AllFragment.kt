package com.czech.muvies.features.all

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.czech.muvies.R
import com.czech.muvies.databinding.AllFragmentBinding
import com.czech.muvies.pagedAdapters.AiringTodayMainAdapter
import com.czech.muvies.utils.NavigationDeepLinks
import com.czech.muvies.utils.viewBinding
import com.czech.muvies.viewModels.AiringTodayViewModel

class AllFragment : Fragment(R.layout.all_fragment) {

    private val binding: AllFragmentBinding by viewBinding(AllFragmentBinding::bind)
    private val viewModel: AiringTodayViewModel by viewModels()
    private val airingTodayAdapter: AiringTodayMainAdapter by lazy {
        AiringTodayMainAdapter {
            NavigationDeepLinks.toTvShowDetails(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.airingTodayMainList.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = airingTodayAdapter
        }

        viewModel.getAiringTodayList().observe(viewLifecycleOwner) {
            airingTodayAdapter.submitList(it)
        }
    }
}
