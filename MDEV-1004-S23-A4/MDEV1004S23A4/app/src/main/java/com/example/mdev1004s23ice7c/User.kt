package com.example.mdev1004s23ice7c

data class LoginResponse(
    val success: Boolean,
    val msg: String,
    val user: User,
    val token: String
)

data class User(
    val id: String,
    val displayName: String,
    val username: String,
    val emailAddress: String
)
