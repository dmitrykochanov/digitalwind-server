package com.dmko.iconf.pictures.entities

data class Picture(
        val id: Long,
        val title: String,
        val imageUrl: String = "",
        val number: Long,
        val city: String,
        val rate: Double,
        val description: String,
        val comments: List<Comment>
)
