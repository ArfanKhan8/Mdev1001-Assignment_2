package com.study.room.pages

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.study.room.R
import com.study.room.data.AppEvent
import com.study.room.data.AppViewModel
import com.study.room.data.Movie
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomePage : Fragment(R.layout.fragment_home_page) {

    private lateinit var viewModel: AppViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_movies_list)
        viewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]
        val fabAddMovie: FloatingActionButton = view.findViewById(R.id.fab_add_movie)

        val toolbar: Toolbar? = activity?.findViewById(R.id.materialToolbar)
        toolbar?.title = "Home Page"



        fabAddMovie.setOnClickListener{
            showAddMovieDialog()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect{ state ->
                if(state.movies.isNotEmpty()){
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    recyclerView.adapter = MoviesAdapter(viewModel,state.movies)

                }
                if(state.currentMovie != null){
                    findNavController().navigate(R.id.action_home_page_to_edit_movie_page)
                }
            }
        }


    }
    private fun showAddMovieDialog(){
        val dialogView = layoutInflater.inflate(R.layout.add_movie_dialog, null)
        val tilAddMovieName: TextInputLayout = dialogView.findViewById(R.id.til_add_movie_name)
        val tilAddStudioName: TextInputLayout = dialogView.findViewById(R.id.til_add_studio_name)
        val tilImageUrl: TextInputLayout = dialogView.findViewById(R.id.til_add_image_url)
        val tilCriticsRating: TextInputLayout = dialogView.findViewById(R.id.til_add_critics_rating)


        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add Movie")
            .setView(dialogView)
            .setPositiveButton("Add"){ dialogInterface, _ ->
                val title = tilAddMovieName.editText?.text.toString()
                val studio = tilAddStudioName.editText?.text.toString()
                val posterUrl = tilImageUrl.editText?.text.toString()
                val rating = tilCriticsRating.editText?.text.toString()

                val movie = Movie(
                    title = title,
                    studio = studio,
                    posterUrl = posterUrl,
                    rating = if(rating == "") 0.0 else rating.toDouble()
                )
                viewModel.onEvent(AppEvent.AddMovie(movie))
                Toast.makeText(requireContext(),"Added $title",Toast.LENGTH_LONG).show()
            }.setNegativeButton("Cancel"){dialogInterface, _ ->
                dialogInterface.dismiss()
            }
        dialog.show()
    }
}