package com.example.spacecraft10.data.responseSpacecraft

data class SpacecraftResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Results>
)
data class Results(
    val agency: Agency,
    val family: List<Family>,
    val id: Int,
    val image: Image,
    val in_use: Boolean,
    val name: String,
)

data class Agency(
    val abbrev: String,
    val id: Int,
    val name: String,
)

data class Family(
    val description: String,
    val id: Int,
)

data class Image(
    val id: Int,
    val image_url: String,
    val thumbnail_url: String,
)