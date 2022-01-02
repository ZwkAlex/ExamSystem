package com.group.exam.filter;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.group.exam.config.CusAuthenticationManager;
import com.group.exam.config.UserAuthenticationFailureHandler;
import com.group.exam.config.UserAuthenticationSuccessHandler;
import com.group.exam.model.entity.User;
import com.group.exam.util.RequestUtil;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Component
public class AuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * @param authenticationManager :             认证管理器
     * @param userAuthenticationSuccessHandler : 认证成功处理
     * @param userAuthenticationFailureHandler : 认证失败处理
     */
    public AuthenticationProcessingFilter(CusAuthenticationManager authenticationManager, UserAuthenticationSuccessHandler userAuthenticationSuccessHandler, UserAuthenticationFailureHandler userAuthenticationFailureHandler) {
        super(new AntPathRequestMatcher("/login", "POST"));
        this.setAuthenticationManager(authenticationManager);
        this.setAuthenticationSuccessHandler(userAuthenticationSuccessHandler);
        this.setAuthenticationFailureHandler(userAuthenticationFailureHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (request.getContentType() == null
//                || !request.getContentType().contains(Constants.REQUEST_HEADERS_CONTENT_TYPE)
        ) {
            throw new AuthenticationServiceException("请求头类型不支持: " + request.getContentType());
        }

        UsernamePasswordAuthenticationToken authRequest;
        User user;
        String  str = "";
        try{
            str = RequestUtil.charReader(request);
            user = JSON.parseObject(str, User.class);
        }catch (JSONException e) {
            Map<String,String> map = RequestUtil.parseParam(str);
            user = new User();
            user.setId(map.get("id"));
            user.setPassword(map.get("password"));
            user.setRole(map.get("role"));
        }catch(IOException e) {
            throw new AuthenticationServiceException(e.getMessage());
        }

        try {
            Collection<GrantedAuthority> auth = new ArrayList<>();
            auth.add(new SimpleGrantedAuthority(user.getRole().toUpperCase()));
            authRequest = new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword(), auth);
            authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
        } catch (Exception e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}