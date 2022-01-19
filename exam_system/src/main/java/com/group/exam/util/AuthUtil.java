package com.group.exam.util;

import com.group.exam.model.entity.SecurityUser;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class AuthUtil {
    @Resource
    private JwtTokenUtil jwtTokenUtil;

    public String getToken(Authentication authentication) throws Exception {
        if(authentication != null){
            SecurityUser securityUser = (SecurityUser)(authentication.getPrincipal());
            return securityUser.getCurrentUserInfo().getToken();
        }else{
            throw new Exception("错误的Authentication");
        }
    }

    public String getToken() throws Exception {
        return getToken(SecurityContextHolder.getContext().getAuthentication());
    }

    public String getUserID() throws Exception {
        return jwtTokenUtil.getClaimFromToken(getToken(), Claims::getId);
    }
}
