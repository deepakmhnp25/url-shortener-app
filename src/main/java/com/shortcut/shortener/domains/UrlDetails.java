package com.shortcut.shortener.domains;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UrlDetails {
    private String url;
    private String shortenedUrl;
}
