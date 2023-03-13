package com.czech.muvies.features.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.czech.muvies.R
import com.czech.muvies.adapters.TabAdapter
import com.czech.muvies.databinding.FavoritesFragmentBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.segunfrancis.muvies.common.viewBinding

class FavoritesFragment : Fragment() {

    private lateinit var viewModel: FavoritesViewModel

    private lateinit var tabAdapter: TabAdapter

    private val binding by viewBinding(FavoritesFragmentBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)

        return inflater.inflate(R.layout.favorites_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabAdapter = TabAdapter(this, createTabs())
        binding.favViewPager.adapter = tabAdapter

        TabLayoutMediator(binding.favTabLayout, binding.favViewPager) { tab, position ->
            when(position) {
                0 -> tab.text = "Movies"
                1 -> tab.text = "Shows"
            }
        }.attach()
    }

    private fun createTabs(): ArrayList<Fragment> {
        val fragments: ArrayList<Fragment> = arrayListOf()

        fragments.add(FavMoviesTabFragment().apply {
        })

        fragments.add(FavShowsTabFragment().apply {
        })

        return fragments
    }
}
