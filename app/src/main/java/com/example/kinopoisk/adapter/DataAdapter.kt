package com.example.kinopoisk.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kinopoisk.R
import com.example.kinopoisk.data.Movie

class DataAdapter(items: MutableList<Movie>, listener: OnItemClickListener) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {
    private val items: MutableList<Movie>
    private val itemsAll: MutableList<Movie>
    private val listener: OnItemClickListener

    init {
        this.items = ArrayList(items)
        this.itemsAll = ArrayList(items)
        this.listener = listener
    }

    fun setItems(itemsNew: List<Movie>) {
        items.clear()
        itemsAll.clear()
        items.addAll(itemsNew)
        itemsAll.addAll(itemsNew)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(item: Movie, position: Int)
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image)
        val imageFavorite: ImageView = itemView.findViewById(R.id.favorite)
        val nameFilm: TextView = itemView.findViewById(R.id.name)
        val genre: TextView = itemView.findViewById(R.id.genre)
        val year: TextView = itemView.findViewById(R.id.year)
        val inform: TextView = itemView.findViewById(R.id.inform)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Movie = items[position]

        var genreText = item.genre
        if (genreText.indexOf(",") > 1) {
            genreText = genreText.substring(0, genreText.indexOf(","))
        }
        val genreInfo = genreText.replaceRange(0, 1, genreText[0].uppercase())

        holder.imageView.load(item.imageUrl)
        holder.imageFavorite.setImageResource(R.drawable.ordinary)
        holder.nameFilm.text = item.nameRu
        holder.genre.text = genreInfo
        holder.year.text = item.year
        holder.inform.text = item.info
        holder.itemView.setOnClickListener { listener.onItemClick(item, position) }
    }

    override fun getItemCount() = items.size
}