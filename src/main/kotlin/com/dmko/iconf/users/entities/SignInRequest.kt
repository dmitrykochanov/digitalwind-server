package com.dmko.iconf.users.entities

data class SignInRequest(
        val email: String,
        val password: String
)