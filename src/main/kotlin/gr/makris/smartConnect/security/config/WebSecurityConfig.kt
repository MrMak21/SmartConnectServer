package gr.makris.smartConnect.security.config

import gr.makris.smartConnect.security.userDetailsService.AppUserDetailsService
import lombok.AllArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@AllArgsConstructor
@EnableWebSecurity
class WebSecurityConfig: WebSecurityConfigurerAdapter() {

    private var appUserService: AppUserDetailsService = AppUserDetailsService()
    private var passwordEncoder: BCryptPasswordEncoder = BCryptPasswordEncoder()

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/smartConnect/**")
            .permitAll()
            .anyRequest()
            .authenticated()

    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.authenticationProvider(daoAuthenticationProvider())
    }

    @Bean
    fun daoAuthenticationProvider(): DaoAuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordEncoder)
        provider.setUserDetailsService(appUserService)
        return provider
    }
}