package com.dmko.iconf

import org.springframework.stereotype.Service

@Service
class ConferencesDao {

    private val conferences = ArrayList<Conference>()

    init {
        for (i in 0..10) {
            conferences.add(Conference("id#$i", "Conference#$i", "description#$i"))
        }
    }

    fun getConferences() = conferences
}