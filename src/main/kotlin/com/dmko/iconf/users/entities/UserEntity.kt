package com.dmko.iconf.users.entities

data class UserEntity(
        val id: Long = 0,
        val login: String,
        val password: String
)
