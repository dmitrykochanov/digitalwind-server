package com.dmko.iconf

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ConferencesController(private var conferencesDao: ConferencesDao) {

    @GetMapping("/conferences")
    fun getConferences() = conferencesDao.getConferences()
}