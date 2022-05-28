package com.shortcut.shortener.domains;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

/**
 * LoginDetails for security module
 *
 * @author Deepak Mohan
 * @version 0.1
 * @since 2022-05-28
 */
public class LoginDetails implements UserDetails {

    private String username;
    private String password;
    private boolean nonExpired;
    private boolean nonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public LoginDetails(String username, String password, boolean nonExpired,
                        boolean nonLocked, boolean credentialsNonExpired, boolean enabled) {
        this.username = username;
        this.password = password;
        this.nonExpired = nonExpired;
        this.nonLocked = nonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return nonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return nonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}