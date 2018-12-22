package com.dmko.iconf.users.entities

data class UserEntity(
        val id: Long = 0,
        val email: String,
        val firstName: String,
        val lastName: String,
        val password: String
)