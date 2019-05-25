package com.dmko.iconf.pictures.entities

data class Comment(
        val id: Long,
        val date: Long,
        val authorName: String,
        val body: String
)
