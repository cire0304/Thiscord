package com.example.thiscode.security.ajax;

import com.example.thiscode.security.common.CommonAuthenticationToken;
import com.example.thiscode.security.model.PrincipalUser;
import com.example.thiscode.security.model.ProviderUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {

    private final AjaxUserDetailsService ajaxUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = (String) authentication.getCredentials();

        PrincipalUser providerUser = (PrincipalUser) ajaxUserDetailsService.loadUserByUsername(email);

        if (!providerUser.getPassword().equals(password)) {
            throw new BadCredentialsException("Password is not correct");
        }

        return new CommonAuthenticationToken(providerUser);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(CommonAuthenticationToken.class);
    }
}
