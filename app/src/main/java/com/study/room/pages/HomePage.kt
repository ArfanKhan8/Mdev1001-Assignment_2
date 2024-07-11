package com.study.room.pages

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.study.room.R
import com.study.room.data.AppViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomePage : Fragment(R.layout.fragment_home_page) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_movies_list)
        val viewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]
        val fabAddMovie: FloatingActionButton = view.findViewById(R.id.fab_add_movie)
        val movies = viewModel.state.value.movies
        Log.d("logger", movies.toString())

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect{ state ->
                if(state.movies.isNotEmpty()){
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    recyclerView.adapter = MoviesAdapter(state.movies)

                }
            }
        }


    }
}