package com.group.exam.controller;

import com.group.exam.config.CusAuthenticationManager;
import com.group.exam.model.entity.SecurityUser;
import com.group.exam.model.entity.User;
import com.group.exam.model.requestModel.PasswordChangeRequest;
import com.group.exam.model.responseModel.ResponseModel;
import com.group.exam.service.impl.UserService;
import com.group.exam.util.AuthUtil;
import com.group.exam.util.JwtTokenUtil;
import com.group.exam.util.ResponseUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *  date:2021/12/22
 *  author:
 */
@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;
    @Resource
    private CusAuthenticationManager cusAuthenticationManager;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private AuthUtil authUtil;

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> login(@RequestBody User user){
        log.info(String.format("登入:%s ", user.getId()));
        try {
            Collection<GrantedAuthority> auth = new ArrayList<>();
            auth.add(new SimpleGrantedAuthority(user.getRole().toUpperCase()));
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword(), auth);
            authentication = cusAuthenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Map<String,String> token = new HashMap<String, String>(){{
                put("token", authUtil.getToken());
            }};
            return ResponseEntity.ok().body(ResponseUtil.success(token));
        } catch (AuthenticationException e) {
            ResponseModel result;
            if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
                result = ResponseUtil.error(e.getMessage());
            } else if (e instanceof LockedException) {
                result = ResponseUtil.error("账户被锁定，请联系管理员!");
            } else if (e instanceof CredentialsExpiredException) {
                result = ResponseUtil.error("证书过期，请联系管理员!");
            } else if (e instanceof AccountExpiredException) {
                result = ResponseUtil.error("账户过期，请联系管理员!");
            } else if (e instanceof DisabledException) {
                result = ResponseUtil.error("账户被禁用，请联系管理员!");
            } else {
                result = ResponseUtil.error("登录失败，用户名或密码错误。");
            }
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(ResponseUtil.error(e.getMessage()));
        }
    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> logOut(){
        SecurityUser securityUser = (SecurityUser)(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        User user = securityUser.getCurrentUserInfo();
        String msg = String.format("登出成功:%s ", user.getId());
        log.info(msg);
        user.setToken("NULL");
        userService.updateToken(user);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().body(ResponseUtil.success(msg));
    }

    @RequestMapping(value = "/user/changePassword", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> changePassword(@RequestBody PasswordChangeRequest request){
        log.info(String.format("修改密码:%s ", SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        return ResponseEntity.ok().body(userService.changePassword(request));
    }

    @RequestMapping(value = "/user/info",method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> userInfo(){
        try {
            String id = authUtil.getUserID();
            log.info(String.format("获取用户信息:%s ", id));
            return ResponseEntity.ok().body(userService.getUserInfo(id));
        }catch (Exception e){
            return ResponseEntity.ok().body(ResponseUtil.error(e.getMessage()));
        }
    }
}
