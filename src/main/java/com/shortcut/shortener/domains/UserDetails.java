package com.shortcut.shortener.domains;

import lombok.Getter;
import lombok.Setter;

/**
 * User details used for login
 *
 * @author Deepak Mohan
 * @version 0.1
 * @since 2022-05-28
 */
@Getter
@Setter
public class UserDetails {
    private String email;
    private String password;
}
