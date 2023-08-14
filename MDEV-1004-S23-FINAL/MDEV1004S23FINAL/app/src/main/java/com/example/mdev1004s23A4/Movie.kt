package com.example.mdev1004s23A4

// -------------------------------------------------------------
// - Robert Bettinelli - MDEV1004 - S2023
// - 090003683@student.georgianc.on.ca
// -------------------------------------------------------------
// 08/23/2023 - RBettinelli - Header and Documentation Added
// -------------------------------------------------------------
// Contains Data Structures for JSON Calls.
// -------------------------------------------------------------
import java.io.Serializable

data class Movie(
    val _id: String,
    val movieID: Int,
    val title: String,
    val studio: String,
    val genres: List<String>,
    val directors: List<String>,
    val writers: List<String>,
    val actors: List<String>,
    val year: Int,
    val length: String,
    val shortDescription: String,
    val mpaRating: String,
    val criticsRating: Double,
    val posterLink: String
) : Serializable

data class MovieResponse(
    val success: Boolean,
    val msg: String,
    val data: MutableList<Movie>
)

data class MovieResponseWrapper(
    val success: Boolean,
    val msg: String,
    val data: Movie
)

data class MovieDeleteWrapper(
    val success: Boolean,
    val msg: String,
    val data: String
)

data class PaymentWrapper(
    val success: Boolean,
    val msg: String,
    val clientSecret: String,
    val customer: String,
    val publishableKey: String
)