package com.group.exam.service.intf;

import com.group.exam.model.entity.User;

import javax.servlet.http.HttpSession;

public interface LoginServiceInterface {
    Boolean checkRole(User user, String role);
    void setAttribute(HttpSession session, String userId);
    void removeAttribute(HttpSession session);
    String getAttribute(HttpSession session);
}
