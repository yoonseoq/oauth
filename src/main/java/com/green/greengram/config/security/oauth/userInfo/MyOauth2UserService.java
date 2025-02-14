package com.green.greengram.config.security.oauth.userInfo;

import ch.qos.logback.core.joran.conditional.IfAction;
import com.green.greengram.config.jwt.JwtUser;
import com.green.greengram.config.security.MyUserDetails;
import com.green.greengram.config.security.SignInProviderType;
import com.green.greengram.config.security.oauth.OAuth2JwtUser;
import com.green.greengram.config.security.oauth.Oauth2UserInfoFactory;
import com.green.greengram.entity.User;
import com.green.greengram.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyOauth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final Oauth2UserInfoFactory oauth2UserInfoFactory;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest req){
        try {
            return process(req);
        }catch (AuthenticationException ex){
            throw ex;
        }catch (Exception ex){
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest req) {
        OAuth2User oAuth2User = super.loadUser(req);
        /*
        req.getClientRegistration().getRegistrationId() 소셜로그인 신청한 플랫폼 문자열 값이 넘어온다
        플랫폼 문자열 값은 registration 아래에 있는 속성값들(google, kakao, naver)
         */
        SignInProviderType signInProviderType = SignInProviderType.valueOf(req.getClientRegistration()
                .getRegistrationId()
                .toUpperCase());
        // 통신할때 가져오는 것들

        //사용하기 편하도록 규격화된 객체로 변환
        Oauth2UserInfo oauth2UserInfo = oauth2UserInfoFactory.getOauth2UserInfo(signInProviderType, oAuth2User.getAttributes());

        //기존에 회원가입이 되어있는지 체크
        User user = userRepository.findByUidAndProviderType(oauth2UserInfo.getId(), signInProviderType);
        if (user == null) { // 최초 로그인 상황 > 회원가입 처리
            user = new User();
            user.setUid(oauth2UserInfo.getId());
            user.setProviderType(signInProviderType);
            user.setUpw("");
            user.setNickName(oauth2UserInfo.getName());
            user.setPic(oauth2UserInfo.getProfileImageUrl());
            userRepository.save(user);
        }

//       ;
//        JwtUser jwtUser = new JwtUser();
//        jwtUser.setSignedUserId(user.getUserId());
//        jwtUser.setRoles(new ArrayList<>(1));
//        jwtUser.getRoles().add("ROLE_USER");

        OAuth2JwtUser oAuth2JwtUser = new OAuth2JwtUser(user.getNickName(),
                user.getPic(),
                user.getUserId(),
                Arrays.asList("ROLE_USER")
        );

        MyUserDetails myUserDetails = new MyUserDetails();
        myUserDetails.setJwtUser(oAuth2JwtUser);

        return myUserDetails;
    }
}
