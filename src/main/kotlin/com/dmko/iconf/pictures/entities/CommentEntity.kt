package com.dmko.iconf.pictures.entities

import java.time.LocalDate

data class CommentEntity(
        val id: Long,
        val date: LocalDate,
        val authorId: Long,
        val body: String
)
