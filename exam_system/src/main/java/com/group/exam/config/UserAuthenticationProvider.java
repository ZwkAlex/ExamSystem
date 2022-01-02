package com.group.exam.config;

import com.group.exam.model.entity.SecurityUser;
import com.group.exam.service.impl.LoginService;
import com.group.exam.service.impl.UserService;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Iterator;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private UserService userDetailsService;
    @Resource
    private LoginService loginService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取前端表单中输入后返回的用户名、密码
        String id = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        @SuppressWarnings("unchecked")
        Collection<GrantedAuthority> auth = (Collection<GrantedAuthority>) authentication.getAuthorities();
        Iterator<GrantedAuthority> i = auth.iterator();
        String role;
        if(i.hasNext()){
            GrantedAuthority a = i.next();
            role = a.getAuthority();
        }else{
            throw new BadCredentialsException("输入角色为空！");
        }

        SecurityUser userInfo = (SecurityUser) userDetailsService.loadUserByUsername(id);

        if (!loginService.checkRole(userInfo.getCurrentUserInfo(),role)) {
            throw new BadCredentialsException("当前身份认证失败！请确认选择了正确的登录入口。");
        }

        if(!userInfo.getPassword().equals(password)){
            throw new BadCredentialsException("密码错误！");
        }


        // 前后端分离情况下 处理逻辑...
        // 更新登录令牌 - 之后访问系统其它接口直接通过token认证用户权限...
//        String token = PasswordUtils.encodePassword(System.currentTimeMillis() + userInfo.getCurrentUserInfo().getSalt(), userInfo.getCurrentUserInfo().getSalt());
//        User user = userMapper.selectById(userInfo.getCurrentUserInfo().getId());
//        user.setToken(token);
//        userMapper.updateById(user);
//        userInfo.getCurrentUserInfo().setToken(token);

        return new UsernamePasswordAuthenticationToken(userInfo.getUsername(), userInfo.getPassword(), userInfo.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}