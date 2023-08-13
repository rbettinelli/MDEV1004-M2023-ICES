package com.example.mdev1004s23A4

// -------------------------------------------------------------
// - Robert Bettinelli - MDEV1004 - S2023
// - 090003683@student.georgianc.on.ca
// -------------------------------------------------------------
// 08/23/2023 - RBettinelli - Header and Documentation Added
// -------------------------------------------------------------

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import android.util.Log
import android.widget.ImageButton

class Movielisting : AppCompatActivity(), MovieListApiResponseCallback, MovieDeleteApiResponseCallback {

    //Var
    private val comLib = LibCom()
    private val iodbList = IOdbMovieList(this)
    private val iodbDelete = IOdbMovieDelete(this)
    private lateinit var movieAdapter: MovieAdapter
    private var btnMAdd: ImageButton? = null
    private var delMovieSelected: Movie = Movie("", 0, "", "", listOf(), listOf(), listOf(), listOf(), 0, "", "", "", 0.0, "")

    // Main Setup Auth Grab view and buttons.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movielisting)
        btnMAdd = findViewById(R.id.ibAdd)
        btnMAdd?.setOnClickListener { _ -> addMovie() }

        val auth: String = comLib.sharePRead(applicationContext,"auth").toString()
        val bearerAndAuth = "Bearer $auth"
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        movieAdapter = MovieAdapter(mutableListOf()) { movie ->
            // Handle the delete button click here
            deleteMovie(movie)
        }
        recyclerView.adapter = movieAdapter
        iodbList.getMovieList(bearerAndAuth)
    }

    // Delete Movie Button.
    private fun deleteMovie(movie: Movie) {
        // Implement your logic to delete the movie
        // Update the dataset and notify adapter if needed
        Log.d("crap","Delete!")
        val auth: String = comLib.sharePRead(applicationContext,"auth").toString()
        val bearerAndAuth = "Bearer $auth"
        delMovieSelected = movie
        iodbDelete.deleteMovie(bearerAndAuth, movie._id)
    }

    // Add Function if Add Success / Fails.
    override fun onSuccess(response: MovieResponse) {

        val movies = response.data
        movieAdapter.movies = movies
        movieAdapter.notifyDataSetChanged()

        // Applying OnClickListener to our Adapter
        movieAdapter.setOnClickListener(object :
            MovieAdapter.OnClickListener {
            override fun onClick(position: Int, model: Movie) {
                val intent = Intent(this@Movielisting, Entry::class.java)
                // Passing the data to the
                // EmployeeDetails Activity
                intent.putExtra("movie", model)
                startActivity(intent)
            }
        })
    }

    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
        // Handle login failure here
        // Show an error message, log the error, etc.
        Log.d("crap","JSON Call Error")
        comLib.showAlert(this, "Error", "Could Not Add Movie.")
    }

    // Add Movie Button.. Jump to Add movie
    private fun addMovie() {
        startActivity(Intent(this@Movielisting, Entry::class.java))
    }


    // Delete Functions if Delete Success / Fails.
    override fun onDSuccess(response: MovieDeleteWrapper) {
        movieAdapter.remove(delMovieSelected)
        Log.d("crap","Delete Successful")
    }

    override fun onDFailure(call: Call<MovieDeleteWrapper>, t: Throwable) {
        Log.d("crap","Delete Error")
        comLib.showAlert(this, "Error", "Could Not Delete Movie.")
    }

}
