package com.dmko.iconf.users.entities

data class AuthResponse(
        val id: Long,
        val token: String,
        val email: String,
        val firstName: String,
        val lastName: String,
        val roles: List<RoleEntity>
)