package com.example.thiscode.security.common;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class CommonAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;

    private Object credentials;

    public CommonAuthenticationToken(String email, String password) {
        super(null);
        this.principal = email;
        this.credentials = password;
        super.setAuthenticated(false);
    }

    public CommonAuthenticationToken(Object principal) {
        super(List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.principal = principal;
        this.credentials = null;
        super.setAuthenticated(true);
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
