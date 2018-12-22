package com.dmko.iconf.conferences.entities

data class ConferenceEntity(
        var id: Long,
        var name: String,
        var description: String,
        val imageUrl: String
)