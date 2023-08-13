package com.example.mdev1004s23ice7c

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