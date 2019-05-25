package com.dmko.iconf.pictures.entities

import java.time.LocalDate

data class Comment(
        val id: Long,
        val date: LocalDate,
        val authorName: String,
        val body: String
)
