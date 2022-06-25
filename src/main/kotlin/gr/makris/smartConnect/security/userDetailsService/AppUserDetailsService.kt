package gr.makris.smartConnect.security.userDetailsService

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

class AppUserDetailsService: UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails? {
        return null
    }
}