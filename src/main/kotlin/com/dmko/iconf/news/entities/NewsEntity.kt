package com.dmko.iconf.news.entities

data class NewsEntity(
        val id: Long = 0,
        val title: String,
        val body: String,
        val imageUrl: String = ""
)
