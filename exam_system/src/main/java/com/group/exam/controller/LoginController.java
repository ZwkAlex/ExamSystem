package com.group.exam.controller;

import com.group.exam.model.requestModel.PasswordChangeRequest;
import com.group.exam.model.responseModel.ResponseModel;
import com.group.exam.service.impl.UserService;
import com.group.exam.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 *  date:2021/12/22
 *  author:
 */
@RestController
public class LoginController{
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private UserService userService;

    @RequestMapping(value = "/user/logout", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> logOut(HttpSession session){
        String msg = String.format("登出成功:%s ", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        log.info(msg);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().body(ResponseUtil.success(msg));
    }

    @RequestMapping(value = "/user/changePassword", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> changePassword(@RequestBody PasswordChangeRequest request, HttpSession session){
        log.info(String.format("修改密码:%s ", SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        return ResponseEntity.ok().body(userService.changePassword(request));
    }


}
