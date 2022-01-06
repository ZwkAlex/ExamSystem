package com.group.exam.service.impl;

import com.group.exam.dao.StudentMapper;
import com.group.exam.dao.TeacherMapper;
import com.group.exam.dao.UserMapper;
import com.group.exam.model.entity.SecurityUser;
import com.group.exam.model.entity.User;
import com.group.exam.model.requestModel.PasswordChangeRequest;
import com.group.exam.model.responseModel.ResponseModel;
import com.group.exam.service.intf.UserServiceInterface;
import com.group.exam.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Service
public class UserService  implements UserServiceInterface {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Resource
    UserMapper userMapper;
    @Resource
    StudentMapper studentmapper;
    @Resource
    TeacherMapper teacherMapper;

    @Override
    public boolean checkRole(User user, String role) {
        switch(role){
            case "ROLE_STUDENT":
                return studentmapper.checkStudent(user.getId());
            case "ROLE_TEACHER":
                return teacherMapper.checkTeacher(user.getId());
            default: return false;
        }

    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userMapper.findByID(id);
        if(user==null){
            throw new UsernameNotFoundException("用户名错误！");
        }
        return new SecurityUser(user);
    }

    @Override
    public ResponseModel changePassword(PasswordChangeRequest request) {
        String ID = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String password = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        if(!password.equals(request.getOldPassword()))
            return ResponseUtil.error("错误的原密码。");
        userMapper.changePassword(ID,request.getNewPassword());
        SecurityContextHolder.clearContext();
        return ResponseUtil.success("修改成功，请重新登录");
    }

}
