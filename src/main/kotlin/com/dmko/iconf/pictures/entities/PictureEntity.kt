package com.dmko.iconf.pictures.entities

data class PictureEntity(
        val id: Long,
        val title: String,
        val imageUrl: String = "",
        val number: Long,
        val city: String,
        val description: String
)
