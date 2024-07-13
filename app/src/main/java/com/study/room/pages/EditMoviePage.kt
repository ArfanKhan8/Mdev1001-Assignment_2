package com.study.room.pages

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.core.text.set
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.study.room.R
import com.study.room.data.AppEvent
import com.study.room.data.AppViewModel
import com.study.room.data.Movie
import kotlinx.coroutines.launch

class EditMoviePage : Fragment(R.layout.fragment_edit_movie_page) {

    private lateinit var viewModel: AppViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tilMovieName: TextInputLayout = view.findViewById(R.id.til_movie_name)
        val tilStudioName: TextInputLayout = view.findViewById(R.id.til_studio_name)
        val tilImageUrl: TextInputLayout = view.findViewById(R.id.til_image_url)
        val tilCriticsRating: TextInputLayout = view.findViewById(R.id.til_critics_rating)
        val btnCancel: Button = view.findViewById(R.id.btn_cancel)
        val btnSave: Button = view.findViewById(R.id.btn_save)

        viewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]
        val currentMovie = viewModel.state.value.currentMovie
        tilMovieName.editText?.setText(currentMovie?.title)
        tilStudioName.editText?.setText(currentMovie?.studio)
        tilImageUrl.editText?.setText(currentMovie?.posterUrl)
        tilCriticsRating.editText?.setText(currentMovie?.rating.toString())

        val toolbar: Toolbar? = activity?.findViewById(R.id.materialToolbar)
        setHasOptionsMenu(true)
        toolbar?.title = "Edit Movie"

        btnCancel.setOnClickListener {
            viewModel.onEvent(AppEvent.OnEditCancel)
            findNavController().popBackStack()
        }

        btnSave.setOnClickListener {
            val movie = Movie(
                id = currentMovie?.id,
                title = tilMovieName.editText?.text.toString(),
                studio = tilStudioName.editText?.text.toString(),
                posterUrl = tilImageUrl.editText?.text.toString(),
                rating = tilCriticsRating.editText?.text.toString().toDouble()
            )
            viewModel.onEvent(AppEvent.AddMovie(movie))
            findNavController().popBackStack()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_page_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return  when(item.itemId){
            R.id.btn_delete_movie -> {
                val movie = viewModel.state.value.currentMovie
                movie?.let {
                    viewModel.onEvent(AppEvent.DeleteMovie(it))
                    findNavController().navigateUp()
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}

