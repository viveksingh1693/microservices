package com.viv.gatewayserver.config;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @SuppressWarnings("unchecked")
    @Override
    @Nullable
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
        if (realmAccess == null || realmAccess.isEmpty()) {
            return Collections.emptyList();
        }
        Collection<String> roles = (Collection<String>) realmAccess.get("roles");
        if (roles == null || roles.isEmpty()) {
            return Collections.emptyList();
        }
        return roles.stream()
                .map(roleName -> "ROLE_" + roleName) // Prefix with "ROLE_"
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
