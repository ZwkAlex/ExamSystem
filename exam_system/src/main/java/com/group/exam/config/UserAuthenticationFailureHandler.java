
package com.group.exam.config;

import com.group.exam.model.responseModel.ResponseModel;
import com.group.exam.util.ResponseUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class UserAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
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
        ResponseUtil.ok(response,result);
    }
}