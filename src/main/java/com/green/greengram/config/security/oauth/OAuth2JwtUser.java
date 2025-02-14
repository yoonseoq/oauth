package com.green.greengram.config.security.oauth;

import com.green.greengram.config.jwt.JwtUser;
import lombok.Getter;

import java.util.List;

@Getter
public class OAuth2JwtUser extends JwtUser {
    private final String nickname;
    private final String pic;

    public OAuth2JwtUser(String nickname, String pic, long signedUserId, List<String> roles) {
        super(signedUserId, roles);
        this.nickname = nickname;
        this.pic = pic;
    }
}
