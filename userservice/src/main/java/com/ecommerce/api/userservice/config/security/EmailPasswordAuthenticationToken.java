package com.ecommerce.api.userservice.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class EmailPasswordAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 570L;
    private final Object principal;
    private final Object credentials;

    public EmailPasswordAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object principal, Object credentials, Boolean authenticated) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(authenticated);
    }

    public EmailPasswordAuthenticationToken(Object principal, Object credentials, Boolean authenticated) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(authenticated);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

}
