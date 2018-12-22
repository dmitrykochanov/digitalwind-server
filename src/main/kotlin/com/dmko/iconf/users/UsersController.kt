package com.dmko.iconf.users

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.dmko.iconf.base.BaseResponse
import com.dmko.iconf.users.auth.AuthConstants
import com.dmko.iconf.users.entities.AuthResponse
import com.dmko.iconf.users.entities.SignInRequest
import com.dmko.iconf.users.entities.SignUpRequest
import com.dmko.iconf.users.entities.UserEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.stream.Collectors

@RestController()
@RequestMapping("/users")
class UsersController(private var usersDao: UsersDao, private var bCryptPasswordEncoder: BCryptPasswordEncoder) {

    @CrossOrigin
    @PostMapping("/sign-up")
    fun signUp(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<BaseResponse<AuthResponse>> {
        return try {
            val newUser = UserEntity(
                    email = signUpRequest.email,
                    firstName = signUpRequest.firstName,
                    lastName = signUpRequest.lastName,
                    password = bCryptPasswordEncoder.encode(signUpRequest.password)
            )
            usersDao.insertUser(newUser)

            val insertedUser = usersDao.findUserByEmail(signUpRequest.email)!!
            usersDao.addRoleToUser(insertedUser.id, 2)
            val roles = usersDao.getUserRoles(insertedUser.id).stream().map { it.name }.collect(Collectors.joining(","))
            val token = JWT.create()
                    .withSubject(insertedUser.email)
                    .withClaim(AuthConstants.AUTHORITIES_KEY, roles)
                    .withExpiresAt(Date(Long.MAX_VALUE))
                    .sign(Algorithm.HMAC512(AuthConstants.SECRET.toByteArray()))

            val headers = HttpHeaders()
            headers.set(AuthConstants.HEADER_STRING, AuthConstants.TOKEN_PREFIX + token)

            val response = AuthResponse(insertedUser.id, token, insertedUser.email, insertedUser.firstName, insertedUser.lastName)
            ResponseEntity(BaseResponse(response, true), headers, HttpStatus.OK)
        } catch (t: Throwable) {
            ResponseEntity(BaseResponse<AuthResponse>(null, false), HttpStatus.BAD_REQUEST)
        }
    }

    @CrossOrigin
    @PostMapping("/sign-in")
    fun signIn(@RequestBody signInRequest: SignInRequest): ResponseEntity<BaseResponse<AuthResponse>> {
        val user = usersDao.findUserByEmail(signInRequest.email)

        return if (user != null && BCrypt.checkpw(signInRequest.password, user.password)) {

            val roles = usersDao.getUserRoles(user.id).stream().map { it.name }.collect(Collectors.joining(","))
            val token = JWT.create()
                    .withSubject(user.email)
                    .withClaim(AuthConstants.AUTHORITIES_KEY, roles)
                    .withExpiresAt(Date(Long.MAX_VALUE))
                    .sign(Algorithm.HMAC512(AuthConstants.SECRET.toByteArray()))

            val headers = HttpHeaders()
            headers.set(AuthConstants.HEADER_STRING, AuthConstants.TOKEN_PREFIX + token)

            val response = AuthResponse(user.id, token, user.email, user.firstName, user.lastName)
            ResponseEntity(BaseResponse(response, true), headers, HttpStatus.OK)
        } else {
            ResponseEntity(BaseResponse<AuthResponse>(null, false), HttpStatus.BAD_REQUEST)
        }
    }
}