package com.dmko.iconf.users.auth

import com.dmko.iconf.users.TokenProvider
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthorizationFilter(private val tokenProvider: TokenProvider, authManager: AuthenticationManager) : BasicAuthenticationFilter(authManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header: String? = request.getHeader(AuthConstants.HEADER_STRING)
        if (header == null || !header.startsWith(AuthConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response)
            return
        }
        val auth = getAuth(request)
        SecurityContextHolder.getContext().authentication = auth
        chain.doFilter(request, response)
    }

    private fun getAuth(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token: String? = request.getHeader(AuthConstants.HEADER_STRING)
        return if (token != null) {
            tokenProvider.decodeToken(token)
        } else {
            null
        }
    }
}