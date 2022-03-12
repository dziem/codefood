package com.akmal.codefood.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthToken {
    private String token;

    public AuthToken(String token) {
        this.token = token;
    }
}
