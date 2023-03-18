package com.czech.muvies.features.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.czech.muvies.R
import com.czech.muvies.adapters.FavMoviesAdapter
import com.czech.muvies.databinding.FragmentFavMoviesTabBinding
import com.czech.muvies.features.details.movie_details.ToFavorites
import com.czech.muvies.room.movies.MoviesEntity
import com.czech.muvies.utils.AppConstants.EXTRA_REPLY
import com.czech.muvies.viewModels.FavMoviesTabViewModel
import com.segunfrancis.muvies.common.viewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class FavMoviesTabFragment : Fragment(R.layout.fragment_fav_movies_tab) {

    private lateinit var viewModel: FavMoviesTabViewModel
    private val binding: FragmentFavMoviesTabBinding by viewBinding(FragmentFavMoviesTabBinding::bind)

    private val applicationScope = CoroutineScope(SupervisorJob())

    //private val database by lazy { MoviesDatabase.getDatabase(requireContext(), applicationScope) }
    //private val repository by lazy { MoviesRepository(database.moviesDao()) }
    private val favMoviesAdapter = FavMoviesAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        /*viewModel = ViewModelProvider(this, FavMoviesTabViewModelFactory(repository))
            .get(FavMoviesTabViewModel::class.java)*/
        binding.lifecycleOwner = this
        binding.favMoviesTabViewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intentData = Intent()

        intentData.getParcelableExtra<ToFavorites>(EXTRA_REPLY).let { data ->

            val favMovies = MoviesEntity(
                data?.movieId,
                data?.movieTitle,
                data?.movieOverview,
                data?.movieBackdrop,
                data?.moviePoster,
                data?.movieDate,
                data?.movieVote,
                data?.movieLang
            )
            viewModel.insert(favMovies)
        }

        binding.favMoviesList.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = favMoviesAdapter
        }

        /*viewModel.allMovies.observe(viewLifecycleOwner, Observer { movies ->
            movies.let { favMoviesAdapter.updateList(it) }
        })*/

    }

}