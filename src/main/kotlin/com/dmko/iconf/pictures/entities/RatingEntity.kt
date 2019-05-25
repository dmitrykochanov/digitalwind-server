package com.dmko.iconf.pictures.entities

data class RatingEntity(
        val userId: Long,
        val pictureId: Long,
        val rate: Int
)
