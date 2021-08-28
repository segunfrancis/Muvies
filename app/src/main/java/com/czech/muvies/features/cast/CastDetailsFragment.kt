package com.czech.muvies.features.cast

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.czech.muvies.databinding.CastDetailsFragmentBinding
import com.czech.muvies.features.cast.epoxy.CastAdapter
import com.czech.muvies.models.PersonDetails
import com.czech.muvies.models.PersonMovies
import com.czech.muvies.models.PersonTvShows
import com.czech.muvies.network.MoviesApiService
import com.czech.muvies.utils.Result
import com.czech.muvies.utils.convertDate
import com.czech.muvies.utils.epoxy.BaseEpoxyController
import com.czech.muvies.utils.epoxy.carouselNoSnap
import com.czech.muvies.utils.launchFragment
import com.czech.muvies.utils.loadRoundCastImage
import com.czech.muvies.utils.makeGone
import com.czech.muvies.utils.makeVisible
import com.czech.muvies.utils.showErrorMessage
import com.czech.muvies.utils.toGender
import timber.log.Timber
import java.lang.Exception
import java.util.*

class CastDetailsFragment : Fragment() {

    private var _binding: CastDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: CastDetailsFragmentArgs by navArgs()
    private val castId: Int by lazy { args.castId }
    private val viewModel: CastDetailsViewModel by viewModels {
        CastDetailsViewModelFactory(
            MoviesApiService.getService()
        )
    }
    private val moviesController = CastMoviesEpoxyController()
    private val tvShowsController = CastTvShowsEpoxyController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCastDetails(castId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CastDetailsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.castMoviesToggle.setOnClickListener {
            binding.castMoviesToggle.strokeWidth = 2
            binding.castShowsToggle.strokeWidth = 0
            binding.castMovies.makeVisible()
            binding.castShows.makeGone()
        }
        binding.castShowsToggle.setOnClickListener {
            binding.castShowsToggle.strokeWidth = 2
            binding.castMoviesToggle.strokeWidth = 0
            binding.castShows.makeVisible()
            binding.castMovies.makeGone()
        }

        binding.castMovies.adapter = moviesController.adapter
        binding.castShows.adapter = tvShowsController.adapter

        viewModel.castDetails.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> handleLoading()
                is Result.Success -> handleSuccess(result.data)
                is Result.Error -> handleError(result.error)
            }
        }

        viewModel.movieDetails.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                }
                is Result.Success -> {
                    val movieDetails = result.data.cast?.take(20)
                    moviesController.data = movieDetails
                    moviesController.requestModelBuild()
                }
                is Result.Error -> {
                    requireView().showErrorMessage(result.error.localizedMessage)
                    Timber.e(result.error)
                }
            }
        }

        viewModel.tvShowsDetails.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                }
                is Result.Success -> {
                    val tvShowDetails = result.data.cast?.take(20)
                    tvShowsController.data = tvShowDetails
                    tvShowsController.requestModelBuild()
                }
                is Result.Error -> {
                    requireView().showErrorMessage(result.error.localizedMessage)
                    Timber.e(result.error)
                }
            }
        }
    }

    private fun handleLoading() = with(binding) {
        lottieProgress.makeVisible()
    }

    private fun handleError(error: Throwable) = with(binding) {
        root.showErrorMessage(error.localizedMessage)
        lottieProgress.makeGone()
        Timber.e(error)
    }

    private fun handleSuccess(details: PersonDetails) = with(binding) {
        lottieProgress.makeGone()
        allView.makeVisible()
        biography.text = details.biography
        birthday.text = "Born on ".plus(details.birthday?.convertDate())
        name.text = details.name
        (requireActivity() as AppCompatActivity).supportActionBar?.title = details.name
        castImage.loadRoundCastImage(details.profilePath)
        try {
            details.gender?.let {
                gender.text = it.toGender().name.toLowerCase(Locale.getDefault())
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
        // Hide tv shows list initially
        castShows.makeGone()
    }

    inner class CastMoviesEpoxyController : BaseEpoxyController<PersonMovies.Cast?>() {
        override fun buildModels() {
            data?.let {
                carouselNoSnap {
                    id("movies_carousel")
                    models(it.mapIndexed { index, cast ->
                        CastAdapter {
                            launchFragment(
                                CastDetailsFragmentDirections.actionCastDetailsFragmentToDetailsFragment(
                                    it
                                )
                            )
                        }.apply {
                            id(index)
                            itemId = cast?.id ?: 0
                            name = cast?.title.toString()
                            imageUrl = cast?.posterPath.toString()
                        }
                    })
                }
            }
        }
    }

    inner class CastTvShowsEpoxyController : BaseEpoxyController<PersonTvShows.Cast?>() {
        override fun buildModels() {
            data?.let {
                carouselNoSnap {
                    id("tv_shows_carousel")
                    models(it.mapIndexed { index, cast ->
                        CastAdapter {
                            launchFragment(
                                CastDetailsFragmentDirections.actionCastDetailsFragmentToTvShowsDetailsFragment(
                                    it
                                )
                            )
                        }.apply {
                            id(index)
                            itemId = cast?.id ?: 0
                            name = cast?.name.toString()
                            imageUrl = cast?.posterPath.toString()
                        }
                    })
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
