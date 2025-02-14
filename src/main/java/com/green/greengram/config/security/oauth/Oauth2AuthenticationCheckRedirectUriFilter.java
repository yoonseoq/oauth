package com.green.greengram.config.security.oauth;

import com.green.greengram.common.GlobalOauth2;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class Oauth2AuthenticationCheckRedirectUriFilter extends OncePerRequestFilter {

    private final GlobalOauth2 globalOauth2;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*
            호스트 주소값을 제외한 요청한 URI
            예) http://localhost:8080/aaa/bbb
            호스트 주소값: http://localhost:8080
            제외한 요청한 URI: aaa/bbb
            String redirectUri = abc;
         */
        String requestUri = request.getRequestURI(); // 요청한 URI
        log.info("request uri: {}", requestUri);
        if (requestUri.startsWith(globalOauth2.getBaseUri())) { //소셜로그인 요청한 것이라면
            String redirectUri = request.getParameter("redirect_uri");
            if (redirectUri != null && !hasAuthorizedRedirectUri(redirectUri)) { //약속한 redirect_uri값이 아니라면
                String errRedirectUrl = UriComponentsBuilder.fromUriString("/err_message")
                                                            .queryParam("message","유효한 RedirectURL이 아닙니다").encode()
                                                            .toUriString();
                //errRedirectUrl = "/err_message? message = 유효한 Redirect URL이 아닙니다"
                response.sendRedirect(errRedirectUrl);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean hasAuthorizedRedirectUri(String redirectUri) {
        for (String uri : globalOauth2.getAuthorizedRedirectUris()) {
            if (uri.equals(redirectUri)) {
                return true;
            }
        }
        return false;
    }
    //상속받은 부모한테 추상메소드가 있으면
}
