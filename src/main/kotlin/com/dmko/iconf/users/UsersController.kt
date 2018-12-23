package com.dmko.iconf.users

import com.dmko.iconf.base.BaseResponse
import com.dmko.iconf.users.entities.AuthResponse
import com.dmko.iconf.users.entities.SignInRequest
import com.dmko.iconf.users.entities.SignUpRequest
import com.dmko.iconf.users.entities.UserEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("/users")
class UsersController(
        private val usersDao: UsersDao,
        private val bCryptPasswordEncoder: BCryptPasswordEncoder,
        private val tokenProvider: TokenProvider
) {

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
            usersDao.addRoleToUser(insertedUser.id, 1)
            val roles = usersDao.getUserRoles(insertedUser.id)

            tokenProvider.createAuthResponse(insertedUser, roles)
        } catch (t: Throwable) {
            ResponseEntity(BaseResponse<AuthResponse>(null, false), HttpStatus.BAD_REQUEST)
        }
    }

    @CrossOrigin
    @PostMapping("/sign-in")
    fun signIn(@RequestBody signInRequest: SignInRequest): ResponseEntity<BaseResponse<AuthResponse>> {
        val user = usersDao.findUserByEmail(signInRequest.email)

        return if (user != null && BCrypt.checkpw(signInRequest.password, user.password)) {
            val roles = usersDao.getUserRoles(user.id)
            tokenProvider.createAuthResponse(user, roles)
        } else {
            ResponseEntity(BaseResponse<AuthResponse>(null, false), HttpStatus.BAD_REQUEST)
        }
    }
}