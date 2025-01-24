package com.green.greengram;

import com.green.greengram.config.jwt.JwtUser;
import com.green.greengram.config.security.MyUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithAuthMockUserSecurityContextFactory implements WithSecurityContextFactory<WithAuthUser> {
    @Override
    public SecurityContext createSecurityContext(final WithAuthUser annotation) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        List<String> roles = List.of(annotation.roles());

        JwtUser jwtUser = new JwtUser();
        jwtUser.setSignedUserId(annotation.signedUserId());
        jwtUser.setRoles(roles);

        MyUserDetails myUserDetails = new MyUserDetails();
        myUserDetails.setJwtUser(jwtUser);


        Authentication auth = new UsernamePasswordAuthenticationToken(myUserDetails, null, myUserDetails.getAuthorities());
        securityContext.setAuthentication(auth);
        return securityContext;
    }
}
