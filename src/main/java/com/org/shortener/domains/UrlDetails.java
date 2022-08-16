package com.org.shortener.domains;

import lombok.Getter;
import lombok.Setter;

/**
 * Uri details to be shortened
 *
 * @author Deepak Mohan
 * @version 0.1
 * @since 2022-05-28
 */
@Getter
@Setter
public class UrlDetails {
    private String url;
    private String shortenedUrl;
}
