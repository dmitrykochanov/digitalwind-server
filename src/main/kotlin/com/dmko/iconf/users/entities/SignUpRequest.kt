package com.dmko.iconf.users.entities

data class SignUpRequest(
        val email: String,
        val firstName: String,
        val lastName: String,
        val password: String
)