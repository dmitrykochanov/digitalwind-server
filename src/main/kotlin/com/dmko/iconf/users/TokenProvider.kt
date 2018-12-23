package com.dmko.iconf.users

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.dmko.iconf.base.BaseResponse
import com.dmko.iconf.users.auth.AuthConstants
import com.dmko.iconf.users.entities.AuthResponse
import com.dmko.iconf.users.entities.RoleEntity
import com.dmko.iconf.users.entities.UserEntity
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class TokenProvider(private val mapper: ObjectMapper) {
    fun createToken(user: UserEntity, roles: List<RoleEntity>): String {
        val rolesString = mapper.writeValueAsString(roles)
        return JWT.create()
                .withSubject(user.email)
                .withClaim(AuthConstants.AUTHORITIES_KEY, rolesString)
                .withExpiresAt(Date(Long.MAX_VALUE))
                .sign(Algorithm.HMAC512(AuthConstants.SECRET.toByteArray()))
    }

    fun createAuthResponse(user: UserEntity, roles: List<RoleEntity>): ResponseEntity<BaseResponse<AuthResponse>> {
        val token = createToken(user, roles)
        val headers = HttpHeaders()
        headers.set(AuthConstants.HEADER_STRING, AuthConstants.TOKEN_PREFIX + token)
        val response = AuthResponse(user.id, token, user.email, user.firstName, user.lastName, roles)
        return ResponseEntity(BaseResponse(response, true), headers, HttpStatus.OK)
    }

    fun decodeToken(token: String): UsernamePasswordAuthenticationToken? {
        val parsedToken = JWT.require(Algorithm.HMAC512(AuthConstants.SECRET.toByteArray()))
                .build()
                .verify(token.replace(AuthConstants.TOKEN_PREFIX, ""))

        if (parsedToken.subject != null) {

            val rolesString = parsedToken.getClaim(AuthConstants.AUTHORITIES_KEY).asString()
            val roles: List<RoleEntity> = mapper.readValue(rolesString,
                    mapper.typeFactory.constructCollectionType(List::class.java, RoleEntity::class.java))
            val userRoles = roles.stream().map { SimpleGrantedAuthority(it.name) }.collect(Collectors.toList())

            return UsernamePasswordAuthenticationToken(parsedToken.subject, null, userRoles)
        }
        return null
    }
}