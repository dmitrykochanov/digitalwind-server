package com.dmko.iconf.pictures.entities

data class CommentEntity(
        val id: Long = 0,
        val pictureId: Long,
        val date: Long,
        val authorId: Long,
        val body: String
)
