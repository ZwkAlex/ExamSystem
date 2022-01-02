package com.group.exam.controller;

import com.group.exam.model.responseModel.ResponseModel;
import com.group.exam.service.impl.LoginService;
import com.group.exam.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
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
    private LoginService loginService;

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> LogOut(HttpSession session){
        String msg = String.format("登出成功:%s ", loginService.getAttribute(session));
        log.info(msg);
        loginService.removeAttribute(session);
        return ResponseEntity.ok().body(ResponseUtil.success(msg));
    }


}
