package com.green.greengram;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) // 무슨의미냐면 애노테이션의 라이프사이클
@WithSecurityContext(factory = WithAuthMockUserSecurityContextFactory.class)
public @interface WithAuthUser {

    long signedUserId() default 1L;
    String[] roles() default {"ROLE_USER"};
}
