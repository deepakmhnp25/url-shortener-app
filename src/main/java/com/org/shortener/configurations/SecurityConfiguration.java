package com.org.shortener.configurations;

import com.org.shortener.constants.EndpointConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security configuration is handled here.
 * Custom authentication which can match the user details to a datastore
 * Url specific authorization given to certain urls.
 * Password is encoded and matched upon login.
 *
 * @author Deepak Mohan
 * @version 0.1
 * @since 2022-05-28
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    UserDetailsService userDetailsService;

    /**
     * Overriding the spring security configure method
     * to set up custom user authentication service
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * This method configures the url specific authorization and
     * success redirection of successful login attempt.
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/").permitAll()
                .antMatchers(EndpointConstants.URL_SHORTENER).authenticated()
                .antMatchers(EndpointConstants.SHORTEN).authenticated()
                .and().formLogin().defaultSuccessUrl(EndpointConstants.URL_SHORTENER);
    }

    /**
     * Configuring the password encrption strategy
     *
     * @return bCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
