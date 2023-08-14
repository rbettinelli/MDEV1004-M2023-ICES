package com.example.mdev1004s23FINAL

// -------------------------------------------------------------
// - Robert Bettinelli - MDEV1004 - S2023
// - 090003683@student.georgianc.on.ca
// -------------------------------------------------------------
// 08/23/2023 - RBettinelli - Header and Documentation Added
// -------------------------------------------------------------
// Contains Data Structures for JSON Calls.
// -------------------------------------------------------------
import java.io.Serializable

data class MyItem(
    val _id: String,
    val name: String,
    val type: String,
    val dateBuilt: String,
    val city: String,
    val country: String,
    val description: String,
    val architects: List<String>,
    val cost: String,
    val website: String,
    val imageURL: String
) : Serializable

data class ItemResponse(
    val success: Boolean,
    val msg: String,
    val data: MutableList<MyItem>
)

data class ItemResponseWrapper(
    val success: Boolean,
    val msg: String,
    val data: MyItem
)

data class ItemDeleteWrapper(
    val success: Boolean,
    val msg: String,
    val data: String
)
