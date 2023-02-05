package com.example.kinopoisk.fragments

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import coil.load
import com.example.kinopoisk.R
import com.example.kinopoisk.SAVE
import com.example.kinopoisk.TITLE_SECOND
import com.example.kinopoisk.data.Movie

class SecondFragment : Fragment() {

    private lateinit var image: ImageView
    private lateinit var information: TextView
    private lateinit var genresInfo: TextView
    private lateinit var countriesInfo: TextView
    private lateinit var name: TextView
    private lateinit var buttonSave: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = TITLE_SECOND
        buttonSave = view.findViewById(R.id.button_save)
        image = view.findViewById<ImageView>(R.id.full_image)
        name = view.findViewById<TextView>(R.id.name)
        information = view.findViewById<TextView>(R.id.information)
        genresInfo = view.findViewById<TextView>(R.id.genres_info)
        countriesInfo = view.findViewById<TextView>(R.id.countries_info)
        val item: Movie = arguments?.getSerializable(FILM) as Movie
        image.load(item.imageUrl)
        name.text = item.nameRu
        information.text = item.info
        genresInfo.text = item.genre
        countriesInfo.text = item.country
        buttonSave.setOnClickListener {
            saveImage(image.drawable, information.toString())
            Toast.makeText(
                activity,
                SAVE,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun saveImage(drawable: Drawable, title:String): Uri {
        val bitmap = (drawable as BitmapDrawable).bitmap
        val savedImageURL = MediaStore.Images.Media.insertImage(
            context?.contentResolver,
            bitmap,
            title,
            "Image of $title"
        )
        return Uri.parse(savedImageURL)
    }

    companion object {
        @JvmStatic
        fun newInstance(movie: Movie): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()
            args.putSerializable(FILM, movie)
            fragment.arguments = args
            return fragment
        }
        private const val FILM = "FILM"
    }
}