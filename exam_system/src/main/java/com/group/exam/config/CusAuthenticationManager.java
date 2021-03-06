package com.group.exam.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

@Component
public class CusAuthenticationManager implements AuthenticationManager {

    @Resource
    private UserAuthenticationProvider userAuthenticationProvider;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication result = userAuthenticationProvider.authenticate(authentication);
        if (Objects.nonNull(result)) {
            return result;
        }
        throw new ProviderNotFoundException("授权失败！");
    }
}