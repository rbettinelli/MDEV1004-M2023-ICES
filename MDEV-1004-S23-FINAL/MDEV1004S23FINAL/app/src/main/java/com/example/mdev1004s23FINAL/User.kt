package com.example.mdev1004s23FINAL

// -------------------------------------------------------------
// - Robert Bettinelli - MDEV1004 - S2023
// - 090003683@student.georgianc.on.ca
// -------------------------------------------------------------
// 08/23/2023 - RBettinelli - Header and Documentation Added
// -------------------------------------------------------------
// Contains Data Structures for JSON Calls.
// -------------------------------------------------------------

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

data class RegisterResponse(
    val success: Boolean,
    val msg: String
)

