package com.dmko.iconf.users.auth

import com.dmko.iconf.users.UsersDao
import com.dmko.iconf.users.entities.UserEntity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserDetailsServiceImpl(private val usersDao: UsersDao) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val userEntity: UserEntity? = usersDao.findUserByEmail(username)
        if (userEntity != null) {
            return User(userEntity.email, userEntity.password, Collections.emptyList())
        }
        throw IllegalArgumentException("User with email $username not found")
    }
}