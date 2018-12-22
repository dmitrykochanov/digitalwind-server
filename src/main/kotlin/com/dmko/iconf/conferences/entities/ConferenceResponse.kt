package com.dmko.iconf.conferences.entities

data class ConferenceResponse(
        val id: Long, val name: String,
        val description: String,
        val imageUrl: String,
        val participantEntities: List<ParticipantEntity>
)