package vn.edu.iuh.fit.bookingservice.configs;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import vn.edu.iuh.fit.bookingservice.dtos.PrincipalAuthentication;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomJwtGrantedAuthoritiesConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt source)
    {
        var resourceAccess = new HashMap<>(source.getClaims());
        String userId = (String) resourceAccess.get("userId");
        String username = (String) resourceAccess.get("sub");


        PrincipalAuthentication principalAuthentication = new PrincipalAuthentication(userId,username);
        Collection<GrantedAuthority> authorities = new JwtGrantedAuthoritiesConverter().convert(source);
        authorities.addAll(extractResourceRoles(source));
        return new UsernamePasswordAuthenticationToken(principalAuthentication, source, authorities);
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt)
    {
        Object scopeObject = jwt.getClaim("scope");

        String scopeString = scopeObject.toString();
        String[] scopes = scopeString.split(" ");


        return Arrays.stream(scopes)
                .map(scope -> {
                    if (scope.startsWith("ROLE_")) {
                        return new SimpleGrantedAuthority(scope); // Giữ nguyên ROLE_
                    } else {
                        return new SimpleGrantedAuthority("ROLE_" + scope); // Thêm ROLE_ nếu thiếu
                    }
                })
                .collect(Collectors.toSet());


    }
}