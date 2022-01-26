package com.group.exam.filter;

import com.group.exam.model.entity.SecurityUser;
import com.group.exam.service.impl.UserService;
import com.group.exam.util.JwtTokenUtil;
import com.group.exam.util.ResponseUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFilter extends OncePerRequestFilter {
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private UserService userService;

    private static final String[] NOT_FILTER = {"/user/login"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 从请求头中获取token字符串并解析（JwtManager之前文章有详解，这里不多说了）
        if(isNeedFilter(request)){
            String token = request.getHeader("Authorization");
            if (token == null) {
                ResponseUtil.ok(response, ResponseUtil.tokenError("未携带Token Header"));
                return;
            }
            if (jwtTokenUtil.isTokenExpired(token)) {
                ResponseUtil.ok(response, ResponseUtil.tokenError("Token Expired"));
                return;
            }
            try {
                String id = jwtTokenUtil.getClaimFromToken(token, Claims::getId);
                if (id != null) {
                    SecurityUser user = (SecurityUser) userService.loadUserByUsername(id);
                    if(user.getCurrentUserInfo().getToken().equals(token)) {
                        Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }else{
                        throw new Exception();
                    }
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                ResponseUtil.ok(response, ResponseUtil.tokenError("错误的Token"));
                e.printStackTrace();
            }
            try{
                chain.doFilter(request, response);
            }catch (Exception e) {
                e.printStackTrace();
                ResponseUtil.ok(response, ResponseUtil.error("服务器发生未知的错误"));
            }

        }else {
            chain.doFilter(request, response);
        }
    }
    public boolean isNeedFilter(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        for (String s : NOT_FILTER) {
            if (requestURI.contains(s)){
                return false;
            }
        }
        return true;
    }
}