package com.roman.entity

data class RecommendationsDirection(
    val id: Int,
    val idImage: Int,
    val name: String,
    val description: String = "Популярное направление"
)