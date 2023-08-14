package com.example.mdev1004s23FINAL

// -------------------------------------------------------------
// - Robert Bettinelli - MDEV1004 - S2023
// - 090003683@student.georgianc.on.ca
// -------------------------------------------------------------
// 08/23/2023 - RBettinelli - Header and Documentation Added
// -------------------------------------------------------------

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MovieAdapter(var movies: MutableList<Movie>, private val deleteClickListener: (Movie) -> Unit) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    //var & Listeners.
    private var onClickListener: OnClickListener? = null

    // Fields for View.
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val studioTextView: TextView = itemView.findViewById(R.id.studioTextView)
        val ratingTextView: TextView = itemView.findViewById(R.id.ratingTextView)
        val btnDelMovie: ImageButton = itemView.findViewById(R.id.btnDelMovie)
    }

    //View Setup
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    // Get Data & Setup Select Button & Delete Button
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.titleTextView.text = movie.title
        holder.studioTextView.text = movie.studio
        holder.ratingTextView.text = movie.criticsRating.toString()

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, movie )
            }
        }

        holder.btnDelMovie.setOnClickListener {
            deleteClickListener.invoke(movie)
        }

        val rating = movie.criticsRating
        val context = holder.itemView.context

        when {
            rating > 7 -> {
                holder.ratingTextView.setBackgroundColor(context.getColor(R.color.green))
                holder.ratingTextView.setTextColor(context.getColor(R.color.black))
            }
            rating > 5 -> {
                holder.ratingTextView.setBackgroundColor(context.getColor(R.color.yellow))
                holder.ratingTextView.setTextColor(context.getColor(R.color.black))
            }
            else -> {
                holder.ratingTextView.setBackgroundColor(context.getColor(R.color.red))
                holder.ratingTextView.setTextColor(context.getColor(R.color.white))
            }
        }
    }

    // When Delete Button Pressed.
    fun remove(movie: Movie) {
        val position = movies.indexOf(movie)
        if (position != -1) {
            movies.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    // Select Button select get Movie Data.
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, model: Movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}

