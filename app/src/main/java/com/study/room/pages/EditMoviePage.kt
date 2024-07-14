package com.study.room.pages

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.text.set
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.study.room.R
import com.study.room.data.AppEvent
import com.study.room.data.AppViewModel
import com.study.room.data.Movie
import kotlinx.coroutines.launch

class EditMoviePage : Fragment(R.layout.fragment_edit_movie_page) {

    private lateinit var viewModel: AppViewModel
    private lateinit var llEditPage: LinearLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tilMovieName: TextInputLayout = view.findViewById(R.id.til_movie_name)
        val tilStudioName: TextInputLayout = view.findViewById(R.id.til_studio_name)
        val tilImageUrl: TextInputLayout = view.findViewById(R.id.til_image_url)
        val tilCriticsRating: TextInputLayout = view.findViewById(R.id.til_critics_rating)
        val btnCancel: Button = view.findViewById(R.id.btn_cancel)
        val btnSave: Button = view.findViewById(R.id.btn_save)
        llEditPage  = view.findViewById(R.id.ll_edit_page)

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
            val id = currentMovie?.id
            val title = tilMovieName.editText?.text.toString()
            val studio = tilStudioName.editText?.text.toString()
            val posterUrl = tilImageUrl.editText?.text.toString()
            val rating = tilCriticsRating.editText?.text.toString().toDouble()
            val movie = Movie(
                id = id,
                title = title,
                studio = studio,
                posterUrl = posterUrl,
                rating = rating
            )
            viewModel.onEvent(AppEvent.AddMovie(movie))
            Snackbar.make(view,"$title has been updated!", Snackbar.LENGTH_LONG).show()
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
                    Snackbar.make(llEditPage,"${movie.title} has been deleted!", Snackbar.LENGTH_LONG).show()
                    findNavController().navigateUp()
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}

