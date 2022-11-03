package com.example.KursovaWebSite.domain.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN, AUTHOR;

    @Override
    public String getAuthority() {
        return name();
    }
}
