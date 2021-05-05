package com.kashyapkpatel.sampleapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kashyapkpatel.sampleapp.R
import com.kashyapkpatel.sampleapp.databinding.FragmentMoviesBinding
import com.kashyapkpatel.sampleapp.interfaces.IFragmentCallbacks
import com.kashyapkpatel.sampleapp.ui.adapter.MoviesAdapter
import com.kashyapkpatel.sampleapp.util.Status
import com.kashyapkpatel.sampleapp.viewmodel.MovieViewModel

class MovieListFragment : BaseFragment() {

    companion object {
        const val TAG = "MovieListFragment"
    }

    private val movieViewModel by viewModels<MovieViewModel> { viewModelProviderFactory }

    lateinit var binding : FragmentMoviesBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IFragmentCallbacks) {
            iFragmentCallbacks = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_movies, container, false
        )
        binding.viewModel = movieViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iFragmentCallbacks.updateTitle(getString(R.string.app_name))
        setAdapterThings()
        observeMovies()
    }

    private fun setAdapterThings() {
        val moviesAdapter = MoviesAdapter()
        binding.adapter = moviesAdapter
    }

    /**
     * Observes movies from API
     */
    private fun observeMovies() {
        movieViewModel
            .getMoviesList()
            .observe(viewLifecycleOwner, Observer { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressing = true
                        binding.noItems = false
                    }
                    Status.SUCCESS -> {
                        binding.progressing = false
                        binding.noItems = false
                        binding.adapter?.submitList(resource.data?.results)
                    }
                    Status.ERROR -> {
                        binding.progressing = false
                        binding.noItems = true
                    }
                }
            })
    }

}