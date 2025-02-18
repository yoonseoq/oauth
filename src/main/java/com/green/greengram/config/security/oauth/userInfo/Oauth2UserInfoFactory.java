package com.green.greengram.config.security.oauth.userInfo;

import com.green.greengram.config.security.SignInProviderType;
import org.springframework.stereotype.Component;

import java.util.Map;
/*
    Google, Naver, Kakao 플랫폼에서 받은 유저정보 JSON을
    HashMap 형식으로 파싱,
    HashMap을 이용한 규격화된 객체로 파싱,
 */
@Component
public class Oauth2UserInfoFactory { //클래스명에 factory 있으면 객체생성을 의미함
   public Oauth2UserInfo getOauth2UserInfo(SignInProviderType signInProviderType, Map<String, Object> attributes) {
       return switch (signInProviderType){
           case KAKAO -> new KakaoOAuth2UserInfo(attributes);
           case NAVER -> new NaverOAuth2UserInfo(attributes);
           case GOOGLE -> new GoogleOAuth2UserInfo(attributes);
           default -> null;
       };
   }
}
