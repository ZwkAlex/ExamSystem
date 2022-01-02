package com.group.exam.service.impl;

import com.group.exam.dao.StudentMapper;
import com.group.exam.dao.TeacherMapper;
import com.group.exam.dao.UserMapper;
import com.group.exam.model.entity.User;
import com.group.exam.service.intf.LoginServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Service
public class LoginService implements LoginServiceInterface {
    private static final Logger log = LoggerFactory.getLogger(LoginService.class);
    @Resource
    StudentMapper studentmapper;
    @Resource
    TeacherMapper teacherMapper;
    @Resource
    UserMapper userMapper;

    @Override
    public Boolean checkRole(User user, String role) {
        switch(role){
            case "ROLE_STUDENT":
                return studentmapper.checkStudent(user.getId());
            case "ROLE_TEACHER":
                return teacherMapper.checkTeacher(user.getId());
            default: return false;
        }

    }

    @Override
    public void setAttribute(HttpSession session, String userId) {
        if (session == null) {
            log.warn("session is null, uid#{}", userId);
        } else {
            session.setAttribute("UserID", userId);
        }
    }

    @Override
    public void removeAttribute(HttpSession session) {
        if (session == null) {
            log.warn("session is null");
        } else {
            session.removeAttribute("UserID");
        }
    }

    @Override
    public String getAttribute(HttpSession session) {
        if (session == null) {
            log.warn("session is null");
            return null;
        } else {
            return session.getAttribute("UserID").toString();
        }
    }
}
